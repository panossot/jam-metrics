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
package org.jam.metrics.applicationmetricsapi;

import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationJavaSeMetrics {

    public static void metric(final Object instance, Object value, final String metricName, final String metricGroup, final String... moreArgs) throws Exception {
        final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup);
        String cacheStore = properties.getCacheStore();
        String rhqMonitoring = properties.getRhqMonitoring();
        String hawkularMonitoring = properties.getHawkularMonitoring();
        String hawkularApm = properties.getHawkularMetricsApm();
        int refreshRate = properties.getPlotRefreshRate();
        final String hawkularTenant = properties.getHawkularTenant();

        CacheAdapter.cacheAdapter(cacheStore, instance, value, metricName, metricGroup, properties);

        MonitoringRhqAdapter.monitoringRhqAdapter(rhqMonitoring, instance, value, metricName, metricGroup, properties);

        MonitoringHawkularAdapter.monitoringHawkularAdapter(hawkularMonitoring, instance, value, metricName, metricGroup, hawkularTenant, properties);

        MonitoringHawkularApmAdapter.monitoringHawkularApmAdapter(hawkularApm, instance, value, metricName, metricGroup, hawkularTenant, properties, moreArgs);
 
        JMathPlotAdapter.jMathPlotAdapter(metricGroup, instance, metricName, refreshRate, properties, moreArgs);

    }

}
