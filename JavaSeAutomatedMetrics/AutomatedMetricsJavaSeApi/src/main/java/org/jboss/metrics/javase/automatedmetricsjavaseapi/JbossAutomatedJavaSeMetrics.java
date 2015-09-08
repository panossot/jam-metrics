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
package org.jboss.metrics.javase.automatedmetricsjavaseapi;

import org.jboss.metrics.automatedmetricsjavase.MonitoringRhq;
import org.jboss.metrics.automatedmetricsjavase.MonitoringRhqCollection;
import org.jboss.metrics.automatedmetricsjavase.Store;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCache;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCacheCollection;



/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class JbossAutomatedJavaSeMetrics {

    private final static Object cacheStorage = new Object();
    private final static Object rhqMonitoring = new Object();

    public static void metric(Object instance, Object value, String metricName, String metricGroup) throws Exception {
        String cacheStore = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup).getCacheStore();
        String rhqMonitoring = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup).getRhqMonitoring();

        synchronized(cacheStorage) {
		if (cacheStore != null && Boolean.parseBoolean(cacheStore)) {
		    MetricsCache metricsCacheInstance;
		    metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(metricGroup);
		    if (metricsCacheInstance == null) {
		        metricsCacheInstance = new MetricsCache();
		        MetricsCacheCollection.getMetricsCacheCollection().addMetricsCacheInstance(metricGroup, metricsCacheInstance);
		    }
		    Store.CacheStore(instance, value, metricName, metricsCacheInstance);
		}
        }

        synchronized(rhqMonitoring) {
		if (rhqMonitoring != null && Boolean.parseBoolean(rhqMonitoring)) {
		    MonitoringRhq mrhqInstance;
		    mrhqInstance = MonitoringRhqCollection.getRhqCollection().getMonitoringRhqInstance(metricGroup);
		    if (mrhqInstance == null) {
		        mrhqInstance = new MonitoringRhq(metricGroup);
		        MonitoringRhqCollection.getRhqCollection().addMonitoringRhqInstance(metricGroup, mrhqInstance);
		    }

		    mrhqInstance.rhqMonitoring(instance, value, metricName, metricGroup);
		}
        }
    }
}

