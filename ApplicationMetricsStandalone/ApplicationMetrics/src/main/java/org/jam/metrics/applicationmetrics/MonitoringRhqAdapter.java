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

/**
 *
 * @author panos
 */
class MonitoringRhqAdapter {

    private final static Object rhqLock = new Object();
    
    protected static void monitoringRhqAdapter(String rhqMonitoring, String group, Object target, String fieldName) throws IllegalArgumentException, IllegalAccessException {
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
    }
}
