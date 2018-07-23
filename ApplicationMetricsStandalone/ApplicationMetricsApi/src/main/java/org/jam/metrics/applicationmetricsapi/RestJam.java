/*
 * Copyright 2018 panos.
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
package org.jam.metrics.applicationmetricsapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;

/**
 *
 * @author panos
 */
@Path("/Metrics")
public class RestJam {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricList/{metricGroup}/print")
    public Response printMetricList(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsCacheApi.printMetricsCache(metricGroup);

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricList/{metricGroup}")
    public Response metricList(@PathParam("metricGroup") String metricGroup) {

        MetricsCache response = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(metricGroup);

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/cacheEnabled")
    public Response metricGetCacheStore(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getCacheStore();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/cacheEnabled")
    public Response metricSetCacheStore(@PathParam("metricGroup") String metricGroup, @QueryParam("enableCacheStore") String enableCacheStore) {

        MetricsPropertiesApi.getProperties(metricGroup).setCacheStore(enableCacheStore);

        if(MetricsPropertiesApi.getProperties(metricGroup).getCacheStore().compareTo(enableCacheStore)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqMonitoring")
    public Response metricGetRhqMonitoring(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getRhqMonitoring();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqMonitoring")
    public Response metricSetRhqMonitoring(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqMonitoringEnabled") String rhqMonitoringEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setRhqMonitoring(rhqMonitoringEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqMonitoring().compareTo(rhqMonitoringEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularMonitoring")
    public Response metricGetHawkularMonitoring(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularMonitoring();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularMonitoring")
    public Response metricSetHawkularMonitoring(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularMonitoringEnabled") String hawkularMonitoringEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularMonitoring(hawkularMonitoringEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularMonitoring().compareTo(hawkularMonitoringEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/databaseStore")
    public Response metricGetDatabaseStore(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getDatabaseStore();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/databaseStore")
    public Response metricSetDatabaseStore(@PathParam("metricGroup") String metricGroup, @QueryParam("databaseStoreEnabled") String databaseStoreEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setDatabaseStore(databaseStoreEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getDatabaseStore().compareTo(databaseStoreEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularApm")
    public Response metricGetHawkularApm(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularApm();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularApm")
    public Response metricSetHawkularApm(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularApmEnabled") String hawkularApmEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularApm(hawkularApmEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularApm().compareTo(hawkularApmEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/metricPlot")
    public Response metricGetMetricPlot(@PathParam("metricGroup") String metricPlot) {

        String response = MetricsPropertiesApi.getProperties(metricPlot).getMetricPlot();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/metricPlot")
    public Response metricSetMetricPlot(@PathParam("metricGroup") String metricGroup, @QueryParam("metricPlotEnabled") String metricPlotEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setMetricPlot(metricPlotEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getDatabaseStore().compareTo(metricPlotEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/OpenAnalytics")
    public Response metricGetOpenAnalytics(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getOpenAnalytics();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/OpenAnalytics")
    public Response metricSetOpenAnalytics(@PathParam("metricGroup") String metricGroup, @QueryParam("OpenAnalyticsEnabled") String OpenAnalyticsEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setOpenAnalytics(OpenAnalyticsEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getOpenAnalytics().compareTo(OpenAnalyticsEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/filterMetrics")
    public Response metricGetFilterMetrics(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getFilterMetrics();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/filterMetrics")
    public Response metricSetFilterMetrics(@PathParam("metricGroup") String metricGroup, @QueryParam("filterMetricsEnabled") String filterMetricsEnabled) {

        MetricsPropertiesApi.getProperties(metricGroup).setFilterMetrics(filterMetricsEnabled);

        if(MetricsPropertiesApi.getProperties(metricGroup).getFilterMetrics().compareTo(filterMetricsEnabled)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/cacheMaxSize")
    public Response metricGetCacheMaxSize(@PathParam("metricGroup") String metricGroup) {

        int response = MetricsPropertiesApi.getProperties(metricGroup).getCacheMaxSize();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/cacheMaxSize")
    public Response metricSetCacheMaxSize(@PathParam("metricGroup") String metricGroup, @QueryParam("cacheMaxSize") int cacheMaxSize) {

        MetricsPropertiesApi.getProperties(metricGroup).setCacheMaxSize(cacheMaxSize);

        if(MetricsPropertiesApi.getProperties(metricGroup).getCacheMaxSize()==cacheMaxSize)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/plotRefreshRate")
    public Response metricGetPlotRefreshRate(@PathParam("metricGroup") String metricGroup) {

        int response = MetricsPropertiesApi.getProperties(metricGroup).getPlotRefreshRate();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/plotRefreshRate")
    public Response metricSetPlotRefreshRate(@PathParam("metricGroup") String metricGroup, @QueryParam("plotRefreshRate") int plotRefreshRate) {

        MetricsPropertiesApi.getProperties(metricGroup).setPlotRefreshRate(plotRefreshRate);

        if(MetricsPropertiesApi.getProperties(metricGroup).getPlotRefreshRate()==plotRefreshRate)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqMonitoringRefreshRate")
    public Response metricGetRhqMonitoringRefreshRate(@PathParam("metricGroup") String metricGroup) {

        int response = MetricsPropertiesApi.getProperties(metricGroup).getRhqMonitoringRefreshRate();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqMonitoringRefreshRate")
    public Response metricSetRhqMonitoringRefreshRate(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqMonitoringRefreshRate") int rhqMonitoringRefreshRate) {

        MetricsPropertiesApi.getProperties(metricGroup).setRhqMonitoringRefreshRate(rhqMonitoringRefreshRate);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqMonitoringRefreshRate()==rhqMonitoringRefreshRate)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqServerUrl")
    public Response metricGetRhqServerUrl(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getRhqServerUrl();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqServerUrl")
    public Response metricSetRhqServerUrl(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqServerUrl") String rhqServerUrl) {

        MetricsPropertiesApi.getProperties(metricGroup).setRhqServerUrl(rhqServerUrl);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqServerUrl().compareTo(rhqServerUrl)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqServerPort")
    public Response metricGetRhqServerPort(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getRhqServerPort();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqServerPort")
    public Response metricSetRhqServerPort(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqServerPort") String rhqServerPort) {

        MetricsPropertiesApi.getProperties(metricGroup).setRhqServerPort(rhqServerPort);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqServerPort().compareTo(rhqServerPort)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqServerUsername")
    public Response metricGetRhqServerUsername(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getRhqServerUsername();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqServerUsername")
    public Response metricSetRhqServerUsername(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqServerUsername") String rhqServerUsername) {

        MetricsPropertiesApi.getProperties(metricGroup).setRhqServerUsername(rhqServerUsername);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqServerUsername().compareTo(rhqServerUsername)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqServerPassword")
    public Response metricGetRhqServerPassword(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getRhqServerPassword();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqServerPassword")
    public Response metricSetRhqServerPassword(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqServerPassword") String rhqServerPassword) {

        MetricsPropertiesApi.getProperties(metricGroup).setRhqServerPassword(rhqServerPassword);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqServerPassword().compareTo(rhqServerPassword)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/rhqScheduleId")
    public Response metricGetRhqScheduleId(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqScheduleIdName") String rhqScheduleIdName) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getRhqScheduleId(rhqScheduleIdName);

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/rhqScheduleId")
    public Response metricSetRhqScheduleId(@PathParam("metricGroup") String metricGroup, @QueryParam("rhqScheduleIdName") String rhqScheduleIdName, @QueryParam("rhqScheduleId") String rhqScheduleId) {

        MetricsPropertiesApi.getProperties(metricGroup).addRhqScheduleId(rhqScheduleIdName, rhqScheduleId);

        if(MetricsPropertiesApi.getProperties(metricGroup).getRhqScheduleId(rhqScheduleIdName).compareTo(rhqScheduleId)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularMonitoringRefreshRate")
    public Response metricGetHawkularMonitoringRefreshRate(@PathParam("metricGroup") String metricGroup) {

        int response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularMonitoringRefreshRate();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularMonitoringRefreshRate")
    public Response metricSetHawkularMonitoringRefreshRate(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularMonitoringRefreshRate") int hawkularMonitoringRefreshRate) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularMonitoringRefreshRate(hawkularMonitoringRefreshRate);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularMonitoringRefreshRate()==hawkularMonitoringRefreshRate)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularServerUrl")
    public Response metricGetHawkularServerUrl(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerUrl();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularServerUrl")
    public Response metricSetHawkularServerUrl(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularServerUrl") String hawkularServerUrl) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularServerUrl(hawkularServerUrl);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerUrl().compareTo(hawkularServerUrl)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularServerPort")
    public Response metricGetHawkularServerPort(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerPort();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularServerPort")
    public Response metricSetHawkularServerPort(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularServerPort") String hawkularServerPort) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularServerPort(hawkularServerPort);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerPort().compareTo(hawkularServerPort)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularServerUsername")
    public Response metricGetHawkularServerUsername(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerUsername();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularServerUsername")
    public Response metricSetHawkularServerUsername(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularServerUsername") String hawkularServerUsername) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularServerUsername(hawkularServerUsername);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerUsername().compareTo(hawkularServerUsername)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularServerPassword")
    public Response metricGetHawkularServerPassword(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerPassword();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularServerPassword")
    public Response metricSetHawkularServerPassword(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularServerPassword") String hawkularServerPassword) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularServerPassword(hawkularServerPassword);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularServerPassword().compareTo(hawkularServerPassword)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularTenant")
    public Response metricGetHawkularTenant(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularTenant();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularTenant")
    public Response metricSetHawkularTenant(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularTenant") String hawkularTenant) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularTenant(hawkularTenant);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularTenant().compareTo(hawkularTenant)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularApmServerUrl")
    public Response metricGetHawkularApmServerUrl(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerUrl();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularApmServerUrl")
    public Response metricSetHawkularApmServerUrl(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularApmServerUrl") String hawkularApmServerUrl) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularApmServerUrl(hawkularApmServerUrl);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerUrl().compareTo(hawkularApmServerUrl)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularApmServerPort")
    public Response metricGetHawkularApmServerPort(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerPort();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularApmServerPort")
    public Response metricSetHawkularApmServerPort(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularApmServerPort") String hawkularApmServerPort) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularApmServerPort(hawkularApmServerPort);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerPort().compareTo(hawkularApmServerPort)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularApmServerUsername")
    public Response metricGetHawkularApmServerUsername(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerUsername();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularApmServerUsername")
    public Response metricSetHawkularApmServerUsername(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularApmServerUsername") String hawkularApmServerUsername) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularApmServerUsername(hawkularApmServerUsername);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerUsername().compareTo(hawkularApmServerUsername)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularApmServerPassword")
    public Response metricGetHawkularApmServerPassword(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerPassword();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularApmServerPassword")
    public Response metricSetHawkularApmServerPassword(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularApmServerPassword") String hawkularApmServerPassword) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularApmServerPassword(hawkularApmServerPassword);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmServerPassword().compareTo(hawkularApmServerPassword)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/get/{metricGroup}/hawkularApmTenant")
    public Response metricGetHawkularApmTenant(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmTenant();

        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricProperties/set/{metricGroup}/hawkularApmTenant")
    public Response metricSetHawkularApmTenant(@PathParam("metricGroup") String metricGroup, @QueryParam("hawkularApmTenant") String hawkularApmTenant) {

        MetricsPropertiesApi.getProperties(metricGroup).setHawkularApmTenant(hawkularApmTenant);

        if(MetricsPropertiesApi.getProperties(metricGroup).getHawkularApmTenant().compareTo(hawkularApmTenant)==0)
            return Response.status(200).build();
        else
            return Response.status(501).build();
    }
}
