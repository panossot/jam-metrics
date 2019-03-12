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

import java.util.concurrent.Callable;
import org.jam.metrics.applicationmetricsapi.types.Metric;
import org.jam.metrics.applicationmetricsapi.types.MetricProperties;



/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSeTestClass {

    private Metric count;
    
    private Metric count2;
    
    private MetricProperties metricProperties;
    
    private static int i = 0;
    
    public MetricsApiSeTestClass() {
        metricProperties = new MetricProperties(null, "myTestGroup", null, null);
        count = new Metric("count", 0, metricProperties);
        count2 = new Metric("count2", 0, metricProperties);
    }

    public void countMethod() throws Exception {
        Callable<Object> applyMetricFeatures1 = new Callable<Object>() {
            public Object call() throws Exception {
                count.setMetricValue((int)count.getMetricValue()+1);
                return null;
            }
        };
        
        Callable<Object> applyMetricFeatures2 = new Callable<Object>() {
            public Object call() throws Exception {
                count2.setMetricValue((int)count2.getMetricValue()+2);
                return null;
            }
        };
        
        for (int j=0; j<100; j++) {
            count.applyMetricFeatures(applyMetricFeatures1);
            count2.applyMetricFeatures(applyMetricFeatures2);
        }
    }

}
