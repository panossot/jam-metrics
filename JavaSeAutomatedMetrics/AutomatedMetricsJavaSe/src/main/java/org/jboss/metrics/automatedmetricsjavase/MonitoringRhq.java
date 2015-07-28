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
package org.jboss.metrics.automatedmetricsjavase;

import org.jboss.logging.Logger;
import org.jboss.metrics.automatedmetrics.utils.DoubleValue;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MonitoringRhq {

    private final String APPLICATION_JSON;
    private final int REST_SERVER_PORT;
    private final String REST_SERVER_ADDRESS;
    private final String REST_SERVER_USERNAME;
    private final String REST_SERVER_PASSWORD;
    private final PostDataRhq postRhq;

    private Logger logger = Logger.getLogger(MonitoringRhq.class);

    public MonitoringRhq(String deployment) {

        MetricProperties metricProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(deployment);
        APPLICATION_JSON = "application/json";
        REST_SERVER_PORT = Integer.parseInt(metricProperties.getRhqServerPort());
        REST_SERVER_USERNAME = metricProperties.getRhqServerUsername();
        REST_SERVER_PASSWORD = metricProperties.getRhqServerPassword();
        REST_SERVER_ADDRESS = metricProperties.getRhqServerUrl();

        ResteasyClient client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(5).build();
        ResteasyWebTarget target = client.target("http://" + REST_SERVER_ADDRESS + ":" + REST_SERVER_PORT);
        target.register(new BasicAuthentication(REST_SERVER_USERNAME, REST_SERVER_PASSWORD));
        postRhq = target.proxy(PostDataRhq.class);

    }

    public boolean rhqMonitoring(Object target, Object value, String metricName, String deployment) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;
        String metricIdLoaded = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(deployment).getRhqScheduleId(metricName);

        if (metricIdLoaded != null) {
            int numericScheduleId = Integer.parseInt(metricIdLoaded);
            long now = System.currentTimeMillis();

            DoubleValue dataPoint = new DoubleValue(Double.parseDouble(value.toString()));
            try {
                postRhq.postDataRhq(dataPoint, numericScheduleId, now, APPLICATION_JSON);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        return dataSent;
    }

}
