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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricslibrary.MetricObject;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest {

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
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:10399").path("/Metrics/MetricList/myTestGroup/print");
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = response.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                System.out.println(rs);
            }

            mTC.countMethod();
            target = client.target("http://localhost:10399").path("/Metrics/MetricList/myTestGroup");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = response.readEntity(String.class);
                MetricsCache mc = new ObjectMapper().readValue(rs, MetricsCache.class);

                HashSet<MetricObject> metricsCache = mc.getMetricCache();

                assertTrue("There should be two metrics inside the cache", metricsCache.size() == 2);

                for (MetricObject mObject : metricsCache) {
                    Iterator<Object> iob = Collections.synchronizedList(new ArrayList<Object>(mObject.getMetric())).iterator();
                    ArrayList<Object> metricValues = new ArrayList<>();
                    while (iob.hasNext()) {
                        metricValues.add(iob.next().toString());
                    }
                    assertTrue("There should be two metrics values for each metric inside the cache", metricValues.size() == 3);
                    System.out.println(mObject.getName() + "," + metricValues);
                }
            }
            
            target = client.target("http://localhost:10399").path("/Metrics/MetricProperties/get/myTestGroup/cacheEnabled");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = response.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("The cache storage should be enabled... ", rs.compareTo("true")==0);
                System.out.println(rs);
            }
            
            target = client.target("http://localhost:10399").path("/Metrics/MetricProperties/set/myTestGroup/cacheEnabled").queryParam("enableCacheStore", "false");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                System.out.println(response.getStatus());
                fail("Rest Api call failed...");
            } else {
               mTC.countMethod();
               HashSet<MetricObject> metricsCache = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName).getMetricCache();

                assertTrue("There should be two metrics inside the cache", metricsCache.size() == 2);

                for (MetricObject mObject : metricsCache) {
                    Iterator<Object> iob = Collections.synchronizedList(new ArrayList<Object>(mObject.getMetric())).iterator();
                    ArrayList<Object> metricValues = new ArrayList<>();
                    while (iob.hasNext()) {
                        metricValues.add(iob.next().toString());
                    }
                    assertTrue("There should be two metrics values for each metric inside the cache", metricValues.size() == 3);
                    System.out.println(mObject.getName() + "," + metricValues);
                }
            }
            
            target = client.target("http://localhost:10399").path("/Metrics/MetricProperties/get/myTestGroup/rhqMonitoring");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = response.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("RHQ monitoring should be disabled... ", rs.compareTo("false")==0);
                System.out.println(rs);
            }
            
            target = client.target("http://localhost:10399").path("/Metrics/MetricProperties/set/myTestGroup/rhqMonitoring").queryParam("rhqMonitoringEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rhqMonitoring = MetricsPropertiesApi.getProperties(groupName).getRhqMonitoring();
                assertTrue("RHQ monitoring should be enabled... ", rhqMonitoring.compareTo("true")==0);
                System.out.println(rhqMonitoring);
            }
            
            target = client.target("http://localhost:10399").path("/Metrics/MetricProperties/get/myTestGroup/hawkularMonitoring");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = response.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("Hawkular monitoring should be disabled... ", rs.compareTo("false")==0);
                System.out.println(rs);
            }
            
            target = client.target("http://localhost:10399").path("/Metrics/MetricProperties/set/myTestGroup/hawkularMonitoring").queryParam("hawkularMonitoringEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            if (response.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularMonitoring = MetricsPropertiesApi.getProperties(groupName).getHawkularMonitoring();
                assertTrue("Hawkular monitoring should be enabled... ", hawkularMonitoring.compareTo("true")==0);
                System.out.println(hawkularMonitoring);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MetricsPropertiesApi.stopServer();
        }
    }

    private static void initializeMetricProperties() {
        HashMap<String, String> rhqScheduleIds = new HashMap<String, String>();
        rhqScheduleIds.put("count", "11391");
        rhqScheduleIds.put("count2", "11392");
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setRhqMonitoring("false");
        metricProperties.setHawkularMonitoring("false");
        metricProperties.setCacheStore("true");
        metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
        MetricsPropertiesApi.RestApi("http://localhost/", 10399);
    }
}
