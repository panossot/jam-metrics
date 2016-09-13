/*
 * Copyleft 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org.icenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jam.metrics.applicationmetrics;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import org.jam.metrics.applicationmetrics.utils.DataPoint;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Path("/hawkular/metrics/gauges/{metric}/raw")
public interface PostDataHawkular {

    @POST
    @Consumes("application/json")
    void postArrayDataHawkular(List<DataPoint> data, @PathParam("metric") String metric, @HeaderParam(HttpHeaders.ACCEPT) String accept, @HeaderParam("Hawkular-Tenant") String tenant);
}
