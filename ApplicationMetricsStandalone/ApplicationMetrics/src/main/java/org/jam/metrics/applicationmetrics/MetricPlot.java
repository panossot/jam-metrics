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

    private static List<String> plotsUsed = new ArrayList();

    public static synchronized void plot(Metric metricAnnotation, String fieldName, Object target, MetricProperties properties, String group, int refreshRate, int i) {
        if (i == 0) {
            plotsUsed.clear();
        }

        MetricsCache metricsCacheInstance;
        metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
        MetricObject mo = null;
        if (metricsCacheInstance != null) {
            String instanceName = fieldName + "_" + target;
            mo = metricsCacheInstance.searchMetricObject(instanceName);
        }
        String plotName = metricAnnotation.plot()[i];
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
                int plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().get(plotName) == null ? 0 : DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().get(plotName);
                try {
                    if (!plotsUsed.contains(plotName)) {
                        plotsUsed.add(plotName);
                    }
                    plot.removePlot(plotHandler);
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
                        plotHandler = plot.addBarPlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler = plot.addScatterPlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("stair") == 0) {
                        plotHandler = plot.addStaircasePlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler = plot.addHistogramPlot(plotName, color, ArrayUtils.toPrimitive(plotArray), 10);
                    } else {
                        plotHandler = plot.addLinePlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                    }
                } else {
                    if (typePlot.compareTo("bar") == 0) {
                        plotHandler = plot.addBarPlot(plotName, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("scatter") == 0) {
                        plotHandler = plot.addScatterPlot(plotName, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("stair") == 0) {
                        plotHandler = plot.addStaircasePlot(plotName, ArrayUtils.toPrimitive(plotArray));
                    } else if (typePlot.compareTo("histogram") == 0) {
                        plotHandler = plot.addHistogramPlot(plotName, ArrayUtils.toPrimitive(plotArray), 10);
                    } else {
                        plotHandler = plot.addLinePlot(plotName, ArrayUtils.toPrimitive(plotArray));
                    }
                }

                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().put(plotName, plotHandler);
            }
        }
    }

    public static synchronized void plot(Plot plotAnnotation, String fieldName, Object target, Method method, MetricProperties properties, String group, int refreshRate, int i, boolean threeD) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (threeD) {
            if (i == 0) {
                plotsUsed.clear();
            }

            String plotName = plotAnnotation.plot()[i];
            Field field = method.getDeclaringClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            double[][] plotData = (double[][]) field.get(target);
            Plot3DPanel plot = properties.get3DPlots().get(plotName);
            Color color = null;
            if (plotAnnotation.color().length != 0) {
                String colorName = plotAnnotation.color()[i];
                color = properties.getColors().get(colorName);
            }
            int plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().get(plotName) == null ? 0 : DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().get(plotName);
            try {
                if (!plotsUsed.contains(plotName)) {
                    plotsUsed.add(plotName);
                }
                plot.removePlot(plotHandler);
            } catch (Exception e) {
            }

            String typePlot;
            try {
                typePlot = plotAnnotation.typePlot()[i];
            } catch (Exception e) {
                typePlot = "line";
            }
            if (color != null) {
                if (typePlot.compareTo("box") == 0) {
                    plotHandler = plot.addBoxPlot(plotName, color, plotData);
                } else if (typePlot.compareTo("scatter") == 0) {
                    plotHandler = plot.addScatterPlot(plotName, color, plotData);
                } else if (typePlot.compareTo("grid") == 0) {
                    plotHandler = plot.addGridPlot(plotName, color, plotData);
                } else if (typePlot.compareTo("histogram") == 0) {
                    plotHandler = plot.addHistogramPlot(plotName, color, plotData);
                } else {
                    plotHandler = plot.addLinePlot(plotName, color, plotData);
                }
            } else {
                if (typePlot.compareTo("box") == 0) {
                    plotHandler = plot.addBoxPlot(plotName, plotData);
                } else if (typePlot.compareTo("scatter") == 0) {
                    plotHandler = plot.addScatterPlot(plotName, plotData);
                } else if (typePlot.compareTo("grid") == 0) {
                    plotHandler = plot.addGridPlot(plotName, plotData);
                } else if (typePlot.compareTo("histogram") == 0) {
                    plotHandler = plot.addHistogramPlot(plotName, plotData);
                } else {
                    plotHandler = plot.addLinePlot(plotName, plotData);
                }
            }

            DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().put(plotName, plotHandler);
        }
    }
}
