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

package org.jboss.metrics.automatedmetrics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import org.jboss.metrics.automatedmetrics.utils.DoubleValue;
import org.jboss.metrics.automatedmetrics.utils.MDataPoint;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCache;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCacheCollection;
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
    private static long nowHistory = 0;

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

    public synchronized boolean rhqMonitoring(Object target, Field field, String group) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;
        String name = field.getName() + "_" + target;
        MetricsCache metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
        String metricIdLoaded = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getRhqScheduleId(field.getName());
        int metricsRhqMonitored = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getRhqMonitoringCount(name);
        int refreshRhqRate = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getRhqMonitoringRefreshRate();
        List<Object> metricValues = metricsCacheInstance.searchMetricObject(name).getMetric();
        int metricCount = metricValues.size();
                
        if (metricIdLoaded != null) {
            int numericScheduleId = Integer.parseInt(metricIdLoaded);
            long now = System.currentTimeMillis();
            if (now<nowHistory)
                now = nowHistory+1;

            if(metricCount >= metricsRhqMonitored + refreshRhqRate) {
                List<MDataPoint> points = new ArrayList<>(refreshRhqRate);
                
                for (int i=metricsRhqMonitored; i<metricsRhqMonitored+refreshRhqRate; i++) {
                    MDataPoint dataPoint = new MDataPoint();
                    dataPoint.setScheduleId(numericScheduleId);
                    dataPoint.setTimeStamp(now++);
                    dataPoint.setValue((Double)metricValues.get(i));
                    points.add(dataPoint);
                }
                try {
                    postRhq.postArrayDataRhq(points, APPLICATION_JSON);
                    DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).putRhqMonitoringCount(name, metricsRhqMonitored + refreshRhqRate);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
            nowHistory=now;
        }

        return dataSent;
    }

}
