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
package org.jam.metrics.applicationmetricsjavase;

import java.util.ArrayList;
import java.util.List;
import org.hawkular.apm.api.model.trace.Trace;
import org.jboss.logging.Logger;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MonitoringHawkularApm {

    private final String APPLICATION_JSON;
    private final int REST_SERVER_PORT;
    private final String REST_SERVER_ADDRESS;
    private final String REST_SERVER_USERNAME;
    private final String REST_SERVER_PASSWORD;
    private final PostDataHawkularApm postHawkularApm;
   

    private Logger logger = Logger.getLogger(MonitoringHawkularApm.class);

    public MonitoringHawkularApm(String deployment) {

        MetricProperties metricProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(deployment);
        APPLICATION_JSON = "application/json";
        REST_SERVER_PORT = Integer.parseInt(metricProperties.getHawkularApmServerPort());
        REST_SERVER_USERNAME = metricProperties.getHawkularApmServerUsername();
        REST_SERVER_PASSWORD = metricProperties.getHawkularApmServerPassword();
        REST_SERVER_ADDRESS = metricProperties.getHawkularApmServerUrl();

        ResteasyClient client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(5).build();
        ResteasyWebTarget target = client.target("http://" + REST_SERVER_ADDRESS + ":" + REST_SERVER_PORT);
        target.register(new BasicAuthentication(REST_SERVER_USERNAME, REST_SERVER_PASSWORD));
        postHawkularApm = target.proxy(PostDataHawkularApm.class);

    }

    public synchronized boolean hawkularApm(String fieldName, Trace trace, String tenant, String group) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;

        try {
            MetricInternalParameters internalParameters = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group);
            List<Trace> traceList = internalParameters.getTraceList(fieldName);
            int hawkularApmRefreshRate = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getHawkularApmRefreshRate();
            int hawkularApmVisibleTraces = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getHawkularApmVisibleTraces();
            
            if (traceList==null) {
                traceList = new ArrayList();
            }
            if (traceList.size()==hawkularApmVisibleTraces)
                traceList.remove(0);
            
            traceList.add(trace);
            internalParameters.increaseTraceListRefreshed(fieldName);
            internalParameters.putTraceList(fieldName, traceList);
            
            if (internalParameters.getTraceListRefreshed(fieldName) == hawkularApmRefreshRate) {
                traceList.get(0).getNodes().get(0).setCorrelationIds(null);
                double timestampUpdateRate;
                
                if (hawkularApmVisibleTraces > hawkularApmRefreshRate) {
                    timestampUpdateRate = hawkularApmVisibleTraces/hawkularApmRefreshRate;
                } else {
                    timestampUpdateRate = hawkularApmVisibleTraces;
                }
                
                if (timestampUpdateRate >= internalParameters.getHawkularAmpTimestampUpdate(fieldName)) {
                    postHawkularApm.postDataHawkularApm(traceList, APPLICATION_JSON, tenant, APPLICATION_JSON);
                    internalParameters.increaseHawkularAmpTimestampUpdate(fieldName);
                } else {
                    postHawkularApm.postDataHawkularApm(traceList, APPLICATION_JSON, tenant, APPLICATION_JSON, System.currentTimeMillis());
                    internalParameters.putHawkularAmpTimestampUpdate(fieldName, 1);
                }
                    
                internalParameters.putTraceListRefreshed(fieldName, 0);
                dataSent = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSent;
    }

}
