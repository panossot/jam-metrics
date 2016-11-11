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
package org.jam.metrics.applicationmetricsapi;

import org.jam.metrics.applicationmetricsjavase.MonitoringRhq;
import org.jam.metrics.applicationmetricsjavase.MonitoringRhqCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class MonitoringRhqAdapter {

    private final static Object rhqLock = new Object();
    
    protected static void monitoringRhqAdapter(String rhqMonitoring, final Object instance, Object value, final String metricName, final String metricGroup, final MetricProperties properties) throws Exception {
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
    }
}
