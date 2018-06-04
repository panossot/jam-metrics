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
import org.glassfish.jersey.server.ResourceConfig;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;

/**
 *
 * @author panos
 */
@Path("/Metrics")
public class RestJam extends ResourceConfig {
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
    
}
