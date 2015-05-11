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
package org.jboss.metrics.automatedmetrics;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricObject;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCache;


/**
 *
 * @author panos
 */
public class Store {
    
    private final static Object metricObjectLock = new Object();

    public static void CacheStore(Object target, Field field, MetricsCache metricsCacheInstance) throws IllegalArgumentException, IllegalAccessException {
        String name = field.getName() + "_" + target;
        MetricObject mo;
        synchronized(metricObjectLock) {
            mo = metricsCacheInstance.searchMetricObject(name);
        }
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
