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
import org.hawkular.apm.api.model.Property;
import org.hawkular.apm.api.model.trace.Consumer;
import org.hawkular.apm.api.model.trace.Producer;
import org.hawkular.apm.api.model.trace.Trace;
import org.jam.metrics.applicationmetricsapi.Metric;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricslibrary.MetricObject;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Metric
@Interceptor
public class MetricInterceptor {

    private Map<String, Field> metricFields = new HashMap();
    private final static Object rhqLock = new Object();
    private final static Object hawkularLock = new Object();
    private final static Object hawkularApmLock = new Object();
    private final static Object cacheLock = new Object();
    private static int id = 1;

    @AroundInvoke
    public Object metricsInterceptor(InvocationContext ctx) throws Exception {
        Object result = ctx.proceed();
        Method method = ctx.getMethod();
        final Object target = ctx.getTarget();

        try {
            final Metric metricAnnotation = method.getAnnotation(Metric.class);
            MetricsCache metricsCacheInstance = null;
            HashMap<String, Object> metricValuesInternal = new HashMap();
            if (metricAnnotation != null) {
                int fieldNameSize = metricAnnotation.fieldName().length;
                final int dataSize = metricAnnotation.data().length;
                final String group = metricAnnotation.groupName();

                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                String cacheStore = properties.getCacheStore();
                String rhqMonitoring = properties.getRhqMonitoring();
                String hawkularMonitoring = properties.getHawkularMonitoring();
                String hawkularApm = properties.getHawkularApm();
                final String hawkularTenant = properties.getHawkularTenant();
                String metricPlot = properties.getMetricPlot();
                final int refreshRate = properties.getPlotRefreshRate();

                for (int i = 0; i < fieldNameSize; i++) {

                    final Field field = accessField(metricAnnotation, method, i);
                    final Object fieldValue = field.get(target);
                    final String fieldName = field.getName();
                    metricValuesInternal.put(metricAnnotation.fieldName()[i], field.get(target));
                    Trace trace = null;
                    MetricObject mo = null;

                    if (cacheStore != null && Boolean.parseBoolean(cacheStore)) {
                        synchronized (cacheLock) {
                            metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
                            if (metricsCacheInstance == null) {
                                metricsCacheInstance = new MetricsCache();
                                MetricsCacheCollection.getMetricsCacheCollection().addMetricsCacheInstance(group, metricsCacheInstance);
                            }

                            mo = Store.CacheStore(target, fieldName, fieldValue, metricsCacheInstance, properties);

                            if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {

                            }
                        }
                    }

                    if (rhqMonitoring != null && Boolean.parseBoolean(rhqMonitoring)) {
                        new Thread() {
                            public void run() {
                                MonitoringRhq mrhqInstance;
                                synchronized (rhqLock) {
                                    mrhqInstance = MonitoringRhqCollection.getRhqCollection().getMonitoringRhqInstance(group);
                                    if (mrhqInstance == null) {
                                        mrhqInstance = new MonitoringRhq(group);
                                        MonitoringRhqCollection.getRhqCollection().addMonitoringRhqInstance(group, mrhqInstance);
                                    }
                                }

                                try {
                                    mrhqInstance.rhqMonitoring(target, fieldName, group);
                                } catch (IllegalArgumentException | IllegalAccessException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        }.start();
                    }

                    if (hawkularMonitoring != null && Boolean.parseBoolean(hawkularMonitoring)) {
                        new Thread() {
                            public void run() {
                                MonitoringHawkular mhawkularInstance;
                                synchronized (hawkularLock) {
                                    mhawkularInstance = MonitoringHawkularCollection.getHawkularCollection().getMonitoringHawkularInstance(group);
                                    if (mhawkularInstance == null) {
                                        mhawkularInstance = new MonitoringHawkular(group);
                                        MonitoringHawkularCollection.getHawkularCollection().addMonitoringHawkularInstance(group, mhawkularInstance);
                                    }
                                }

                                try {
                                    mhawkularInstance.hawkularMonitoring(target, fieldName, hawkularTenant, group);
                                } catch (IllegalArgumentException | IllegalAccessException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        }.start();
                    }

                    if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
                        MetricInternalParameters internalParameters = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group);
                        trace = new Trace();
                        trace.setId(fieldName + String.valueOf(id++));
                        trace.setStartTime(System.currentTimeMillis());

                        Consumer c1 = new Consumer();
                        c1.getProperties().add(new Property(fieldName, String.valueOf(fieldValue)));
                        c1.getProperties().add(new Property("method", method.getName()));
                        c1.getProperties().add(new Property("time", String.valueOf(System.currentTimeMillis())));
                        c1.setEndpointType("js");
                        c1.addInteractionCorrelationId(fieldName + "_" + internalParameters.getTraceListProcessed(fieldName));

                        Producer p1 = new Producer();
                        p1.addInteractionCorrelationId(fieldName + "_" + String.valueOf(internalParameters.getTraceListProcessed(fieldName) + 1));

                        c1.getNodes().add(p1);
                        trace.getNodes().add(c1);
                        internalParameters.increaseTraceListProcessed(fieldName);

                        final Trace traceAmp = trace;

                        HawkularApm hawkularApmInstance;
                        synchronized (hawkularApmLock) {
                            hawkularApmInstance = HawkularApmCollection.getHawkularApmCollection().getHawkularApmInstance(group);
                            if (hawkularApmInstance == null) {
                                hawkularApmInstance = new HawkularApm(group);
                                HawkularApmCollection.getHawkularApmCollection().addHawkularApmInstance(group, hawkularApmInstance);
                            }
                        }

                        try {
                            hawkularApmInstance.hawkularApm(target, fieldName, traceAmp, hawkularTenant, group);
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            ex.printStackTrace();
                        }

                    }

                }

                if (metricPlot != null && Boolean.parseBoolean(metricPlot)) {
                    new Thread() {
                        public void run() {
                            if (dataSize != 0) {
                                for (int i = 0; i < dataSize; i++) {
                                    try {
                                        Field field = getData(metricAnnotation, i);
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

    private Field getData(Metric metricAnnotation, int fieldNum) throws Exception {
        Field field;
        if (metricFields.containsKey(metricAnnotation.data()[fieldNum])) {
            field = metricFields.get(metricAnnotation.data()[fieldNum]);
        } else {
            field = null;
        }
        return field;
    }
}
