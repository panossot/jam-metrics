/*
 * Copyright 2016 panos.
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
package org.jam.metrics.applicationmetrics;

import io.opentracing.Span;
import io.opentracing.Tracer;
import java.lang.reflect.Method;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.hawkular.apm.client.opentracing.APMTracer;
import org.jam.metrics.applicationmetricsapi.HawkularApm;
import org.jam.metrics.applicationmetricsapi.Metric;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.HawkularApmManagers;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.logging.Logger;

/**
 *
 * @author panos
 */
@HawkularApm
@Interceptor
public class HawkularApmInterceptor {

    private Logger logger = Logger.getLogger(HawkularApmInterceptor.class);
    private static Tracer tracer = new APMTracer();

    @AroundInvoke
    public Object hawkularApmInterceptor(InvocationContext ctx) throws Exception {

        Method method = ctx.getMethod();
        final Object target = ctx.getTarget();

        try {
            final HawkularApm hawkularApmAnnotation = method.getAnnotation(HawkularApm.class);
            final Metric metricsAnnotation = method.getAnnotation(Metric.class);

            if (hawkularApmAnnotation != null) {
                final String group = hawkularApmAnnotation.groupName();
                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                final String hawkularApm = properties.getHawkularApm();

                if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
                    String threadName = Thread.currentThread().getName();
                    String[] submethodSpans = hawkularApmAnnotation.childMethodSpans();
                    String spanTransaction = hawkularApmAnnotation.transaction();
                    String spanService = hawkularApmAnnotation.service();

                    final MetricInternalParameters internalParams = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group);

                    HawkularApmManagers hApmManagers = internalParams.getHawkularApmManagers(threadName);
                    if (hApmManagers == null) {
                        internalParams.putHawkularApmManagers(threadName, new HawkularApmManagers());
                        hApmManagers = internalParams.getHawkularApmManagers(threadName);
                    }

                    boolean methodExists = false;
                    if (hApmManagers.getParentSpans(method.getName()) != null )
                        methodExists = true;
                    
                    if (methodExists) {

                        Span parentSpan = tracer.buildSpan(method.getName())
                                .asChildOf(hApmManagers.getParentSpans(method.getName()).get(0))
                                .withStartTimestamp(System.currentTimeMillis())
                                .withTag("service", spanService != null ? spanService : method.getName() + "Service")
                                .withTag("transaction", spanTransaction != null ? spanTransaction : method.getName() + "Transaction")
                                .start();

                        hApmManagers.getParentSpans(method.getName()).remove(0);

                        int submethodSpanLength = submethodSpans.length;
                        if (submethodSpanLength != 0) {
                            for (int i = submethodSpanLength - 1; i >= 0; i--) {
                                Span childSpan = tracer.buildSpan(submethodSpans[i])
                                        .asChildOf(parentSpan)
                                        .start();

                                if (hApmManagers.getMethodIndex(submethodSpans[i]) != -1) {
                                    hApmManagers.getParentSpans(submethodSpans[i]).add(0, childSpan);
                                } else {
                                    hApmManagers.addMethodName(submethodSpans[i]);
                                    hApmManagers.addParentSpan(submethodSpans[i], 0, childSpan);
                                }
                                
                                childSpan.finish();
                            }
                        }

                        if (hApmManagers.getParentSpans(method.getName()).isEmpty()) {
                            hApmManagers.removeParentSpans(method.getName());
                            hApmManagers.removeMethodName(method.getName());
                        }
                    } else {
                        Span parentSpan = tracer.buildSpan(method.getName())
                                .withStartTimestamp(System.currentTimeMillis())
                                .withTag("service", spanService != null ? spanService : method.getName() + "Service")
                                .withTag("transaction", spanTransaction != null ? spanTransaction : method.getName() + "Transaction")
                                .start();

                        int submethodSpanLength = submethodSpans.length;
                        if (submethodSpanLength != 0) {
                            for (int i = submethodSpanLength - 1; i >= 0; i--) {
                                Span childSpan = tracer.buildSpan(submethodSpans[i])
                                        .asChildOf(parentSpan)
                                        .start();

                                if (hApmManagers.getMethodIndex(submethodSpans[i]) != -1) {
                                    hApmManagers.getParentSpans(submethodSpans[i]).add(0, childSpan);
                                } else {
                                    hApmManagers.addMethodName(submethodSpans[i]);
                                    hApmManagers.addParentSpan(submethodSpans[i], 0, childSpan);
                                }

                                childSpan.finish();
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Object result = ctx.proceed();

        return result;
    }
}
