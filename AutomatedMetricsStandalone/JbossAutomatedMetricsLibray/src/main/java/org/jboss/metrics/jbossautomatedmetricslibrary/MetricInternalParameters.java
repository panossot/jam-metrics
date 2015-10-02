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

    public MetricInternalParameters() {
        this.plotHandler = new HashMap<>();
        this.plotRefreshed = new HashMap<>();
        this.plotedCount = new HashMap<>();
    }

    public HashMap<String, Integer> getPlotHandler() {
        return plotHandler;
    }

    public void setPlotHandler(HashMap<String, Integer> plotHandler) {
        this.plotHandler = plotHandler;
    }
    
    public void putPlotHandler(String plotName, int value) {
        this.plotHandler.put(plotName, value);
    }

    public HashMap<String, Boolean> getPlotRefreshed() {
        return plotRefreshed;
    }

    public void setPlotRefreshed(HashMap<String, Boolean> plotRefreshed) {
        this.plotRefreshed = plotRefreshed;
    }
    
    public void putPlotRefreshed(String plotName, boolean value) {
        this.plotRefreshed.put(plotName, value);
    }

    public HashMap<MetricOfPlot, Integer> getPlotedCount() {
        return plotedCount;
    }

    public void setPlotedCount(HashMap<MetricOfPlot, Integer> plotedCount) {
        this.plotedCount = plotedCount;
    }
     
    public void putPlotedCount(MetricOfPlot metricOfPlot, int value) {
        this.plotedCount.put(metricOfPlot, value);
    }
    
    public void initializePlotedCount(MetricOfPlot metricOfPlot) {
        if (this.plotedCount.get(metricOfPlot) == null)
            this.plotedCount.put(metricOfPlot, 0);
    }
}
