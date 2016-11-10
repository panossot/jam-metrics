/*
 * Copyleft 2015  by Red Hat.
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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */

package org.jam.metrics.applicationmetricslibrary;

import java.util.HashMap;
import java.util.List;
import org.hawkular.apm.api.model.trace.Trace;

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
    private HashMap<String,List<Trace>> traceLists;
    private HashMap<String,Integer> traceListRefreshed;
    private HashMap<String,Integer> traceListProcessed;
    private HashMap<String,Integer> hawkularAmpTimestampUpdate;
    private HashMap<String,HawkularApmManagers> hawkularApmManagers;

    public MetricInternalParameters() {
        this.plotHandler = new HashMap<>();
        this.plotRefreshed = new HashMap<>();
        this.plotedCount = new HashMap<>();
        this.rhqMonitoringCount = new HashMap<>();
        this.hawkularMonitoringCount = new HashMap<>();
        this.dbQueries = new HashMap<>();
        this.traceLists = new HashMap<>();
        this.traceListRefreshed = new HashMap<>();
        this.traceListProcessed = new HashMap<>();
        this.hawkularAmpTimestampUpdate = new HashMap<>();
        this.hawkularApmManagers = new HashMap<>();
    }

    public synchronized void putHawkularApmManagers(String thread, HawkularApmManagers hawkularApmManagers) {
        this.hawkularApmManagers.put(thread, hawkularApmManagers);
    }

    public synchronized HawkularApmManagers getHawkularApmManagers(String thread) {
        return hawkularApmManagers.get(thread);
    }

    public synchronized void setHawkularApmManagers(HashMap<String, HawkularApmManagers> hawkularApmManagers) {
        this.hawkularApmManagers = hawkularApmManagers;
    }
    
    public synchronized int getHawkularAmpTimestampUpdate(String metricName) {
        if (hawkularAmpTimestampUpdate.get(metricName) == null)
            hawkularAmpTimestampUpdate.put(metricName, 1);
        
        return hawkularAmpTimestampUpdate.get(metricName);
    }

    public synchronized void setHawkularAmpTimestampUpdate(HashMap<String, Integer> hawkularampTimestampUpdate) {
        this.hawkularAmpTimestampUpdate = traceListProcessed;
    }
    
    public synchronized void putHawkularAmpTimestampUpdate(String metricName, int hawkularampTimestampUpdate) {
        this.hawkularAmpTimestampUpdate.put(metricName, hawkularampTimestampUpdate);
    }
    
    public synchronized void increaseHawkularAmpTimestampUpdate(String metricName) {
        if (this.hawkularAmpTimestampUpdate.get(metricName)==null)
            this.hawkularAmpTimestampUpdate.put(metricName, 1);
        else
            this.hawkularAmpTimestampUpdate.put(metricName, this.hawkularAmpTimestampUpdate.get(metricName)+1);
    }
    
    public synchronized HashMap<String, List<Trace>> getTraceLists() {
        return traceLists;
    }

    public synchronized void setTraceLists(HashMap<String, List<Trace>> traceLists) {
        this.traceLists = traceLists;
    }

    public synchronized HashMap<String, Integer> getTraceListRefreshed() {
        return traceListRefreshed;
    }

    public synchronized void setTraceListRefreshed(HashMap<String, Integer> traceListRefreshed) {
        this.traceListRefreshed = traceListRefreshed;
    }
    
    public synchronized void increaseTraceListRefreshed(String metricName) {
        if (this.traceListRefreshed.get(metricName)==null)
            this.traceListRefreshed.put(metricName, 1);
        else
            this.traceListRefreshed.put(metricName, this.traceListRefreshed.get(metricName)+1);
    }
    
    public synchronized void decreaseTraceListRefreshed(String metricName) {
        if (this.traceListRefreshed.get(metricName)==null)
            this.traceListRefreshed.put(metricName, 0);
        else
            this.traceListRefreshed.put(metricName, this.traceListRefreshed.get(metricName)-1);
    }
    
    public synchronized void putTraceListRefreshed(String metricName, int numTracesRefreshed) {
        this.traceListRefreshed.put(metricName, numTracesRefreshed);
    }

    public synchronized int getTraceListProcessed(String metricName) {
        if (traceListProcessed.get(metricName) == null)
            traceListProcessed.put(metricName, 0);
        
        return traceListProcessed.get(metricName);
    }

    public synchronized void setTraceListProcessed(HashMap<String, Integer> traceListProcessed) {
        this.traceListProcessed = traceListProcessed;
    }
    
    public synchronized void putTraceListProcessed(String metricName, int numTracesProcessed) {
        this.traceListProcessed.put(metricName, numTracesProcessed);
    }
    
    public synchronized void increaseTraceListProcessed(String metricName) {
        if (this.traceListProcessed.get(metricName) != null)
            this.traceListProcessed.put(metricName, this.traceListProcessed.get(metricName)+1);
        else
            this.traceListProcessed.put(metricName, 1);
    }

    public synchronized List<Trace> getTraceList(String metricName) {
        return traceLists.get(metricName);
    }

    public synchronized void putTraceList(String metricName, List<Trace> traceList) {
        this.traceLists.put(metricName, traceList);
    }

    public synchronized int getTraceListRefreshed(String metricName) {
        return traceListRefreshed.get(metricName);
    }

    public synchronized void setTraceListRefreshed(String metricName, int newTracesNum) {
        this.traceListRefreshed.put(metricName, newTracesNum);
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
