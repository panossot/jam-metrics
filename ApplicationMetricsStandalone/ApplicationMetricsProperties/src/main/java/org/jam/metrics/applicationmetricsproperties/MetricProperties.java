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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */

package org.jam.metrics.applicationmetricsproperties;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import java.awt.Color;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JFrame;
import org.hawkular.apm.api.model.trace.Trace;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricProperties {
 
    private String rhqMonitoring = "false";
    private String hawkularMonitoring = "false";
    private String hawkularApm = "false";
    private String hawkularMetricsApm = "false";
    private String cacheStore = "false";
    private String OpenAnalytics = "false";
    private int cacheMaxSize = Integer.MAX_VALUE;
    private String databaseStore = "false";
    private String filterMetrics = "false";
    private String metricPlot = "false";
    private HashMap<String,Statement> databaseStatement;
    private HashMap<String,String> updateDbQueries;
    private HashMap<String,Integer> updateRateOfDbQueries;
    private HashMap<String,Trace> hawkularApmTraces;
    private HashMap<String,Plot2DPanel> plots;
    private HashMap<String,Plot3DPanel> plots3D;
    private HashMap<String,JFrame> frames;
    private HashMap<String,Color> colors;
    private int plotRefreshRate = 1;
    private int rhqMonitoringRefreshRate = 1;
    private String rhqServerUrl = "localhost";
    private String rhqServerPort = "7080";
    private String rhqServerUsername = "rhqadmin";
    private String rhqServerPassword = "rhqadmin";
    private HashMap<String,String> rhqScheduleIds;
    private int hawkularMonitoringRefreshRate = 1;
    private String hawkularServerUrl = "localhost";
    private String hawkularServerPort = "8080";
    private String hawkularServerUsername = "hawkularadmin";
    private String hawkularServerPassword = "hawkularadmin";
    private String hawkularTenant = "hawkular";
    private int hawkularApmRefreshRate = 2;
    private int hawkularApmVisibleTraces = 4;
    private String hawkularApmServerUrl = "localhost";
    private String hawkularApmServerPort = "8080";
    private String hawkularApmServerUsername = "hawkularapmadmin";
    private String hawkularApmServerPassword = "hawkularapmadmin";
    private String hawkularApmTenant = "my-tenant";
    private EventBus eb = null;

    public MetricProperties() {
        rhqScheduleIds = new HashMap<>();
        databaseStatement = new HashMap<>();
        updateRateOfDbQueries = new HashMap<>();
        updateDbQueries = new HashMap<>();
        plots = new HashMap<>();
        plots3D = new HashMap<>();
        colors = new HashMap<>();
        frames = new HashMap<>();
        hawkularApmTraces = new HashMap<>();
    }

    public EventBus getEventBus() {
        return eb;
    }

    public void setEventBus(EventBus eb) {
        this.eb = eb;
    }

    @Deprecated
    public String getHawkularMetricsApm() {
        return hawkularMetricsApm;
    }

    @Deprecated
    public void setHawkularMetricsApm(String hawkularMetricsApm) {
        this.hawkularMetricsApm = hawkularMetricsApm;
    } 

    @Deprecated
    public int getHawkularApmVisibleTraces() {
        return hawkularApmVisibleTraces;
    }

    @Deprecated
    public void setHawkularApmVisibleTraces(int hawkularApmVisibleTraces) {
        this.hawkularApmVisibleTraces = hawkularApmVisibleTraces;
    }

    @Deprecated
    public HashMap<String, Trace> getHawkularApmTraces() {
        return hawkularApmTraces;
    }

    @Deprecated
    public void setHawkularApmTraces(HashMap<String, Trace> hawkularApmTraces) {
        this.hawkularApmTraces = hawkularApmTraces;
    }
    
    @Deprecated
    public void addHawkularApmTraces(String traceName, Trace trace) {
        this.hawkularApmTraces.put(traceName, trace);
    }

    public String getHawkularApm() {
        return hawkularApm;
    }

    public void setHawkularApm(String hawkularApm) {
        this.hawkularApm = hawkularApm;
    }

    public int getHawkularApmRefreshRate() {
        return hawkularApmRefreshRate;
    }

    public void setHawkularApmRefreshRate(int hawkularApmRefreshRate) {
        this.hawkularApmRefreshRate = hawkularApmRefreshRate;
    }

    public String getHawkularApmServerUrl() {
        return hawkularApmServerUrl;
    }

    public void setHawkularApmServerUrl(String hawkularApmServerUrl) {
        this.hawkularApmServerUrl = hawkularApmServerUrl;
    }

    public String getHawkularApmServerPort() {
        return hawkularApmServerPort;
    }

    public void setHawkularApmServerPort(String hawkularApmServerPort) {
        this.hawkularApmServerPort = hawkularApmServerPort;
    }

    public String getHawkularApmServerUsername() {
        return hawkularApmServerUsername;
    }

    public void setHawkularApmServerUsername(String hawkularApmServerUsername) {
        this.hawkularApmServerUsername = hawkularApmServerUsername;
    }

    public String getHawkularApmServerPassword() {
        return hawkularApmServerPassword;
    }

    public void setHawkularApmServerPassword(String hawkularApmServerPassword) {
        this.hawkularApmServerPassword = hawkularApmServerPassword;
    }

    public String getHawkularApmTenant() {
        return hawkularApmTenant;
    }

    public void setHawkularApmTenant(String hawkularApmTenant) {
        this.hawkularApmTenant = hawkularApmTenant;
    }

    public synchronized String getHawkularMonitoring() {
        return hawkularMonitoring;
    }

    public synchronized void setHawkularMonitoring(String hawkularMonitoring) {
        this.hawkularMonitoring = hawkularMonitoring;
    }

    public synchronized int getHawkularMonitoringRefreshRate() {
        return hawkularMonitoringRefreshRate;
    }

    public synchronized void setHawkularMonitoringRefreshRate(int hawkularMonitoringRefreshRate) {
        this.hawkularMonitoringRefreshRate = hawkularMonitoringRefreshRate;
    }

    public synchronized String getHawkularServerUrl() {
        return hawkularServerUrl;
    }

    public synchronized void setHawkularServerUrl(String hawkularServerUrl) {
        this.hawkularServerUrl = hawkularServerUrl;
    }

    public synchronized String getHawkularServerPort() {
        return hawkularServerPort;
    }

    public synchronized void setHawkularServerPort(String hawkularServerPort) {
        this.hawkularServerPort = hawkularServerPort;
    }

    public synchronized String getHawkularServerUsername() {
        return hawkularServerUsername;
    }

    public synchronized void setHawkularServerUsername(String hawkularServerUsername) {
        this.hawkularServerUsername = hawkularServerUsername;
    }

    public synchronized String getHawkularServerPassword() {
        return hawkularServerPassword;
    }

    public synchronized void setHawkularServerPassword(String hawkularServerPassword) {
        this.hawkularServerPassword = hawkularServerPassword;
    }

    public synchronized String getHawkularTenant() {
        return hawkularTenant;
    }

    public synchronized void setHawkularTenant(String hawkularTenant) {
        this.hawkularTenant = hawkularTenant;
    }
    
    public synchronized String getRhqMonitoring() {
        return rhqMonitoring;
    }

    public synchronized void setRhqMonitoring(String rhqMonitoring) {
        this.rhqMonitoring = rhqMonitoring;
    }

    public synchronized String getCacheStore() {
        return cacheStore;
    }

    public synchronized void setCacheStore(String cacheStore) {
        this.cacheStore = cacheStore;
    }

    public String getFilterMetrics() {
        return filterMetrics;
    }

    public void setFilterMetrics(String filterMetrics) {
        this.filterMetrics = filterMetrics;
    }

    public synchronized HashMap<String, String> getRhqScheduleIds() {
        return rhqScheduleIds;
    }

    public synchronized void setRhqScheduleIds(HashMap<String, String> rhqScheduleIds) {
        this.rhqScheduleIds = rhqScheduleIds;
    }
    
    public synchronized String getRhqScheduleId(String name) {
        return(this.rhqScheduleIds.get(name));
    }

    public synchronized String getRhqServerUrl() {
        return rhqServerUrl;
    }

    public synchronized void setRhqServerUrl(String rhqServerUrl) {
        this.rhqServerUrl = rhqServerUrl;
    }

    public synchronized String getRhqServerPort() {
        return rhqServerPort;
    }

    public synchronized void setRhqServerPort(String rhqServerPort) {
        this.rhqServerPort = rhqServerPort;
    }

    public synchronized String getRhqServerUsername() {
        return rhqServerUsername;
    }

    public synchronized void setRhqServerUsername(String rhqServerUsername) {
        this.rhqServerUsername = rhqServerUsername;
    }

    public synchronized String getRhqServerPassword() {
        return rhqServerPassword;
    }

    public synchronized void setRhqServerPassword(String rhqServerPassword) {
        this.rhqServerPassword = rhqServerPassword;
    }
    
    public synchronized void addRhqScheduleId(String name, String id) {
        this.rhqScheduleIds.put(name, id);
    }
    
    public synchronized void removeRhqScheduleId(String name) {
        this.rhqScheduleIds.remove(name);
    }

    public synchronized String getOpenAnalytics() {
        return OpenAnalytics;
    }

    public void setOpenAnalytics(String OpenAnalytics) {
        this.OpenAnalytics = OpenAnalytics;
    }

    public synchronized String getDatabaseStore() {
        return databaseStore;
    }

    public synchronized void setDatabaseStore(String databaseStore) {
        this.databaseStore = databaseStore;
    }

    public synchronized HashMap<String, Statement> getDatabaseStatement() {
        return databaseStatement;
    }

    public synchronized void setDatabaseStatement(HashMap<String, Statement> databaseStatement) {
        this.databaseStatement = databaseStatement;
    }

    public synchronized HashMap<String, Integer> getUpdateRateOfDbQueries() {
        return updateRateOfDbQueries;
    }
    
    public synchronized int getUpdateRateOfDbQuery(String statement) {
        if (!this.updateRateOfDbQueries.containsKey(statement))
            this.updateRateOfDbQueries.put(statement, 1);
        
        return updateRateOfDbQueries.get(statement);
    }

    public synchronized void setUpdateRateOfDbQueries(HashMap<String, Integer> updateDbQueries) {
        this.updateRateOfDbQueries = updateDbQueries;
    }
    
    public synchronized void setUpdateRateOfDbQuery(String statement, int value) {
        this.updateRateOfDbQueries.put(statement,value);
    }

    public HashMap<String, String> getUpdateDbQueries() {
        return updateDbQueries;
    }

    public void setUpdateDbQueries(HashMap<String, String> updateDbQueries) {
        this.updateDbQueries = updateDbQueries;
    }

    public synchronized String getMetricPlot() {
        return metricPlot;
    }

    public synchronized void setMetricPlot(String metricPlot) {
        this.metricPlot = metricPlot;
    }
    
    public synchronized HashMap<String, Plot3DPanel> get3DPlots() {
        return plots3D;
    }

    public synchronized void set3DPlots(HashMap<String, Plot3DPanel> plots) {
        this.plots3D = plots;
    }

    public synchronized void add3DPlot(String name, Plot3DPanel plot) {
        this.plots3D.put(name, plot);
    }
    
    public synchronized void remove3DPlot(String name) {
        this.plots3D.remove(name);
    }

    public synchronized HashMap<String, Plot2DPanel> getPlots() {
        return plots;
    }

    public synchronized void setPlots(HashMap<String, Plot2DPanel> plots) {
        this.plots = plots;
    }

    public synchronized void addPlot(String name, Plot2DPanel plot) {
        this.plots.put(name, plot);
    }
    
    public synchronized void removePlot(String name) {
        this.plots.remove(name);
    }

    public synchronized int getPlotRefreshRate() {
        return plotRefreshRate;
    }

    public synchronized void setPlotRefreshRate(int plotRefreshRate) {
        this.plotRefreshRate = plotRefreshRate;
    }

    public synchronized HashMap<String, Color> getColors() {
        return colors;
    }

    public synchronized void setColors(HashMap<String, Color> colors) {
        this.colors = colors;
    }
    
    public synchronized void addColor(String name, Color color) {
        this.colors.put(name, color);
    }
    
    public synchronized void removeColor(String name) {
        this.colors.remove(name);
    }
    
    public synchronized HashMap<String, JFrame> getFrames() {
        return frames;
    }

    public synchronized void setFrames(HashMap<String, JFrame> frames) {
        this.frames = frames;
    }

    public synchronized void addFrame(String name, JFrame frame) {
        this.frames.put(name, frame);
    }
    
    public synchronized void removeFrame(String name) {
        this.frames.remove(name);
    }

    public synchronized int getRhqMonitoringRefreshRate() {
        return rhqMonitoringRefreshRate;
    }

    public synchronized void setRhqMonitoringRefreshRate(int rhqMonitoringRefreshRate) {
        this.rhqMonitoringRefreshRate = rhqMonitoringRefreshRate;
    }

    public synchronized int getCacheMaxSize() {
        return cacheMaxSize;
    }

    public synchronized void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }
}
