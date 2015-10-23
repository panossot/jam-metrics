/*
 * Copyright 2015 JBoss by Red Hat.
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
package org.jboss.metrics.automatedmetrics;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.jboss.metrics.automatedmetricsapi.Metric;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricObject;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricOfPlot;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCache;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCacheCollection;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author panos
 */
public class MetricPlot {

    private static List<String> plotsUsed = new ArrayList();
    
    public static synchronized void plot(Metric metricAnnotation, Field field, Object target, MetricProperties properties, String group, int refreshRate, int i) {
        if (i == 0)
            plotsUsed.clear();
        
        MetricsCache metricsCacheInstance;
        metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
        MetricObject mo = null;
        if (metricsCacheInstance != null && field != null) {
            String instanceName = field.getName() + "_" + target;
            mo = metricsCacheInstance.searchMetricObject(instanceName);
        }
        String plotName = metricAnnotation.plot()[i];
        MetricOfPlot mOP = new MetricOfPlot(field.getName(),plotName);
        if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotedCount().get(mOP)==null)
            DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).resetPlotedCount(mOP);
        
        if (mo != null) {
            int plotSize = 0;
            if (refreshRate == 0) {
                plotSize = mo.getMetric().size();
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotedCount(mOP,plotSize);
            } else if (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotedCount().get(mOP) + refreshRate <= mo.getMetric().size()) {
                plotSize = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotedCount().get(mOP) + refreshRate;
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putPlotedCount(mOP,plotSize);
            }

            if (plotSize != 0) {
                Double[] plotArray = mo.getMetric().toArray(new Double[plotSize]);
                Plot2DPanel plot = properties.getPlots().get(plotName);
                Color color = null;
                if (metricAnnotation.color().length != 0) {
                    String colorName = metricAnnotation.color()[i];
                    color = properties.getColors().get(colorName);
                }
                int plotHandler = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().get(plotName)==null?0:DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().get(plotName);
                try {
                    if (!plotsUsed.contains(plotName)) {
                        plot.removePlot(plotHandler);
                        plotsUsed.add(plotName);
                    }
                } catch (Exception e) {
                }
                
                String typePlot;
                try {
                    typePlot = metricAnnotation.typePlot()[i];
                } catch(Exception e) {
                    typePlot = "line";
                }
                if (color != null) {
                    if (typePlot.compareTo("bar")==0)
                        plotHandler = plot.addBarPlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                    else if (typePlot.compareTo("scatter")==0)
                        plotHandler = plot.addScatterPlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                    else
                        plotHandler = plot.addLinePlot(plotName, color, ArrayUtils.toPrimitive(plotArray));
                } else {
                    if (typePlot.compareTo("bar")==0)
                        plotHandler = plot.addBarPlot(plotName, ArrayUtils.toPrimitive(plotArray));
                    else if (typePlot.compareTo("scatter")==0)
                        plotHandler = plot.addScatterPlot(plotName, ArrayUtils.toPrimitive(plotArray));
                    else
                        plotHandler = plot.addLinePlot(plotName, ArrayUtils.toPrimitive(plotArray));
                }
                
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getPlotHandler().put(plotName, plotHandler);
            }
        }
    }
}
