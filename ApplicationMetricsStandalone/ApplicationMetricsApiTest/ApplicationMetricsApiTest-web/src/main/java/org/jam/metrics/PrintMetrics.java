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
package org.jam.metrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class PrintMetrics extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private MetricsApiSessionBean metricsApiSessionBean;
    
    private String groupName = "myTestGroup";
    private String AppName = "/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT";
    private String ServerAddress = "http://localhost:8080";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");
        initializeMetricProperties();
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PrintMetrics</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PrintMetrics : </h1>");
            metricsApiSessionBean.countMethod();
            out.println(MetricsCacheApi.printMetricsCache(groupName));
            out.println("<br>Successful Run ...</br>");
            
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricList/myTestGroup/print");
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("<br/>Rest Api call failed...");
                }
                out.println("<br/>printMetricsList : " + rs);
            }

            metricsApiSessionBean.countMethod();
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricList/myTestGroup");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                MetricsCache mc = new ObjectMapper().readValue(rs, MetricsCache.class);

                HashSet<MetricObject> metricsCache = mc.getMetricCache();

                assertTrue("<br/>There should be two metrics inside the cache", metricsCache.size() == 2);

                for (MetricObject mObject : metricsCache) {
                    assertTrue("There should be two metrics values for each metric inside the cache", mObject.getMetric().size() == 2);
                    out.println("<br/>" + mObject.getName() + "," + mObject.getMetric().toString());
                }
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/cacheEnabled");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("<br/>Rest Api call failed...");
                }
                assertTrue("<br/>The cache storage should be enabled... ", rs.compareTo("true")==0);
                out.println("<br/>" + rs);
            }
            
            metricsApiSessionBean.countMethod();
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/cacheEnabled").queryParam("enableCacheStore", "false");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                out.println(responseJaxrs.getStatus());
                fail("Rest Api call failed...");
            } else {
               HashSet<MetricObject> metricsCache = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName).getMetricCache();

                assertTrue("There should be two metrics inside the cache", metricsCache.size() == 2);

                for (MetricObject mObject : metricsCache) {
                    assertTrue("There should be three metrics values for each metric inside the cache", mObject.getMetric().size() == 3);
                    out.println("<br/>" + mObject.getName() + "," + mObject.getMetric().toString());
                }
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqMonitoring");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("<br/>Rest Api call failed...");
                }
                assertTrue("<br/>RHQ monitoring should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>rhqMonitoring : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqMonitoring").queryParam("rhqMonitoringEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rhqMonitoring = MetricsPropertiesApi.getProperties(groupName).getRhqMonitoring();
                assertTrue("<br/>RHQ monitoring should be enabled... ", rhqMonitoring.compareTo("true")==0);
                out.println("<br/>rhqMonitoring : " + rhqMonitoring);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/databaseStore");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("<br/>Rest Api call failed...");
                }
                assertTrue("<br/>DatabaseStore should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>databaseStore : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/databaseStore").queryParam("databaseStoreEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String databaseStore = MetricsPropertiesApi.getProperties(groupName).getDatabaseStore();
                assertTrue("<br/>DatabaseStore should be enabled... ", databaseStore.compareTo("true")==0);
                out.println("<br/>databaseStore : " + databaseStore);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularMonitoring");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("Hawkular monitoring should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>hawkularMonitoring : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularMonitoring").queryParam("hawkularMonitoringEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularMonitoring = MetricsPropertiesApi.getProperties(groupName).getHawkularMonitoring();
                assertTrue("Hawkular monitoring should be enabled... ", hawkularMonitoring.compareTo("true")==0);
                out.println("<br/>hawkularMonitoring : " + hawkularMonitoring);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularApm");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("Hawkular Apm should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>hawkularApm : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularApm").queryParam("hawkularApmEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularApm = MetricsPropertiesApi.getProperties(groupName).getHawkularApm();
                assertTrue("Hawkular Apm should be enabled... ", hawkularApm.compareTo("true")==0);
                out.println("<br/>hawkularApm : " + hawkularApm);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/metricPlot");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("MetricPlot should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>metricPlot : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/metricPlot").queryParam("metricPlotEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String metricPlot = MetricsPropertiesApi.getProperties(groupName).getMetricPlot();
                assertTrue("MetricPlot should be enabled... ", metricPlot.compareTo("true")==0);
                out.println("<br/>metricPlot : " + metricPlot);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/OpenAnalytics");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("OpenAnalytics should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>OpenAnalytics : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/OpenAnalytics").queryParam("OpenAnalyticsEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String OpenAnalytics = MetricsPropertiesApi.getProperties(groupName).getOpenAnalytics();
                assertTrue("OpenAnalytics should be enabled... ", OpenAnalytics.compareTo("true")==0);
                out.println("<br/>OpenAnalytics : " + OpenAnalytics);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/filterMetrics");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                if (rs == null) {
                    fail("Rest Api call failed...");
                }
                assertTrue("FilterMetrics should be disabled... ", rs.compareTo("false")==0);
                out.println("<br/>filterMetrics : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/filterMetrics").queryParam("filterMetricsEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String filterMetrics = MetricsPropertiesApi.getProperties(groupName).getFilterMetrics();
                assertTrue("FilterMetrics should be enabled... ", filterMetrics.compareTo("true")==0);
                out.println("<br/>FilterMetrics : " + filterMetrics);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/cacheMaxSize");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int rs = responseJaxrs.readEntity(Integer.class);
                assertTrue("CacheMaxSize should not be changed... ", rs==Integer.MAX_VALUE);
                out.println("<br/>cacheMaxSize : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/cacheMaxSize").queryParam("cacheMaxSize", 1000);
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int cacheMaxSize = MetricsPropertiesApi.getProperties(groupName).getCacheMaxSize();
                assertTrue("cacheMaxSize should be changed... ", cacheMaxSize==1000);
                out.println("<br/>cacheMaxSize : " + cacheMaxSize);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/plotRefreshRate");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int rs = responseJaxrs.readEntity(Integer.class);
                assertTrue("PlotRefreshRate should not be changed... ", rs==1);
                out.println("<br/>plotRefreshRate : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/plotRefreshRate").queryParam("plotRefreshRate", 5);
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int plotRefreshRate = MetricsPropertiesApi.getProperties(groupName).getPlotRefreshRate();
                assertTrue("plotRefreshRate should be changed... ", plotRefreshRate==5);
                out.println("<br/>plotRefreshRate : " + plotRefreshRate);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqMonitoringRefreshRate");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int rs = responseJaxrs.readEntity(Integer.class);
                assertTrue("RhqMonitoringRefreshRate should not be changed... ", rs==1);
                out.println("<br/>rhqMonitoringRefreshRate : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqMonitoringRefreshRate").queryParam("rhqMonitoringRefreshRate", 5);
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int rhqMonitoringRefreshRate = MetricsPropertiesApi.getProperties(groupName).getRhqMonitoringRefreshRate();
                assertTrue("rhqMonitoringRefreshRate should be changed... ", rhqMonitoringRefreshRate==5);
                out.println("<br/>rhqMonitoringRefreshRate : " + rhqMonitoringRefreshRate);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqServerUrl");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("RhqServerUrl should not be changed... ", rs.compareTo("localhost")==0);
                out.println("<br/>rhqServerUrl : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqServerUrl").queryParam("rhqServerUrl", "localhost2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rhqServerUrl = MetricsPropertiesApi.getProperties(groupName).getRhqServerUrl();
                assertTrue("rhqServerUrl should be changed... ", rhqServerUrl.compareTo("localhost2")==0);
                out.println("<br/>rhqServerUrl : " + rhqServerUrl);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqServerPort");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("rhqServerPort should not be changed... ", rs.compareTo("7080")==0);
                out.println("<br/>rhqServerPort : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqServerPort").queryParam("rhqServerPort", "7081");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rhqServerPort = MetricsPropertiesApi.getProperties(groupName).getRhqServerPort();
                assertTrue("rhqServerPort should be changed... ", rhqServerPort.compareTo("7081")==0);
                out.println("<br/>rhqServerPort : " + rhqServerPort);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqServerUsername");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("rhqServerUsername should not be changed... ", rs.compareTo("rhqadmin")==0);
                out.println("<br/>rhqServerUsername : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqServerUsername").queryParam("rhqServerUsername", "rhqadmin2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rhqServerUsername = MetricsPropertiesApi.getProperties(groupName).getRhqServerUsername();
                assertTrue("rhqServerUsername should be changed... ", rhqServerUsername.compareTo("rhqadmin2")==0);
                out.println("<br/>rhqServerUsername : " + rhqServerUsername);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqServerPassword");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("rhqServerPassword should not be changed... ", rs.compareTo("rhqadmin")==0);
                out.println("<br/>rhqServerPassword : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqServerPassword").queryParam("rhqServerPassword", "rhqadmin2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rhqServerPassword = MetricsPropertiesApi.getProperties(groupName).getRhqServerPassword();
                assertTrue("rhqServerPassword should be changed... ", rhqServerPassword.compareTo("rhqadmin2")==0);
                out.println("<br/>rhqServerPassword : " + rhqServerPassword);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularMonitoringRefreshRate");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int rs = responseJaxrs.readEntity(Integer.class);
                assertTrue("hawkularMonitoringRefreshRate should not be changed... ", rs==1);
                out.println("<br/>hawkularMonitoringRefreshRate : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularMonitoringRefreshRate").queryParam("hawkularMonitoringRefreshRate", 5);
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                int hawkularMonitoringRefreshRate = MetricsPropertiesApi.getProperties(groupName).getHawkularMonitoringRefreshRate();
                assertTrue("hawkularMonitoringRefreshRate should be changed... ", hawkularMonitoringRefreshRate==5);
                out.println("<br/>hawkularMonitoringRefreshRate : " + hawkularMonitoringRefreshRate);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularServerUrl");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularServerUrl should not be changed... ", rs.compareTo("localhost")==0);
                out.println("<br/>hawkularServerUrl : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularServerUrl").queryParam("hawkularServerUrl", "localhost2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularServerUrl = MetricsPropertiesApi.getProperties(groupName).getHawkularServerUrl();
                assertTrue("hawkularServerUrl should be changed... ", hawkularServerUrl.compareTo("localhost2")==0);
                out.println("<br/>hawkularServerUrl : " + hawkularServerUrl);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularServerPort");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularServerPort should not be changed... ", rs.compareTo("8080")==0);
                out.println("<br/>hawkularServerPort : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularServerPort").queryParam("hawkularServerPort", "8081");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularServerPort = MetricsPropertiesApi.getProperties(groupName).getHawkularServerPort();
                assertTrue("hawkularServerPort should be changed... ", hawkularServerPort.compareTo("8081")==0);
                out.println("<br/>hawkularServerPort : " + hawkularServerPort);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularServerUsername");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularServerUsername should not be changed... ", rs.compareTo("hawkularadmin")==0);
                out.println("<br/>hawkularServerUsername : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularServerUsername").queryParam("hawkularServerUsername", "hawkularadmin2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularServerUsername = MetricsPropertiesApi.getProperties(groupName).getHawkularServerUsername();
                assertTrue("hawkularServerUsername should be changed... ", hawkularServerUsername.compareTo("hawkularadmin2")==0);
                out.println("<br/>hawkularServerUsername : " + hawkularServerUsername);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularServerPassword");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularServerPassword should not be changed... ", rs.compareTo("hawkularadmin")==0);
                out.println("<br/>hawkularServerPassword : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularServerPassword").queryParam("hawkularServerPassword", "hawkularadmin2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularServerPassword = MetricsPropertiesApi.getProperties(groupName).getHawkularServerPassword();
                assertTrue("hawkularServerPassword should be changed... ", hawkularServerPassword.compareTo("hawkularadmin2")==0);
                out.println("<br/>hawkularServerPassword : " + hawkularServerPassword);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularTenant");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularTenant should not be changed... ", rs.compareTo("hawkular")==0);
                out.println("<br/>hawkularTenant : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularTenant").queryParam("hawkularTenant", "hawkular2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularTenant = MetricsPropertiesApi.getProperties(groupName).getHawkularTenant();
                assertTrue("hawkularTenant should be changed... ", hawkularTenant.compareTo("hawkular2")==0);
                out.println("<br/>hawkularTenant : " + hawkularTenant);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularApmServerUrl");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularApmServerUrl should not be changed... ", rs.compareTo("localhost")==0);
                out.println("<br/>hawkularApmServerUrl : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularApmServerUrl").queryParam("hawkularApmServerUrl", "localhost2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularApmServerUrl = MetricsPropertiesApi.getProperties(groupName).getHawkularApmServerUrl();
                assertTrue("hawkularApmServerUrl should be changed... ", hawkularApmServerUrl.compareTo("localhost2")==0);
                out.println("<br/>hawkularApmServerUrl : " + hawkularApmServerUrl);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularApmServerPort");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularApmServerPort should not be changed... ", rs.compareTo("8080")==0);
                out.println("<br/>hawkularApmServerPort : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularApmServerPort").queryParam("hawkularApmServerPort", "8081");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularApmServerPort = MetricsPropertiesApi.getProperties(groupName).getHawkularApmServerPort();
                assertTrue("hawkularApmServerPort should be changed... ", hawkularApmServerPort.compareTo("8081")==0);
                out.println("<br/>hawkularApmServerPort : " + hawkularApmServerPort);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularApmServerUsername");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularApmServerUsername should not be changed... ", rs.compareTo("hawkularapmadmin")==0);
                out.println("<br/>hawkularApmServerUsername : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularApmServerUsername").queryParam("hawkularApmServerUsername", "hawkularapmadmin2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularApmServerUsername = MetricsPropertiesApi.getProperties(groupName).getHawkularApmServerUsername();
                assertTrue("hawkularApmServerUsername should be changed... ", hawkularApmServerUsername.compareTo("hawkularapmadmin2")==0);
                out.println("<br/>hawkularApmServerUsername : " + hawkularApmServerUsername);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularApmServerPassword");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularApmServerPassword should not be changed... ", rs.compareTo("hawkularapmadmin")==0);
                out.println("<br/>hawkularApmServerPassword : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularApmServerPassword").queryParam("hawkularApmServerPassword", "hawkularapmadmin2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularApmServerPassword = MetricsPropertiesApi.getProperties(groupName).getHawkularApmServerPassword();
                assertTrue("hawkularApmServerPassword should be changed... ", hawkularApmServerPassword.compareTo("hawkularapmadmin2")==0);
                out.println("<br/>hawkularApmServerPassword : " + hawkularApmServerPassword);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/hawkularApmTenant");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("hawkularApmTenant should not be changed... ", rs.compareTo("my-tenant")==0);
                out.println("<br/>hawkularApmTenant : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/hawkularApmTenant").queryParam("hawkularApmTenant", "my-tenant2");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String hawkularApmTenant = MetricsPropertiesApi.getProperties(groupName).getHawkularApmTenant();
                assertTrue("hawkularApmTenant should be changed... ", hawkularApmTenant.compareTo("my-tenant2")==0);
                out.println("<br/>hawkularApmTenant : " + hawkularApmTenant);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/get/myTestGroup/rhqScheduleId").queryParam("rhqScheduleIdName", "count");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rs = responseJaxrs.readEntity(String.class);
                assertTrue("rhqScheduleId should not be changed... ", rs.compareTo("11761")==0);
                out.println("<br/>rhqScheduleId : " + rs);
            }
            
            target = client.target(ServerAddress).path(AppName+"/rest/Metrics/MetricProperties/set/myTestGroup/rhqScheduleId").queryParam("rhqScheduleIdName", "count").queryParam("rhqScheduleId", "11392");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("Rest Api call failed...");
            } else {
                String rhqScheduleId = MetricsPropertiesApi.getProperties(groupName).getRhqScheduleId("count");
                assertTrue("rhqScheduleId should be changed... ", rhqScheduleId.compareTo("11392")==0);
                out.println("<br/>rhqScheduleId : " + rhqScheduleId);
            }
            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void initializeMetricProperties() {
        HashMap<String,String> rhqScheduleIds = new HashMap<String,String>();
        rhqScheduleIds.put("count", "11761");
        rhqScheduleIds.put("count2", "11762");
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setRhqMonitoring("false");
        metricProperties.setCacheStore("true");
      //  metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
