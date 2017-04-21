/*
 * Copyleft 2017  by Red Hat.
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
package org.jam.metrics.applicationmetrics;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.jam.metrics.applicationmetricsapi.Metric;
import org.jam.metrics.applicationmetricsapi.Plot;
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

    public static synchronized void plot(Metric metricAnnotation, String fieldName, Object target, MetricProperties properties, String group, int refreshRate, int i) {
        MetricsCache metricsCacheInstance;
        metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
        MetricObject mo = null;
        if (metricsCacheInstance != null) {
            String instanceName = fieldName + "_" + target;
            mo = metricsCacheInstance.searchMetricObject(instanceName);
        }
        String plotName = metricAnnotation.plot()[i];
        String plotNameHandler = metricAnnotation.plotHandlerName()[i];
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
                Color color = null;
                if (metricAnnotation.color().length != 0) {
                    String colorName = metricAnnotation.color()[i];
                    color = properties.getColors().get(colorName);
                }

                int plotHandler = 0;
                if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName) != null) {
                    if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler) == null) {
                        plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getMaxPlotHandler(plotName) + 1;
                    } else {
                        plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler);
                        try {
                            plot.removePlot(plotHandler);
                        } catch (Exception e) {
                        }
                    }

                } else {
                    DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotHandlerStore(plotName, new HashMap<>());
                }

                try {

                } catch (Exception e) {
                }

                String typePlot;
                try {
                    typePlot = metricAnnotation.typePlot()[i];
                } catch (Exception e) {
                    typePlot = "line";
                }
                if (color != null) {
                    if (typePlot.compareTo("bar") == 0) {
                        plotHandler = plot.addBarPlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler = plot.addScatterPlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("stair") == 0) {
                        plotHandler = plot.addStaircasePlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler = plot.addHistogramPlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray), 10);
                    } else {
                        plotHandler = plot.addLinePlot(plotNameHandler, color, ArrayUtils.toPrimitive(plotArray));
                    }
                } else {
                    if (typePlot.compareTo("bar") == 0) {
                        plotHandler = plot.addBarPlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler = plot.addScatterPlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("stair") == 0) {
                        plotHandler = plot.addStaircasePlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler = plot.addHistogramPlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray), 10);
                    } else {
                        plotHandler = plot.addLinePlot(plotNameHandler, ArrayUtils.toPrimitive(plotArray));
                    }
                }

                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).put(plotNameHandler, plotHandler);
            }
        }
    }

    public static synchronized void plot3D(Plot plotAnnotation, String fieldName, Object target, Method method, MetricProperties properties, String group, int refreshRate, int i, boolean threeD) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (threeD) {
            String plotName = plotAnnotation.plot()[i];
            String plotNameHandler = plotAnnotation.plotHandlerName()[i];
            Field field = method.getDeclaringClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            double[][] plotData = (double[][]) field.get(target);
            Plot3DPanel plot = properties.get3DPlots().get(plotName);
            Color color = null;
            if (plotAnnotation.color().length != 0) {
                String colorName = plotAnnotation.color()[i];
                color = properties.getColors().get(colorName);
            }

            int plotHandler = 0;
            if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName) != null) {
                if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler) == null) {
                    plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getMaxPlotHandler(plotName) + 1;
                } else {
                    plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler);
                    try {
                        plot.removePlot(plotHandler);
                    } catch (Exception e) {
                    }
                }
            } else {
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotHandlerStore(plotName, new HashMap<>());
            }

            String typePlot;
            try {
                typePlot = plotAnnotation.typePlot()[i];
            } catch (Exception e) {
                typePlot = "line";
            }
            if (color != null) {
                if (typePlot.compareTo("box") == 0) {
                    plotHandler = plot.addBoxPlot(plotNameHandler, color, plotData);
                } else if (typePlot.compareTo("scatter") == 0) {
                    plotHandler = plot.addScatterPlot(plotNameHandler, color, plotData);
                } else if (typePlot.compareTo("grid") == 0) {
                    plotHandler = plot.addGridPlot(plotNameHandler, color, increment(0, 1, plotData.length), increment(0, 1, plotData[0].length), plotData);
                } else if (typePlot.compareTo("histogram") == 0) {
                    plotHandler = plot.addHistogramPlot(plotNameHandler, color, plotData);
                } else {
                    plotHandler = plot.addLinePlot(plotNameHandler, color, plotData);
                }
            } else {
                if (typePlot.compareTo("box") == 0) {
                    plotHandler = plot.addBoxPlot(plotNameHandler, plotData);
                } else if (typePlot.compareTo("scatter") == 0) {
                    plotHandler = plot.addScatterPlot(plotNameHandler, plotData);
                } else if (typePlot.compareTo("grid") == 0) {
                    plotHandler = plot.addGridPlot(plotName, increment(0, 1, plotData.length), increment(0, 1, plotData[0].length), plotData);
                } else if (typePlot.compareTo("histogram") == 0) {
                    plotHandler = plot.addHistogramPlot(plotNameHandler, plotData);
                } else {
                    plotHandler = plot.addLinePlot(plotNameHandler, plotData);
                }
            }

            plot.repaint();
            DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).put(plotNameHandler, plotHandler);
        }
    }

    public static synchronized void plot2D(Plot plotAnnotation, String fieldName, Object target, Method method, MetricProperties properties, String group, int refreshRate, int i, boolean threeD) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (!threeD) { // Then it is 2D
            String plotName = plotAnnotation.plot()[i];
            String plotNameHandler = plotAnnotation.plotHandlerName()[i];
            Field field = method.getDeclaringClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            double[][] plotData = (double[][]) field.get(target);
            Plot2DPanel plot = properties.getPlots().get(plotName);
            Color color = null;
            if (plotAnnotation.color().length != 0) {
                String colorName = plotAnnotation.color()[i];
                color = properties.getColors().get(colorName);
            }

            int plotHandler = 0;
            if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName) != null) {
                if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler) == null) {
                    plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getMaxPlotHandler(plotName) + 1;
                } else {
                    plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).get(plotNameHandler);
                    try {
                        plot.removePlot(plotHandler);
                    } catch (Exception e) {
                    }
                }
            } else {
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotHandlerStore(plotName, new HashMap<>());
            }

            String typePlot;
            try {
                typePlot = plotAnnotation.typePlot()[i];
            } catch (Exception e) {
                typePlot = "line";
            }
            if (color != null) {
                if (typePlot.compareTo("box") == 0) {
                    plotHandler = plot.addBoxPlot(plotNameHandler, color, plotData);
                } else if (typePlot.compareTo("bar") == 0) {
                    plotHandler = plot.addBarPlot(plotNameHandler, color, plotData);
                } else if (typePlot.compareTo("scatter") == 0) {
                    plotHandler = plot.addScatterPlot(plotNameHandler, color, plotData);
                } else if (typePlot.compareTo("stair") == 0) {
                    plotHandler = plot.addStaircasePlot(plotNameHandler, color, plotData);
                } else if (typePlot.compareTo("histogram") == 0) {
                    plotHandler = plot.addHistogramPlot(plotNameHandler, color, plotData);
                } else {
                    plotHandler = plot.addLinePlot(plotNameHandler, color, plotData);
                }
            } else {
                if (typePlot.compareTo("box") == 0) {
                    plotHandler = plot.addBoxPlot(plotNameHandler, plotData);
                } else if (typePlot.compareTo("bar") == 0) {
                    plotHandler = plot.addBarPlot(plotName, plotData);
                } else if (typePlot.compareTo("scatter") == 0) {
                    plotHandler = plot.addScatterPlot(plotNameHandler, plotData);
                } else if (typePlot.compareTo("stair") == 0) {
                    plotHandler = plot.addStaircasePlot(plotNameHandler, plotData);
                } else if (typePlot.compareTo("histogram") == 0) {
                    plotHandler = plot.addHistogramPlot(plotNameHandler, plotData);
                } else {
                    plotHandler = plot.addLinePlot(plotNameHandler, plotData);
                }
            }

            plot.repaint();
            DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandlerStore().get(plotName).put(plotNameHandler, plotHandler);
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
