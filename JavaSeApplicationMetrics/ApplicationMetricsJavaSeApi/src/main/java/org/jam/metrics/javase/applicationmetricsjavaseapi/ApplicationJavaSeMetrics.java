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
package org.jam.metrics.javase.applicationmetricsjavaseapi;

import org.hawkular.apm.api.model.Property;
import org.hawkular.apm.api.model.trace.Consumer;
import org.hawkular.apm.api.model.trace.Producer;
import org.hawkular.apm.api.model.trace.Trace;
import org.jam.metrics.applicationmetricsjavase.HawkularApm;
import org.jam.metrics.applicationmetricsjavase.HawkularApmCollection;
import org.jam.metrics.applicationmetricsjavase.MonitoringHawkular;
import org.jam.metrics.applicationmetricsjavase.MonitoringHawkularCollection;
import org.jam.metrics.applicationmetricsjavase.MonitoringRhq;
import org.jam.metrics.applicationmetricsjavase.MonitoringRhqCollection;
import org.jam.metrics.applicationmetricsjavase.Store;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationJavaSeMetrics {

    private final static Object cacheStorage = new Object();
    private final static Object rhqLock = new Object();
    private final static Object hawkularLock = new Object();
    private final static Object hawkularApmLock = new Object();
    private static int id = 1;

    public static void metric(final Object instance, Object value, final String metricName, final String metricGroup, final String... moreArgs) throws Exception {
        final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup);
        String cacheStore = properties.getCacheStore();
        String rhqMonitoring = properties.getRhqMonitoring();
        String hawkularMonitoring = properties.getHawkularMonitoring();
        String hawkularApm = properties.getHawkularApm();
        final String hawkularTenant = properties.getHawkularTenant();

        synchronized (cacheStorage) {
            if (cacheStore != null && Boolean.parseBoolean(cacheStore)) {
                MetricsCache metricsCacheInstance;
                metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(metricGroup);
                if (metricsCacheInstance == null) {
                    metricsCacheInstance = new MetricsCache();
                    MetricsCacheCollection.getMetricsCacheCollection().addMetricsCacheInstance(metricGroup, metricsCacheInstance);
                }
                Store.CacheStore(instance, metricName, value, metricsCacheInstance, properties);
            }
        }

        if (rhqMonitoring != null && Boolean.parseBoolean(rhqMonitoring)) {
            new Thread() {
                public void run() {
                    MonitoringRhq mrhqInstance;
                    synchronized (rhqLock) {
                        mrhqInstance = MonitoringRhqCollection.getRhqCollection().getMonitoringRhqInstance(metricGroup);
                        if (mrhqInstance == null) {
                            mrhqInstance = new MonitoringRhq(metricGroup);
                            MonitoringRhqCollection.getRhqCollection().addMonitoringRhqInstance(metricGroup, mrhqInstance);
                        }
                    }

                    try {
                        mrhqInstance.rhqMonitoring(instance, metricName, metricGroup);
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
                        mhawkularInstance = MonitoringHawkularCollection.getHawkularCollection().getMonitoringHawkularInstance(metricGroup);
                        if (mhawkularInstance == null) {
                            mhawkularInstance = new MonitoringHawkular(metricGroup);
                            MonitoringHawkularCollection.getHawkularCollection().addMonitoringHawkularInstance(metricGroup, mhawkularInstance);
                        }
                    }

                    try {
                        mhawkularInstance.hawkularMonitoring(instance, metricName, hawkularTenant, metricGroup);
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }

                }
            }.start();
        }

        if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
            MetricInternalParameters internalParameters = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(metricGroup);
            Trace trace = new Trace();
            trace.setId(metricName + String.valueOf(id++));
            trace.setBusinessTransaction(metricName);
            trace.setStartTime(System.currentTimeMillis());

            Consumer c1 = new Consumer();
            c1.getProperties().add(new Property(metricName, String.valueOf(value)));
            c1.getProperties().add(new Property("method", moreArgs[0]));
            c1.getProperties().add(new Property("time", String.valueOf(System.currentTimeMillis())));
            c1.setEndpointType("js");
            c1.addInteractionCorrelationId(metricName + "_" + internalParameters.getTraceListProcessed(metricName));

            Producer p1 = new Producer();
            p1.addInteractionCorrelationId(metricName + "_" + String.valueOf(internalParameters.getTraceListProcessed(metricName) + 1));

            c1.getNodes().add(p1);
            trace.getNodes().add(c1);
            internalParameters.increaseTraceListProcessed(metricName);

            final Trace traceAmp = trace;

            HawkularApm hawkularApmInstance;
            synchronized (hawkularApmLock) {
                hawkularApmInstance = HawkularApmCollection.getHawkularApmCollection().getHawkularApmInstance(metricGroup);
                if (hawkularApmInstance == null) {
                    hawkularApmInstance = new HawkularApm(metricGroup);
                    HawkularApmCollection.getHawkularApmCollection().addHawkularApmInstance(metricGroup, hawkularApmInstance);
                }
            }

            try {
                hawkularApmInstance.hawkularApm(metricName, traceAmp, hawkularTenant, metricGroup);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }

        }

    }

}
