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

package org.jam.metrics.applicationmetrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jboss.logging.Logger;
import org.jam.metrics.applicationmetrics.utils.DataPoint;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MonitoringHawkular {

    private final String APPLICATION_JSON;
    private final int REST_SERVER_PORT;
    private final String REST_SERVER_ADDRESS;
    private final String REST_SERVER_USERNAME;
    private final String REST_SERVER_PASSWORD;
    private final PostDataHawkular postHawkular;

    private Logger logger = Logger.getLogger(MonitoringHawkular.class);
    private static long nowHistory = 0;
    private static Object historyLock = new Object();

    public MonitoringHawkular(String deployment) {

        MetricProperties metricProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(deployment);
        APPLICATION_JSON = "application/json";
        REST_SERVER_PORT = Integer.parseInt(metricProperties.getHawkularServerPort());
        REST_SERVER_USERNAME = metricProperties.getHawkularServerUsername();
        REST_SERVER_PASSWORD = metricProperties.getHawkularServerPassword();
        REST_SERVER_ADDRESS = metricProperties.getHawkularServerUrl();

        ResteasyClient client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(5).build();
        ResteasyWebTarget target = client.target("http://" + REST_SERVER_ADDRESS + ":" + REST_SERVER_PORT);
        target.register(new BasicAuthentication(REST_SERVER_USERNAME, REST_SERVER_PASSWORD));
        postHawkular = target.proxy(PostDataHawkular.class);

    }

    public synchronized boolean hawkularMonitoring(Object target, String fieldName, String tenant, String group) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;
        String name = fieldName + "_" + target;
        MetricsCache metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
        int metricsHawkularMonitored = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getHawkularMonitoringCount(name);
        int refreshHawkularRate = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getHawkularMonitoringRefreshRate();
        List<Object> metricValues = metricsCacheInstance.searchMetricObject(name).getMetric();
        int metricCount = metricValues.size();
                
        long now = System.currentTimeMillis();
            
        synchronized(historyLock) {
            if (now<nowHistory)
                now = nowHistory+1;
        }

        if(metricCount >= metricsHawkularMonitored + refreshHawkularRate) {
            List<DataPoint> points = new ArrayList<>(refreshHawkularRate);
                
            for (int i=metricsHawkularMonitored; i<metricsHawkularMonitored+refreshHawkularRate; i++) {
                DataPoint<Double> dataPoint = new DataPoint(now++,(Double)metricValues.get(i),new HashMap<String, String>());
                points.add(dataPoint);
            }
            try {
                postHawkular.postArrayDataHawkular(points, fieldName, APPLICATION_JSON, tenant);
                DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putHawkularMonitoringCount(name, metricsHawkularMonitored + refreshHawkularRate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        synchronized(historyLock) {
            nowHistory=now;
        }

        return dataSent;
    }

}
