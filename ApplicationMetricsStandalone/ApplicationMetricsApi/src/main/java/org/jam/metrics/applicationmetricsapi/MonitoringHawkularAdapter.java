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
package org.jam.metrics.applicationmetricsapi;

import org.jam.metrics.applicationmetricsjavase.MonitoringHawkular;
import org.jam.metrics.applicationmetricsjavase.MonitoringHawkularCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class MonitoringHawkularAdapter {

    private final static Object hawkularLock = new Object();

    protected static void monitoringHawkularAdapter(String hawkularMonitoring, final Object instance, Object value, final String metricName, final String metricGroup, final String hawkularTenant, final MetricProperties properties) throws Exception {
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
    }
}
