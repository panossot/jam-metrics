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

package org.jam.metrics.applicationmetricsapi;

import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationJavaSeMetricsSyncDbStore {
    
    public static void metricsDbStore(Object instance, Object[] values, String group, String statementName, String[] queryUpdateDB, String metricUser) throws Exception {
        String dataBaseStorage = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getDatabaseStore();
        
        try {
            DBStoreSyncAdapter.dBStoreSyncAdapter(dataBaseStorage, instance, values, group, statementName, queryUpdateDB, metricUser);
        } catch(Exception e) {
            e.printStackTrace();
        }
         
    }
}

