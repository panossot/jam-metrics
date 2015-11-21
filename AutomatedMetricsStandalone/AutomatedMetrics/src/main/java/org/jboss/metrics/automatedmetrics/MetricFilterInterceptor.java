/*
 * Copyleft 2015 Red Hat, Inc. and/or its affiliates
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

package org.jboss.metrics.automatedmetrics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.metrics.automatedmetricsapi.MetricFilter;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParams;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParamsCollection;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@MetricFilter
@Interceptor
public class MetricFilterInterceptor {

    private Map<String, Field> metricFields = new HashMap();

    @AroundInvoke
    public Object metricFilterInterceptor(InvocationContext ctx) throws Exception {
        Object result = ctx.proceed();
        Method method = ctx.getMethod();
        final Object target = ctx.getTarget();
            
        try {
            final MetricFilter metricFilterAnnotation = method.getAnnotation(MetricFilter.class);
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
                    final Field field = accessField(metricFilterAnnotation, method);
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

        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    private synchronized Field accessField(MetricFilter metricFilterAnnotation, Method method) throws Exception {
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
