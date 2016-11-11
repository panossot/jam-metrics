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

/**
 *
 * @author panos
 */
class MonitoringHawkularAdapter {

    private final static Object hawkularLock = new Object();

    protected static void monitoringHawkularAdapter(String hawkularMonitoring, String group, Object target, String fieldName, String hawkularTenant) throws IllegalArgumentException, IllegalAccessException {
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
    }
}
