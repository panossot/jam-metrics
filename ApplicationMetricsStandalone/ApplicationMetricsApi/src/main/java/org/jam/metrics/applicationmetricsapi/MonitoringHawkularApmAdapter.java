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

import org.hawkular.apm.api.model.Property;
import org.hawkular.apm.api.model.trace.Consumer;
import org.hawkular.apm.api.model.trace.Producer;
import org.hawkular.apm.api.model.trace.Trace;
import org.jam.metrics.applicationmetricsjavase.HawkularApmCollection;
import org.jam.metrics.applicationmetricsjavase.MonitoringHawkularApm;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class MonitoringHawkularApmAdapter {

    private final static Object hawkularApmLock = new Object();

    private static int id = 1;

    protected static void monitoringHawkularApmAdapter(String hawkularApm, final Object instance, Object value, final String metricName, final String metricGroup, final String hawkularTenant, final MetricProperties properties, final Object[] moreArgs) throws Exception {
        if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
            MetricInternalParameters internalParameters = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(metricGroup);
            Trace trace = new Trace();
            trace.setTraceId(metricName + String.valueOf(id++));
            trace.setTransaction(metricName);
            trace.setTimestamp(System.currentTimeMillis());

            Consumer c1 = new Consumer();
            c1.getProperties().add(new Property(metricName, String.valueOf(value)));
            c1.getProperties().add(new Property("method", (String)moreArgs[0]));
            c1.getProperties().add(new Property("time", String.valueOf(System.currentTimeMillis())));
            c1.setEndpointType("js");
            c1.addInteractionCorrelationId(metricName + "_" + internalParameters.getTraceListProcessed(metricName));

            Producer p1 = new Producer();
            p1.addInteractionCorrelationId(metricName + "_" + String.valueOf(internalParameters.getTraceListProcessed(metricName) + 1));

            c1.getNodes().add(p1);
            trace.getNodes().add(c1);
            internalParameters.increaseTraceListProcessed(metricName);

            final Trace traceAmp = trace;

            MonitoringHawkularApm hawkularApmInstance;
            synchronized (hawkularApmLock) {
                hawkularApmInstance = HawkularApmCollection.getHawkularApmCollection().getHawkularApmInstance(metricGroup);
                if (hawkularApmInstance == null) {
                    hawkularApmInstance = new MonitoringHawkularApm(metricGroup);
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
