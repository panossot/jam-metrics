/*
 * Copyright 2015  by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org.icenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jam.metrics.applicationmetricslibrary;

import java.util.HashMap;

/**
 *
 * @author panos
 */
public class MetricInternalParameters {
    private HashMap<String,Integer> plotHandler;
    private HashMap<String,Boolean> plotRefreshed;
    private HashMap<MetricOfPlot,Integer> plotedCount;
    private HashMap<String,Integer> rhqMonitoringCount;
    private HashMap<String,Integer> hawkularMonitoringCount;
    private HashMap<String,HashMap<String,DbQueries>> dbQueries;

    public MetricInternalParameters() {
        this.plotHandler = new HashMap<>();
        this.plotRefreshed = new HashMap<>();
        this.plotedCount = new HashMap<>();
        this.rhqMonitoringCount = new HashMap<>();
        this.hawkularMonitoringCount = new HashMap<>();
        this.dbQueries = new HashMap<>();
    }

    public synchronized HashMap<String, Integer> getHawkularMonitoringCount() {
        return hawkularMonitoringCount;
    }
    
    public synchronized int getHawkularMonitoringCount(String metric) {
        return hawkularMonitoringCount.get(metric)!=null?hawkularMonitoringCount.get(metric):0;
    }

    public synchronized void setHawkularMonitoringCount(HashMap<String, Integer> hawkularMonitoringCount) {
        this.hawkularMonitoringCount = hawkularMonitoringCount;
    }
    
    public synchronized void putHawkularMonitoringCount(String metric, int value) {
        this.hawkularMonitoringCount.put(metric, value);
    }
    
    public synchronized void resetHawkularMonitoringCount(String metric) {
        this.hawkularMonitoringCount.put(metric, 0);
    }

    public synchronized HashMap<String, HashMap<String, DbQueries>> getDbQueries() {
        return dbQueries;
    }

    public synchronized void setDbQueries(HashMap<String, HashMap<String, DbQueries>> dbQueries) {
        this.dbQueries = dbQueries;
    }

    public synchronized HashMap<String, Integer> getPlotHandler() {
        return plotHandler;
    }

    public synchronized void setPlotHandler(HashMap<String, Integer> plotHandler) {
        this.plotHandler = plotHandler;
    }
    
    public synchronized void putPlotHandler(String plotName, int value) {
        this.plotHandler.put(plotName, value);
    }

    public synchronized HashMap<String, Boolean> getPlotRefreshed() {
        return plotRefreshed;
    }

    public synchronized void setPlotRefreshed(HashMap<String, Boolean> plotRefreshed) {
        this.plotRefreshed = plotRefreshed;
    }
    
    public synchronized void putPlotRefreshed(String plotName, boolean value) {
        this.plotRefreshed.put(plotName, value);
    }

    public synchronized HashMap<MetricOfPlot, Integer> getPlotedCount() {
        return plotedCount;
    }

    public synchronized void setPlotedCount(HashMap<MetricOfPlot, Integer> plotedCount) {
        this.plotedCount = plotedCount;
    }
     
    public synchronized void putPlotedCount(MetricOfPlot metricOfPlot, int value) {
        this.plotedCount.put(metricOfPlot, value);
    }
    
    public synchronized void resetPlotedCount(MetricOfPlot metricOfPlot) {
        this.plotedCount.put(metricOfPlot, 0);
    }

    public synchronized HashMap<String, Integer> getRhqMonitoringCount() {
        return rhqMonitoringCount;
    }
    
    public synchronized int getRhqMonitoringCount(String metric) {
        return rhqMonitoringCount.get(metric)!=null?rhqMonitoringCount.get(metric):0;
    }

    public synchronized void setRhqMonitoringCount(HashMap<String, Integer> rhqMonitoringCount) {
        this.rhqMonitoringCount = rhqMonitoringCount;
    }
    
    public synchronized void putRhqMonitoringCount(String metric, int value) {
        this.rhqMonitoringCount.put(metric, value);
    }
    
    public synchronized void resetRhqMonitoringCount(String metric) {
        this.rhqMonitoringCount.put(metric, 0);
    }

    public synchronized DbQueries getDbQueries(String user, String statement) {
        if (user == null) 
            user = "default";
        addDbQueries(user, statement);
        return dbQueries.get(user).get(statement);
    }

    public synchronized void addDbQueries(String user, String statement) {
        if (user == null) 
            user = "default";
        
        if (!this.dbQueries.containsKey(user))
            this.dbQueries.put(user, new HashMap<String, DbQueries>());
        
        if (!this.dbQueries.get(user).containsKey(statement))
            this.dbQueries.get(user).put(statement, new DbQueries(statement));
    }

}
