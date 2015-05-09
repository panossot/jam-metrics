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
package org.jboss.metrics.jbossautomatedmetricslibrary;

import java.util.HashMap;

/**
 *
 * @author panos
 */
public class MetricsCacheCollection {
    private static MetricsCacheCollection mcachec = new MetricsCacheCollection();
    private HashMap<String, MetricsCache> metricsCacheInstances;

    private MetricsCacheCollection() {
        metricsCacheInstances = new HashMap<String, MetricsCache>();
    }
    
    public static MetricsCacheCollection getMetricsCacheCollection() {
        return mcachec;
    }
    
    public MetricsCache getMetricsCacheInstance(String name) {
        return (this.metricsCacheInstances.get(name));
    }
    
    public void addMetricsCacheInstance(String name, MetricsCache metricsCache) {
        this.metricsCacheInstances.put(name, metricsCache);
    }
    
    public void removeMetricsCacheInstance(String name) {
        this.metricsCacheInstances.remove(name);
    }
    
    public boolean existsMetricsCacheInstance(String name) {
        return(this.metricsCacheInstances.containsKey(name));
    }
}
