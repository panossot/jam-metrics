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
package org.jboss.metrics;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.metrics.automatedmetricsapi.CodeParamsApi;
import org.jboss.metrics.automatedmetricsapi.MetricsPropertiesApi;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

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
            metricsApiSessionBean.countMethod();
            out.println("<br>Successful Run ...</br>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setJBossOpenAnalytics("true");
        CodeParamsApi.addUserName("Niki");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "panos");
            Statement stmt = connection.createStatement();
            createDbTable(stmt);
            HashMap<String, Statement> dbStmt = new HashMap<String, Statement>();
            dbStmt.put("jboss_analytics_statement", stmt);
            dbStmt.put("jboss_analytics_location_data_statement", stmt);
            metricProperties.setDatabaseStatement(dbStmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }

    private void createDbTable(Statement stmt) {
        try {
            String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'MyMETRICS' AND table_name = 'openAnalyticsValues'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            boolean exists = rs.getInt("COUNT(*)") > 0;

            if (!exists) {
                String sql = "CREATE DATABASE MyMETRICS";
                try {
                    stmt.executeUpdate(sql);
                    System.out.println("Database created successfully...");
                } catch (Exception ex) {
                    System.out.println("Database already exists...");
                }
                sql = "CREATE TABLE MyMETRICS.openAnalyticsValues(ID int NOT NULL AUTO_INCREMENT, IP_RECORD varchar(255),"
                        + "LOCATION_RECORD varchar(255),NUMACCESS_RECORD varchar(255),TIMEACCESS_RECORD varchar(255),METHOD_NAME varchar(255),"
                        + "CLASS_NAME varchar(255),INSTANCE varchar(255),USER_NAME varchar(255),RECORD_TIME DATETIME,PRIMARY KEY(ID));";

                stmt.executeUpdate(sql);
            }

            query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'MyMETRICS' AND table_name = 'openAnalyticsLocationData'";
            rs = stmt.executeQuery(query);
            rs.next();
            exists = rs.getInt("COUNT(*)") > 0;

            if (!exists) {
                String sql = "CREATE TABLE MyMETRICS.openAnalyticsLocationData(SERVER_INSTANCE_NAME varchar(255) NOT NULL,"
                        + "SERVER_INSTANCELOCATION varchar(255),PRIMARY KEY(SERVER_INSTANCE_NAME));";

                stmt.executeUpdate(sql);
                
                sql = "INSERT INTO MyMETRICS.openAnalyticsLocationData(SERVER_INSTANCE_NAME,SERVER_INSTANCELOCATION) VALUES('testing-instance','CZ, Brno, Red Hat Office, TPB, 2nd Floor, South');";
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
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
