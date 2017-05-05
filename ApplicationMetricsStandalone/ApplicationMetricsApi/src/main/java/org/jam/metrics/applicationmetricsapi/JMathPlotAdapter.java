/*
 * Copyleft 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
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
package org.jam.metrics.applicationmetricsapi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import org.jam.metrics.applicationmetricsjavase.MetricPlot;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
public class JMathPlotAdapter {

    protected synchronized static void jMathPlotAdapter(String group, Object target, String fieldName, int refreshRate, MetricProperties properties, String plotName, String plotNameHandler, String colorName, String typePlot) throws IllegalArgumentException, IllegalAccessException {
        if (properties.getMetricPlot() != null && Boolean.parseBoolean(properties.getMetricPlot())) {
            new Thread() {
                public void run() {
                    try {
                        MetricPlot.plot(fieldName, target, properties, group, refreshRate, plotName, plotNameHandler, colorName, typePlot);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public synchronized static void jMathPlotAdapter(double[][] plotData, String group, MetricProperties properties, String plotName, String plotNameHandler, String colorName, String typePlot, boolean threeD) throws IllegalArgumentException, IllegalAccessException {
        if (properties.getMetricPlot() != null && Boolean.parseBoolean(properties.getMetricPlot())) {
            new Thread() {
                public void run() {
                    try {
                        if(threeD)
                            MetricPlot.plot3D(plotData, properties, group, plotName, plotNameHandler, colorName, typePlot, threeD);
                        else
                            MetricPlot.plot2D(plotData, properties, group, plotName, plotNameHandler, colorName, typePlot, threeD);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }

}
