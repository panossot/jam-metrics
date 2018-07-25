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
            WebTarget target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricList/myTestGroup/print");
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
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricList/myTestGroup");
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
            
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricProperties/get/myTestGroup/cacheEnabled");
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
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricProperties/set/myTestGroup/cacheEnabled").queryParam("enableCacheStore", "false");
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
            
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricProperties/get/myTestGroup/rhqMonitoring");
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
            
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricProperties/set/myTestGroup/rhqMonitoring").queryParam("rhqMonitoringEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String rhqMonitoring = MetricsPropertiesApi.getProperties(groupName).getRhqMonitoring();
                assertTrue("<br/>RHQ monitoring should be enabled... ", rhqMonitoring.compareTo("true")==0);
                out.println("<br/>rhqMonitoring : " + rhqMonitoring);
            }
            
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricProperties/get/myTestGroup/databaseStore");
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
            
            target = client.target("http://localhost:8080").path("/ApplicationMetricsApiTest-web-1.0.5.Final-SNAPSHOT/rest/Metrics/MetricProperties/set/myTestGroup/databaseStore").queryParam("databaseStoreEnabled", "true");
            invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            responseJaxrs = invocationBuilder.get();
            if (responseJaxrs.getStatus() != 200) {
                fail("<br/>Rest Api call failed...");
            } else {
                String databaseStore = MetricsPropertiesApi.getProperties(groupName).getDatabaseStore();
                assertTrue("<br/>DatabaseStore should be enabled... ", databaseStore.compareTo("true")==0);
                out.println("<br/>databaseStore : " + databaseStore);
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
