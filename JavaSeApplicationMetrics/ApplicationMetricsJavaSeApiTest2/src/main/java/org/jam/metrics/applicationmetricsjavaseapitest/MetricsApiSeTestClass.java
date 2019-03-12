/*
 * Copyleft 2015 
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
package org.jam.metrics.applicationmetricsjavaseapitest;

import org.jam.metrics.applicationmetricsapi.ApplicationJavaSeMetrics;
import org.jam.metrics.applicationmetricsapi.ApplicationJavaSeMetricsDbStore;



/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSeTestClass {

    private int count = 0;
    
    private int count2 = 0;

    public int countMethod() throws Exception {
        count++;
        count2 += 2;
        ApplicationJavaSeMetrics.metric(this,count,"count","myTestGroup");
        ApplicationJavaSeMetricsDbStore.metricsDbStore(this, new Object[]{count}, "myTestGroup", "statement_1", new String[]{"StoreDBMetric","count"}, null);
        ApplicationJavaSeMetrics.metric(this,count2,"count2","myTestGroup");
        ApplicationJavaSeMetricsDbStore.metricsDbStore(this, new Object[]{count2}, "myTestGroup", "statement_1", new String[]{"StoreDBMetric","count2"}, null);

        return count;
    }

}
