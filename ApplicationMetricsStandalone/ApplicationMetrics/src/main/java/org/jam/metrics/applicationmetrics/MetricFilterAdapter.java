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
import org.jam.metrics.applicationmetricsapi.MetricFilter;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary2.CodeParamsCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class MetricFilterAdapter {
    
    protected static void metricFilterAdapter(MetricFilter metricFilterAnnotation, Object target, String fieldName, Method method, Map<String, Field> metricFields) throws IllegalArgumentException, IllegalAccessException, Exception {
        
        if (metricFilterAnnotation != null) {
                final String group = metricFilterAnnotation.groupName();

                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                String filterMetrics = properties.getFilterMetrics();
                
                if (filterMetrics != null && Boolean.parseBoolean(filterMetrics)) {
                    final double comparableValue = metricFilterAnnotation.comparableValue();
                    final String filterName = metricFilterAnnotation.filterName();
                    final String filterParamName = metricFilterAnnotation.filterParamName();
                    final String userName = metricFilterAnnotation.user();
                    final boolean largerThan = metricFilterAnnotation.largerThan();
                    final boolean smallerThan = metricFilterAnnotation.smallerThan();
                    final boolean equalsWith = metricFilterAnnotation.equalsWith();
                    final Field field = accessField(metricFields, metricFilterAnnotation, method);
                    final double fieldValue = Double.parseDouble((field.get(target)).toString());
                    CodeParams cp = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(userName);
                    
                    if(fieldValue>comparableValue)
                        cp.setFilterParam(filterParamName, largerThan);
                    if(fieldValue<comparableValue)
                        cp.setFilterParam(filterParamName, smallerThan);
                    if(fieldValue==comparableValue)
                        cp.setFilterParam(filterParamName, equalsWith);
                        
                }

            }
    }
    
    private static synchronized Field accessField(Map<String, Field> metricFields, MetricFilter metricFilterAnnotation, Method method) throws Exception {
        Field field;
        if (metricFields.containsKey(metricFilterAnnotation.fieldName())) {
            field = metricFields.get(metricFilterAnnotation.fieldName());
        } else {
            field = method.getDeclaringClass().getDeclaredField(metricFilterAnnotation.fieldName());
            field.setAccessible(true);
            metricFields.put(metricFilterAnnotation.fieldName(), field);
        }
        
        return field;
    }
}
