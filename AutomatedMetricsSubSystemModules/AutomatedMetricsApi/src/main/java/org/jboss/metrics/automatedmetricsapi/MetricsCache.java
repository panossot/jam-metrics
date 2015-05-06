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
package org.jboss.metrics.automatedmetricsapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author panos
 */
public class MetricsCache {
    public static Map<String,ArrayList<Object>> getMetricsCache()
    {
        Map<String,ArrayList<Object>> metricList = new HashMap<>();
        HashSet<MetricObject> metricsCache = MetricsCacheSingleton.getCache().getMetricCache();
        
        for (MetricObject mObject : metricsCache) {
            Iterator<Object> iob = mObject.metric.iterator();
            ArrayList<Object> metricValues = new ArrayList<>();
            while (iob.hasNext()) {
                metricValues.add(iob.next().toString());
            }
            metricList.put(mObject.name, metricValues);
        }
        
        return metricList;
    }
    
    public static String printMetricsCache() {
        String output = "";
        Map<String, ArrayList<Object>> cache;
        Set<String> metricNames;
        Collection<ArrayList<Object>> metricValues;
        cache = getMetricsCache();
        metricNames = cache.keySet();
        metricValues = cache.values();

        Iterator<String> iob = metricNames.iterator();
        Iterator<ArrayList<Object>> iobv = metricValues.iterator();
        while (iob.hasNext()) {
            output += "<br>Metric Parameter Name : " + iob.next() + "</br>\n";
            if (iobv.hasNext()) {
                ArrayList<Object> values = iobv.next();
                for (Object value : values) {
                    output += "<br>Value : " + value.toString() + "</br>\n";
                }
            }
        }
        
        return output;
    }
    
    public static void cleanMetricsCache()
    {
        Map<String,ArrayList<Object>> metricList = new HashMap<>();
        HashSet<MetricObject> metricsCache = MetricsCacheSingleton.getCache().getMetricCache();
        metricsCache.clear();
    }
}
