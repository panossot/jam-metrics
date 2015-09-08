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
package org.jboss.metrics.jbossautomatedmetricslibrary;

import java.util.HashSet;
import java.util.Iterator;
import org.jboss.logging.Logger;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsCache {

    private HashSet<MetricObject> metricCache;
    private static final Logger logger = Logger.getLogger(MetricsCache.class);

    public MetricsCache() {
        metricCache = new HashSet();
    }

    /**
     * @return the metricCache
     */
    public synchronized HashSet<MetricObject> getMetricCache() {
        return metricCache;
    }

    public synchronized void addMetricCacheObject(MetricObject cacheObject) {
        this.getMetricCache().add(cacheObject);
    }

    public synchronized void removeMetricCacheObject(MetricObject cacheObject) {
        this.getMetricCache().remove(cacheObject);
    }

    public synchronized MetricObject searchMetricObject(String metricName) {
        for (MetricObject mObject : getMetricCache()) {
            if (mObject.getName().compareTo(metricName) == 0) {
                return mObject;
            }
        }

        return null;
    }

    public synchronized void printMetricObjects() {
        logger.info("Logging metric objects ...");
        for (MetricObject mObject : getMetricCache()) {
            logger.info("Name : " + mObject.getName());
            Iterator<Object> iob = mObject.getMetric().iterator();

            while (iob.hasNext()) {
                logger.info("Value : " + iob.next().toString());
            }
        }
    }

}
