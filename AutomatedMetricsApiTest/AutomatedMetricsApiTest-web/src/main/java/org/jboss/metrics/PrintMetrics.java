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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @EJB
    private MetricsApiSessionBean metricsApiSessionBean;
    @EJB
    private MetricsApiGetCache metricsApiGetCache;
    
    private Map<String, ArrayList<Object>> cache;
    private Set<String> metricNames;
    private Collection<ArrayList<Object>> metricValues;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PrintMetrics</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PrintMetrics : </h1>");
            metricsApiSessionBean.countClass();
            cache = metricsApiGetCache.getCache();
            metricNames = cache.keySet();
            metricValues = cache.values();
            out.println("<br>After first call of method countClass : </br>");
            Iterator<String> iob = metricNames.iterator();
            Iterator<ArrayList<Object>> iobv = metricValues.iterator();
            while (iob.hasNext()){
                out.println("<br>Metric Parameter Name : " + iob.next() + "</br>");
                if (iobv.hasNext()) {
                    ArrayList<Object> values = iobv.next();
                    for (Object value : values) {
                        out.println("<br>Value : " + value.toString() + "</br>");
                    }
                }
            }
            
            metricsApiGetCache.cleanCache();
            metricsApiSessionBean.countClass();
            cache = metricsApiGetCache.getCache();
            metricNames = cache.keySet();
            metricValues = cache.values();
            out.println("<br>After second call of method countClass : </br>");
            iob = metricNames.iterator();
            iobv = metricValues.iterator();
            while (iob.hasNext()){
                out.println("<br>Metric Parameter Name : " + iob.next() + "</br>");
                if (iobv.hasNext()) {
                    ArrayList<Object> values = iobv.next();
                    for (Object value : values) {
                        out.println("<br>Value : " + value.toString() + "</br>");
                    }
                }
            }
            out.println("</body>");
            out.println("</html>");
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
