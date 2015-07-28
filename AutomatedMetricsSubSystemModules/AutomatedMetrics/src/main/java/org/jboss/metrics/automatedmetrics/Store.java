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
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricObject;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCache;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class Store {

    public static void CacheStore(Object target, Field field, MetricsCache metricsCacheInstance) throws IllegalArgumentException, IllegalAccessException {
        String name = field.getName() + "_" + target;
        MetricObject mo;
        mo = metricsCacheInstance.searchMetricObject(name);

        if (mo != null) {
            mo.addMetricValue(field.get(target));
        } else {
            MetricObject newMo = new MetricObject();
            newMo.addMetricValue(field.get(target));
            newMo.setName(name);
            metricsCacheInstance.addMetricCacheObject(newMo);
        }
    }

}
