/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
 * @author panos
 */
public class JbossAutomatedJavaSeMetrics {

    public void metric(Object instance, Object value, String metricName, String metricGroup) throws Exception {
        String cacheStore = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup).getCacheStore();
        String rhqMonitoring = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup).getRhqMonitoring();

        if (cacheStore != null && Boolean.parseBoolean(cacheStore)) {
            MetricsCache metricsCacheInstance;
            metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(metricGroup);
            if (metricsCacheInstance == null) {
                metricsCacheInstance = new MetricsCache();
                MetricsCacheCollection.getMetricsCacheCollection().addMetricsCacheInstance(metricGroup, metricsCacheInstance);
            }
            Store.CacheStore(instance, value, metricName, metricsCacheInstance);
        }
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

