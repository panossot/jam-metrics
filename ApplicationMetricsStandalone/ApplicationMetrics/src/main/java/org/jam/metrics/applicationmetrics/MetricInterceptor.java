/*
 * Copyleft 2015 
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
import java.util.HashMap;
import java.util.Map;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jam.metrics.applicationmetricsapi.Metric;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Metric
@Interceptor
public class MetricInterceptor {

    private Map<String, Field> metricFields = new HashMap();
   
    @AroundInvoke
    public Object metricsInterceptor(InvocationContext ctx) throws Exception {
        Object result = ctx.proceed();
        Method method = ctx.getMethod();
        final Object target = ctx.getTarget();

        try {
            final Metric metricAnnotation = method.getAnnotation(Metric.class);
            HashMap<String, Object> metricValuesInternal = new HashMap();
            if (metricAnnotation != null) {
                int fieldNameSize = metricAnnotation.fieldName().length;
                final int dataSize = metricAnnotation.data().length;
                final String group = metricAnnotation.groupName();

                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                String cacheStore = properties.getCacheStore();
                String rhqMonitoring = properties.getRhqMonitoring();
                String hawkularMonitoring = properties.getHawkularMonitoring();
                String hawkularApm = properties.getHawkularMetricsApm();
                final String hawkularTenant = properties.getHawkularTenant();
                String metricPlot = properties.getMetricPlot();
                final int refreshRate = properties.getPlotRefreshRate();

                for (int i = 0; i < fieldNameSize; i++) {

                    final Field field = accessField(metricAnnotation, method, i);
                    final Object fieldValue = field.get(target);
                    final String fieldName = field.getName();
                    metricValuesInternal.put(metricAnnotation.fieldName()[i], field.get(target));
                    
                    CacheAdapter.cacheAdapter(cacheStore, group, target, fieldName, fieldValue, properties);

                    MonitoringRhqAdapter.monitoringRhqAdapter(rhqMonitoring, group, target, fieldName);

                    MonitoringHawkularAdapter.monitoringHawkularAdapter(hawkularMonitoring, group, target, fieldName, hawkularTenant);

                    MonitoringHawkularApmAdapter.monitoringHawkularApmAdapter(hawkularApm, group, target, fieldName, fieldValue, hawkularTenant, method);

                }

                JMathPlotAdapter.jMathPlotAdapter(metricPlot, group, target, cacheStore, dataSize, refreshRate, properties, metricAnnotation, metricFields);
            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private synchronized Field accessField(Metric metricAnnotation, Method method, int fieldNum) throws Exception {
        Field field;
        if (metricFields.containsKey(metricAnnotation.fieldName()[fieldNum])) {
            field = metricFields.get(metricAnnotation.fieldName()[fieldNum]);
        } else {
            field = method.getDeclaringClass().getDeclaredField(metricAnnotation.fieldName()[fieldNum]);
            field.setAccessible(true);
            metricFields.put(metricAnnotation.fieldName()[fieldNum], field);
        }

        return field;
    }

}
