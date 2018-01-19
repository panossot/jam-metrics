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
package org.jam.metrics.applicationmetricsjavaseapitest;

import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest7 {

    private static String groupName = "myTestGroup";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass mTC = new MetricsApiSeTestClass();
            mTC.countMethod();
            mTC.countMethod();
            System.out.println(MetricsCacheApi.printMetricsCache(groupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setHawkularMonitoring("true");
        metricProperties.setCacheStore("true");
        metricProperties.setHawkularTenant("hawkular");
        metricProperties.setHawkularServerUsername("hawkular");
        metricProperties.setHawkularServerPassword("hawkular");
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
}
