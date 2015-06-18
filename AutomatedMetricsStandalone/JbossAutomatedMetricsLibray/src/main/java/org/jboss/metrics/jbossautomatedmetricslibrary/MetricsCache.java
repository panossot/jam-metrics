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

import java.util.HashSet;
import java.util.Iterator;
import org.jboss.logging.Logger;


/**
 *
 * @author panos
 */
public class MetricsCache {

    private volatile HashSet<MetricObject> metricCache;
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

    public void printMetricObjects() {
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
