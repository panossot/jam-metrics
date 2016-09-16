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

package org.jam.metrics.applicationmetricsjavase;

import org.jam.metrics.applicationmetricslibrary.MetricObject;
import org.jam.metrics.applicationmetricslibrary.MetricsCache;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class Store {

    public static void CacheStore(Object target, String fieldName, Object fieldValue, MetricsCache metricsCacheInstance, MetricProperties properties) throws IllegalArgumentException, IllegalAccessException {
        String name = fieldName + "_" + target;
        MetricObject mo;
        mo = metricsCacheInstance.searchMetricObject(name);
        if (mo != null) {
            mo.addMetricValue(fieldValue,true);
            
            if (properties.getCacheMaxSize() < mo.getMetric().size())
                mo.getMetric().remove(0);
        } else {
            MetricObject newMo = new MetricObject();
            newMo.addMetricValue(fieldValue,true);
            newMo.setName(name);
            if (!metricsCacheInstance.addMetricCacheObject(newMo)) {
                Store.CacheStore(target, fieldName, fieldValue, metricsCacheInstance, properties);
            }
        }
    }

}
