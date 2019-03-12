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
package org.jam.metrics.applicationmetrics;

/**
 *
 * @author panos
 */
class OpenAnalyticsAdapter {

    private final static Object OpenAnalyticsLock = new Object();

    protected static void openAnalyticsAdapter(String OpenAnalytics, String group, boolean idRecord, boolean locationRecord, boolean numAccessRecord, boolean timeAccessRecord, boolean date, int time, String methodName, String className, Object instance, String user, String recordDbName, String recordTableName, String locationDbName, String locationTableName, String statementName, String locationStatementName) throws IllegalArgumentException, IllegalAccessException {
        if (OpenAnalytics != null && Boolean.parseBoolean(OpenAnalytics)) {
            new Thread() {
                public void run() {
                    try {
                        OpenAnalyticsInstance OpenAnalyticsInstance;
                        synchronized (OpenAnalyticsLock) {
                            OpenAnalyticsInstance = OpenAnalyticsCollection.getOpenAnalyticsCollection().getOpenAnalyticsInstance(group);
                            if (OpenAnalyticsInstance == null) {
                                OpenAnalyticsInstance = new OpenAnalyticsInstance();
                                OpenAnalyticsCollection.getOpenAnalyticsCollection().addOpenAnalyticsInstance(group, OpenAnalyticsInstance);
                            }
                        }

                        OpenAnalyticsInstance.dbStoreAnalytics(idRecord, locationRecord, numAccessRecord, timeAccessRecord, date, time, methodName, className, instance, user, recordDbName, recordTableName, locationDbName, locationTableName, statementName, locationStatementName, group);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
