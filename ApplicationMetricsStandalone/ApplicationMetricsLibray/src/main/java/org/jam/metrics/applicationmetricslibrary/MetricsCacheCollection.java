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

package org.jam.metrics.applicationmetricslibrary;

import java.util.HashMap;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsCacheCollection {
    private static MetricsCacheCollection mcachec = new MetricsCacheCollection();
    private HashMap<String, MetricsCache> metricsCacheInstances;

    private MetricsCacheCollection() {
        metricsCacheInstances = new HashMap<String, MetricsCache>();
    }
    
    public static synchronized MetricsCacheCollection getMetricsCacheCollection() {
        return mcachec;
    }
    
    public synchronized MetricsCache getMetricsCacheInstance(String name) {
        return (this.metricsCacheInstances.get(name));
    }
    
    public synchronized void addMetricsCacheInstance(String name, MetricsCache metricsCache) {
        this.metricsCacheInstances.put(name, metricsCache);
    }
    
    public synchronized void removeMetricsCacheInstance(String name) {
        this.metricsCacheInstances.remove(name);
    }
    
    public synchronized boolean existsMetricsCacheInstance(String name) {
        return(this.metricsCacheInstances.containsKey(name));
    }
}
