/*
 * Copyleft 2016 
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
package org.jam.metrics.applicationmetrics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import org.jam.metrics.applicationmetricsapi.Metric;
import org.jam.metrics.applicationmetricsapi.Plot;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class JMathPlotAdapter {

    protected synchronized static void jMathPlotAdapter(String metricPlot, String group, Object target, String fieldName, int dataSize, int refreshRate, MetricProperties properties, Metric metricAnnotation, Map<String, Field> metricFields) throws IllegalArgumentException, IllegalAccessException {
        if (metricPlot != null && Boolean.parseBoolean(metricPlot)) {
            new Thread() {
                public void run() {
                    if (dataSize != 0) {
                        for (int i = 0; i < dataSize; i++) {
                            try {
                                Field field = getData(metricFields, metricAnnotation, i);
                                String fieldName = field.getName();
                                Object fieldValue = field.get(target);
                                MetricPlot.plot(metricAnnotation, fieldName, target, properties, group, refreshRate, i);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
        }
    }

    protected synchronized static void jMathPlotAdapter(String metricPlot, String group, Object target, Method method, String fieldName, int refreshRate, int i, MetricProperties properties, Plot plotAnnotation, boolean threeD) throws IllegalArgumentException, IllegalAccessException {
        if (metricPlot != null && Boolean.parseBoolean(metricPlot)) {
            new Thread() {
                public void run() {
                    try {
                        if(threeD)
                            MetricPlot.plot3D(plotAnnotation, fieldName, target, method, properties, group, refreshRate, i, threeD);
                        else
                            MetricPlot.plot2D(plotAnnotation, fieldName, target, method, properties, group, refreshRate, i, threeD);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private static Field getData(Map<String, Field> metricFields, Metric metricAnnotation, int fieldNum) throws Exception {
        Field field;
        if (metricFields.containsKey(metricAnnotation.data()[fieldNum])) {
            field = metricFields.get(metricAnnotation.data()[fieldNum]);
        } else {
            field = null;
        }
        return field;
    }
}
