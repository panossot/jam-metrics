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
package org.jboss.metrics.javase.automatedmetricsjavaseapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricObject;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCacheCollection;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsCacheApi {
    public static synchronized Map<String,ArrayList<Object>> getMetricsCache(String group)
    {
        Map<String,ArrayList<Object>> metricList = new HashMap<>();
        HashSet<MetricObject> metricsCache = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group).getMetricCache();
        
        for (MetricObject mObject : metricsCache) {
            Iterator<Object> iob = ((ArrayList<Object>)mObject.getMetric().clone()).iterator();
            ArrayList<Object> metricValues = new ArrayList<>();
            while (iob.hasNext()) {
                metricValues.add(iob.next().toString());
            }
            metricList.put(mObject.getName(), metricValues);
        }
        
        return metricList;
    }
    
    public static synchronized String printMetricsCache(String group) {
        String output = "";
        Map<String, ArrayList<Object>> cache;
        Set<String> metricNames;
        Collection<ArrayList<Object>> metricValues;
        cache = getMetricsCache(group);
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
    
    public static void cleanMetricsCache(String group)
    {
        MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group).getMetricCache().clear();
    }
}
