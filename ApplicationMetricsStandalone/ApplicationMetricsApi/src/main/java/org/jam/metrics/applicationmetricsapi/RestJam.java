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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author panos
 */
@Path("/MetricsCache")
public class RestJam extends ResourceConfig {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MetricList/{metricGroup}")
    public Response printMetricList(@PathParam("metricGroup") String metricGroup) {

        String response = MetricsCacheApi.printMetricsCache(metricGroup);

        return Response.status(200).entity(response).build();
    }
}
