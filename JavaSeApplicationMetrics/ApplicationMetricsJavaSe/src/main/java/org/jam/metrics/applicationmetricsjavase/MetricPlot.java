/*
 * Copyleft 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */
package org.jam.metrics.applicationmetricsjavase;

import java.awt.Color;
import java.util.HashMap;
import org.apache.commons.lang.ArrayUtils;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricObject;
import org.jam.metrics.applicationmetricslibrary.MetricOfPlot;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

/**
 *
 * @author panos
 */
public class MetricPlot {

    public static synchronized void plot(String fieldName, Object target, MetricProperties properties, String group, int refreshRate, String plotNames, String plotNameHandlers, String colorNames, String typePlots) {
        MetricsCache metricsCacheInstance;
        metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
        MetricObject mo = null;
        if (metricsCacheInstance != null) {
            String instanceName = fieldName + "_" + target;
            mo = metricsCacheInstance.searchMetricObject(instanceName);
        }
        String plotName = ((plotNames!=null)?plotNames:"defaultPlotName");
        String plotNameHandler = ((plotNameHandlers!=null)?plotNameHandlers:"defaultPlotNameHandler");
        MetricOfPlot mOP = new MetricOfPlot(fieldName, plotName);
        if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotedCount().get(mOP) == null) {
            DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).resetPlotedCount(mOP);
        }

        if (mo != null) {
            int plotSize = 0;
            if (refreshRate == 0) {
                plotSize = mo.getMetric().size();
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotedCount(mOP, plotSize);
            } else if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotedCount().get(mOP) + refreshRate <= mo.getMetric().size()) {
                plotSize = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotedCount().get(mOP) + refreshRate;
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotedCount(mOP, plotSize);
            }

            if (plotSize != 0) {
                Double[] plotArray = mo.getMetric().toArray(new Double[plotSize]);
                Plot2DPanel plot = properties.getPlots().get(plotName);
                if (plot!=null) {
                    Color color = null;
                    String colorName = ((colorNames!=null)?colorNames:"red");
                    color = properties.getColors().get(colorName);

                    int plotHandler = 0;
                    if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName) != null) {
                        if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler) == null) {
                            plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getMaxPlotHandler(plotName) + 1;
                        } else {
                            plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler);
                        }
                    } else {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotHandlerStore(plotName, new HashMap<String,Integer>());
                    }

                    String typePlot = ((typePlots!=null)?typePlots:"line");

                    int plotHandler2 = 0;
                    Plot2DPanel plot2 = new Plot2DPanel();

                    if (color != null) {
                        if (typePlot.compareTo("bar") == 0) {
                            plotHandler2 = plot2.addBarPlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                        } else if (typePlot.compareTo("scatter") == 0) {
                            plotHandler2 = plot2.addScatterPlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                        } else if (typePlot.compareTo("stair") == 0) {
                            plotHandler2 = plot2.addStaircasePlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                        } else if (typePlot.compareTo("histogram") == 0) {
                            plotHandler2 = plot2.addHistogramPlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray), 10);
                        } else {
                            plotHandler2 = plot2.addLinePlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                        }
                    } else {
                        if (typePlot.compareTo("bar") == 0) {
                            plotHandler2 = plot2.addBarPlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                        } else if (typePlot.compareTo("scatter") == 0) {
                            plotHandler2 = plot2.addScatterPlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                        } else if (typePlot.compareTo("stair") == 0) {
                            plotHandler2 = plot2.addStaircasePlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                        } else if (typePlot.compareTo("histogram") == 0) {
                            plotHandler2 = plot2.addHistogramPlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray), 10);
                        } else {
                            plotHandler2 = plot2.addLinePlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                        }
                    }

                    if (plot.plotCanvas.getPlots().size()==0)
                        plot.addPlot(plot2.getPlot(plotHandler2));
                    else {
                        if (plotHandler < plot.plotCanvas.getPlots().size())
                            plot.setPlot(plotHandler, plot2.getPlot(plotHandler2));
                        else
                            plot.addPlot(plot2.getPlot(plotHandler2));
                    }

                    plot.setAutoBounds();
                    plot.repaint();
                    DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).put(plotNameHandler, plotHandler);
                }
            }
        }
    }

    public static synchronized void plot3D(double[][] plotData, MetricProperties properties, String group, String plotNames, String plotNameHandlers, String colorNames, String typePlots, boolean threeD) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (threeD) {
            String plotName = ((plotNames!=null)?plotNames:"defaultPlotName");
            String plotNameHandler = ((plotNameHandlers!=null)?plotNameHandlers:"defaultPlotNameHandler");
            Plot3DPanel plot = properties.get3DPlots().get(plotName);
            if (plot!=null) {
                Color color = null;
                String colorName = ((colorNames!=null)?colorNames:"red");
                color = properties.getColors().get(colorName);

                int plotHandler = 0;
                if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName) != null) {
                    if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler) == null) {
                        plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getMaxPlotHandler(plotName) + 1;
                    } else {
                        plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler);
                    }
                } else {
                    DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotHandlerStore(plotName, new HashMap<String,Integer>());
                }

                String typePlot = ((typePlots!=null)?typePlots:"line");

                int plotHandler2 = 0;
                Plot3DPanel plot2 = new Plot3DPanel();

                if (color != null) {
                    if (typePlot.compareTo("box") == 0) {
                        plotHandler2 = plot2.addBoxPlot(plotNameHandler, color, plotData);
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler2 = plot2.addScatterPlot(plotNameHandler, color, plotData);
                    } else if (typePlot.compareTo("grid") == 0) {
                        plotHandler2 = plot2.addGridPlot(plotNameHandler, color, increment(0, 1, plotData.length), increment(0, 1, plotData[0].length), plotData);
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler2 = plot2.addHistogramPlot(plotNameHandler, color, plotData);
                    } else {
                        plotHandler2 = plot2.addLinePlot(plotNameHandler, color, plotData);
                    }
                } else {
                    if (typePlot.compareTo("box") == 0) {
                        plotHandler2 = plot2.addBoxPlot(plotNameHandler, plotData);
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler2 = plot2.addScatterPlot(plotNameHandler, plotData);
                    } else if (typePlot.compareTo("grid") == 0) {
                        plotHandler2 = plot2.addGridPlot(plotName, increment(0, 1, plotData.length), increment(0, 1, plotData[0].length), plotData);
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler2 = plot2.addHistogramPlot(plotNameHandler, plotData);
                    } else {
                        plotHandler2 = plot2.addLinePlot(plotNameHandler, plotData);
                    }
                }

                if (plot.plotCanvas.getPlots().size()==0)
                    plot.addPlot(plot2.getPlot(plotHandler2));
                else {
                    if (plotHandler < plot.plotCanvas.getPlots().size())
                        plot.setPlot(plotHandler, plot2.getPlot(plotHandler2));
                    else
                        plot.addPlot(plot2.getPlot(plotHandler2));
                }

                plot.setAutoBounds();
                plot.repaint();
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).put(plotNameHandler, plotHandler);
            }
        }
    }

    public static synchronized void plot2D(double[][] plotData, MetricProperties properties, String group, String plotNames, String plotNameHandlers, String colorNames, String typePlots, boolean threeD) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (!threeD) { // Then it is 2D
            String plotName = ((plotNames!=null)?plotNames:"defaultPlotName");
            String plotNameHandler = ((plotNameHandlers!=null)?plotNameHandlers:"defaultPlotNameHandler");
            Plot2DPanel plot = properties.getPlots().get(plotName);
            if (plot!=null) {
                Color color = null;
                String colorName = ((colorNames!=null)?colorNames:"red");
                color = properties.getColors().get(colorName);

                int plotHandler = 0;
                if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName) != null) {
                    if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler) == null) {
                        plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getMaxPlotHandler(plotName) + 1;
                    } else {
                        plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler);
                    }
                } else {
                    DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotHandlerStore(plotName, new HashMap<String,Integer>());
                }

                String typePlot = ((typePlots!=null)?typePlots:"line");

                int plotHandler2 = 0;
                Plot2DPanel plot2 = new Plot2DPanel();

                if (color != null) {
                    if (typePlot.compareTo("box") == 0) {
                        plotHandler2 = plot2.addBoxPlot(plotNameHandler, color, plotData);
                    } else if (typePlot.compareTo("bar") == 0) {
                        plotHandler2 = plot2.addBarPlot(plotNameHandler, color, plotData);
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler2 = plot2.addScatterPlot(plotNameHandler, color, plotData);
                    } else if (typePlot.compareTo("stair") == 0) {
                        plotHandler2 = plot2.addStaircasePlot(plotNameHandler, color, plotData);
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler2 = plot2.addHistogramPlot(plotNameHandler, color, plotData);
                    } else {
                        plotHandler2 = plot2.addLinePlot(plotNameHandler, color, plotData);
                    }
                } else {
                    if (typePlot.compareTo("box") == 0) {
                        plotHandler2 = plot2.addBoxPlot(plotNameHandler, plotData);
                    } else if (typePlot.compareTo("bar") == 0) {
                        plotHandler2 = plot2.addBarPlot(plotName, plotData);
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler2 = plot2.addScatterPlot(plotNameHandler, plotData);
                    } else if (typePlot.compareTo("stair") == 0) {
                        plotHandler2 = plot2.addStaircasePlot(plotNameHandler, plotData);
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler2 = plot2.addHistogramPlot(plotNameHandler, plotData);
                    } else {
                        plotHandler2 = plot2.addLinePlot(plotNameHandler, plotData);
                    }
                }

                if (plot.plotCanvas.getPlots().size()==0)
                    plot.addPlot(plot2.getPlot(plotHandler2));
                else {
                    if (plotHandler < plot.plotCanvas.getPlots().size())
                        plot.setPlot(plotHandler, plot2.getPlot(plotHandler2));
                    else
                        plot.addPlot(plot2.getPlot(plotHandler2));
                }

                plot.setAutoBounds();
                plot.repaint();
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).put(plotNameHandler, plotHandler);
            }
        }
    }

    private static double[] increment(double start, double step, double end) {
        double range = end - start;
        int steps = (int) (range / step);
        double[] rv = new double[steps];
        for (int i = 0; i < steps; i++) {
            rv[i] = start + ((step / range) * i);
        }
        return rv;
    }
}
