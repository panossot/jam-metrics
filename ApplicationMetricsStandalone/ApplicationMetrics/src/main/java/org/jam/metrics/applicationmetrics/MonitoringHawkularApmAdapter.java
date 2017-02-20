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
package org.jam.metrics.applicationmetrics;

import java.lang.reflect.Method;
import org.hawkular.apm.api.model.Property;
import org.hawkular.apm.api.model.trace.Consumer;
import org.hawkular.apm.api.model.trace.Producer;
import org.hawkular.apm.api.model.trace.Trace;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;

/**
 *
 * @author panos
 */
class MonitoringHawkularApmAdapter {

    private final static Object hawkularApmLock = new Object();

    private static int id = 1;

    protected static void monitoringHawkularApmAdapter(String hawkularApm, String group, Object target, String fieldName, Object fieldValue, String hawkularTenant, Method method) throws IllegalArgumentException, IllegalAccessException {
        if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
            MetricInternalParameters internalParameters = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group);
            Trace trace = new Trace();
            trace.setTraceId(fieldName + String.valueOf(id++));
            trace.setTransaction(fieldName);
            trace.setTimestamp(System.currentTimeMillis());

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

            MonitoringHawkularApm hawkularApmInstance;
            synchronized (hawkularApmLock) {
                hawkularApmInstance = MonitoringHawkularApmCollection.getMonitoringHawkularApmCollection().getMonitoringHawkularApmInstance(group);
                if (hawkularApmInstance == null) {
                    hawkularApmInstance = new MonitoringHawkularApm(group);
                    MonitoringHawkularApmCollection.getMonitoringHawkularApmCollection().addMonitoringHawkularApmInstance(group, hawkularApmInstance);
                }
            }

            try {
                hawkularApmInstance.hawkularApm(fieldName, traceAmp, hawkularTenant, group);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }

        }
    }
}
