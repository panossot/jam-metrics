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

import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class CacheAdapter {

    private final static Object cacheLock = new Object();

    protected static void cacheAdapter(String cacheStore, String group, Object target, String fieldName, Object fieldValue, MetricProperties properties) throws IllegalArgumentException, IllegalAccessException {
        if (cacheStore != null && Boolean.parseBoolean(cacheStore)) {
            synchronized (cacheLock) {
                MetricsCache metricsCacheInstance = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group);
                if (metricsCacheInstance == null) {
                    metricsCacheInstance = new MetricsCache();
                    MetricsCacheCollection.getMetricsCacheCollection().addMetricsCacheInstance(group, metricsCacheInstance);
                }

                Store.CacheStore(target, fieldName, fieldValue, metricsCacheInstance, properties);

            }
        }
    }
}
