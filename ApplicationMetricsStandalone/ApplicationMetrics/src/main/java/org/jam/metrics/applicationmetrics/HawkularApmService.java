/*
 * Copyleft 2016 Red Hat, Inc. and/or its affiliates
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
package org.jam.metrics.applicationmetrics;

import java.util.List;
import org.hawkular.apm.api.model.trace.Trace;
import org.hawkular.apm.trace.publisher.rest.client.TracePublisherRESTClient;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.logging.Logger;

/**
 *
 * @author panos
 */
public class HawkularApmService {
    private final int REST_SERVER_PORT;
    private final String REST_SERVER_ADDRESS;
    private final String REST_SERVER_USERNAME;
    private final String REST_SERVER_PASSWORD;
    TracePublisherRESTClient tprc;

    private Logger logger = Logger.getLogger(HawkularApmService.class);

    public HawkularApmService(String deployment) {

        MetricProperties metricProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(deployment);
        REST_SERVER_PORT = Integer.parseInt(metricProperties.getHawkularApmServerPort());
        REST_SERVER_USERNAME = metricProperties.getHawkularApmServerUsername();
        REST_SERVER_PASSWORD = metricProperties.getHawkularApmServerPassword();
        REST_SERVER_ADDRESS = metricProperties.getHawkularApmServerUrl();

        
        tprc = new TracePublisherRESTClient();
        tprc.setPassword(REST_SERVER_PASSWORD);
        tprc.setUsername(REST_SERVER_USERNAME);
        tprc.setUri("http://" + REST_SERVER_ADDRESS + ":" + REST_SERVER_PORT + "/");

    }

    public synchronized boolean hawkularApm(List<Trace> traces, String tenant, String group) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;

        try {
            if (traces.size()!=0)
                tprc.publish(tenant, traces);
            dataSent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSent;
    }
}
