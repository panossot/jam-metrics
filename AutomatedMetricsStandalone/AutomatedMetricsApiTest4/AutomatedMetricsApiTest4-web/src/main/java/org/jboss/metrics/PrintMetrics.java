/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.metrics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.metrics.automatedmetricsapi.MetricsCacheApi;
import org.jboss.metrics.automatedmetricsapi.MetricsPropertiesApi;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCacheCollection;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author panos
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
    
    private String deploymentName = "myTestDeployment";
    
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
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PrintMetrics.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(deploymentName)!=null)
                out.println(MetricsCacheApi.printMetricsCache(deploymentName));
            
            out.println("<br>Successful Run ...</br>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void initializeMetricProperties() {
        metricsBean = new MetricsApiSessionBean(metricsClass);
        metricsBean2 = new MetricsApiSessionBean(metricsClass);
        
        HashMap<String,String> rhqScheduleIds = new HashMap<String,String>();
        rhqScheduleIds.put("count", "11401");
        rhqScheduleIds.put("count2", "11402");
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setRhqMonitoring("false");
        metricProperties.setCacheStore("true");
        metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        MetricsPropertiesApi.storeProperties(deploymentName, metricProperties);
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
