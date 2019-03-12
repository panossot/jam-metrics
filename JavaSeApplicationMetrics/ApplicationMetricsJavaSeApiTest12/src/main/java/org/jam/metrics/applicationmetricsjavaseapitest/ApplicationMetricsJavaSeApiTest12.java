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

import io.vertx.core.Vertx;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest12 {

    private static String groupName = "myTestGroup";
    private static Vertx vertx;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            vertx = Vertx.vertx();
            initializeMetricProperties();
            MetricsClass metricsClass = new MetricsClass();
            MetricsApiSeTestClass mTC = new MetricsApiSeTestClass(metricsClass);
            MetricsThreads mTreads =  new MetricsThreads("1",mTC);
            mTreads.start();
         
            MetricsThreads mTreads2 =  new MetricsThreads("2",mTC);
            mTreads2.start();
            
            MetricsThreads mTreads3 =  new MetricsThreads("3",mTC);
            mTreads3.start();
            
            while (mTreads.getT().isAlive() || mTreads2.getT().isAlive() || mTreads3.getT().isAlive()){};
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            vertx.close();
        }
    }
    
    private static void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setHawkularApm("false");
        metricProperties.setHawkularApmTenant("my-tenant");
        metricProperties.setHawkularApmServerPort("8680");
        metricProperties.setEventBus(vertx.eventBus());
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
    
}
