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
import org.jam.metrics.applicationmetricsapi.MetricFilter;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary2.CodeParamsCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

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
            
            MetricFilterAdapter.metricFilterAdapter(metricFilterAnnotation, target, null, method, metricFields);

        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
