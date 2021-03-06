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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jam.metrics.applicationmetricsapi.CodeParamsApi;
import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

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
    
    MetricsApiSessionBean metricsBean;
    
    MetricsApiSessionBean metricsBean2;
    
    @Inject 
    MetricsClass metricsClass;
    
    private String groupName = "myTestGroup";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            MetricsThreads mTreads =  new MetricsThreads(metricsBean, "1");
            mTreads.start();
         
            MetricsThreads mTreads2 =  new MetricsThreads(metricsBean2, "2");
            mTreads2.start();
            
            MetricsThreads mTreads3 =  new MetricsThreads(metricsBean2, "3");
            mTreads3.start();
            
            while (mTreads.getT().isAlive() || mTreads2.getT().isAlive() || mTreads3.getT().isAlive()){};
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
                out.println(MetricsCacheApi.printMetricsCache(groupName));
            
            out.println("<br>Successful Run ...</br>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void initializeMetricProperties() {
        metricsBean = new MetricsApiSessionBean(metricsClass);
        metricsBean2 = new MetricsApiSessionBean(metricsClass);
        
        HashMap<String,String> rhqScheduleIds = new HashMap<String,String>();
        rhqScheduleIds.put("count", "11761");
        rhqScheduleIds.put("count2", "11762");
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setRhqMonitoring("false");
        metricProperties.setRhqMonitoringRefreshRate(100);
        metricProperties.setCacheStore("true");
        metricProperties.setCacheMaxSize(10000);
    //    metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        metricProperties.setDatabaseStore("true");
        HashMap<String,Integer> dbUpdateRates = new HashMap<>();
        dbUpdateRates.put("Niki", 300);
        metricProperties.setUpdateRateOfDbQueries(dbUpdateRates);
        CodeParamsApi.addUserName("Niki");
        try {
            Connection  connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "panos");
            Statement stmt = connection.createStatement();
            createDbTable(stmt);
            HashMap<String,Statement> dbStmt = new HashMap<String,Statement>();
            dbStmt.put("statement_1", stmt);
            metricProperties.setDatabaseStatement(dbStmt);
            HashMap<String,String> query1 = new HashMap<String,String>();
            query1.put("StoreDBMetric", "INSERT INTO MyMETRICS.metricValues(SEQUENCE_NUM,METRIC_NAME,METRIC_VALUE,METRIC_INSTANCE,RECORD_TIME) VALUES(#sequenceNum#, '{1}', [1], '{instance}', '{time}');");
            metricProperties.setUpdateDbQueries(query1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
    
    private void createDbTable(Statement stmt) {
        try {
            String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'MyMETRICS' AND table_name = 'metricValues'";
            ResultSet rs = stmt.executeQuery(query);    
            try {              
		    rs.next();
		    boolean exists = rs.getInt("COUNT(*)") > 0;
		    
		    if (!exists) {
		        String sql = "CREATE DATABASE MyMETRICS";
		        stmt.executeUpdate(sql);
		        System.out.println("Database created successfully...");

		        sql = "CREATE TABLE MyMETRICS.metricValues(ID int NOT NULL AUTO_INCREMENT, SEQUENCE_NUM int, METRIC_NAME varchar(255) NOT NULL," +
		              " METRIC_VALUE varchar(255) NOT NULL, METRIC_INSTANCE varchar(255), RECORD_TIME DATETIME, PRIMARY KEY(ID));"; 
		        
		        stmt.executeUpdate(sql);
		    }
            } catch(Exception e){
                    String sql = "DROP DATABASE MyMETRICS";
                    stmt.executeUpdate(sql);
		    sql = "CREATE DATABASE MyMETRICS";
		    stmt.executeUpdate(sql);
		    System.out.println("Database created successfully...");

		    sql = "CREATE TABLE MyMETRICS.metricValues(ID int NOT NULL AUTO_INCREMENT, SEQUENCE_NUM int, METRIC_NAME varchar(255) NOT NULL," +
		          " METRIC_VALUE varchar(255) NOT NULL, METRIC_INSTANCE varchar(255), RECORD_TIME DATETIME, PRIMARY KEY(ID));"; 
		        
		    stmt.executeUpdate(sql);
	    }
        } catch(Exception e){
            e.printStackTrace();
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
