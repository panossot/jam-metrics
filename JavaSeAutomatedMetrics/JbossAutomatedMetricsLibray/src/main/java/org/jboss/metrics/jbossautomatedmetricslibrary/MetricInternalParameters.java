/*
 * Copyright 2015 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.metrics.jbossautomatedmetricslibrary;

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

    public MetricInternalParameters() {
        this.plotHandler = new HashMap<>();
        this.plotRefreshed = new HashMap<>();
        this.plotedCount = new HashMap<>();
        this.rhqMonitoringCount = new HashMap<>();
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
}