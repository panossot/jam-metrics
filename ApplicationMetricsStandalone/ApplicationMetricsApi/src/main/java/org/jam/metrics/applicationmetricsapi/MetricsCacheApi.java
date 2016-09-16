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

package org.jam.metrics.applicationmetricsapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jam.metrics.applicationmetricslibrary.MetricObject;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsCacheApi {
    public static synchronized Map<String,ArrayList<Object>> getMetricsCache(String deployment)
    {
        Map<String,ArrayList<Object>> metricList = new HashMap<>();
        HashSet<MetricObject> metricsCache = MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(deployment).getMetricCache();
        
        for (MetricObject mObject : metricsCache) {
            Iterator<Object> iob = Collections.synchronizedList(new ArrayList<Object>(mObject.getMetric())).iterator();
            ArrayList<Object> metricValues = new ArrayList<>();
            while (iob.hasNext()) {
                metricValues.add(iob.next().toString());
            }
            metricList.put(mObject.getName(), metricValues);
        }
        
        return metricList;
    }
    
    public static synchronized String printMetricsCache(String deployment) {
        String output = "";
        Map<String, ArrayList<Object>> cache;
        Set<String> metricNames;
        Collection<ArrayList<Object>> metricValues;
        cache = getMetricsCache(deployment);
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
    
    public static synchronized ArrayList<Object> getMetricsCacheValuesByKey(String deployment, String key) {
        ArrayList<Object> cacheValues;
        cacheValues = getMetricsCache(deployment).get(key);
        
        return cacheValues;
    }
    
    // Dummy comparison for test cases.
    public static synchronized boolean compareMetricsCacheValuesByKey(String deployment, String key, ArrayList<Object> valuesToCompare) {
        boolean isEqual = true;
        ArrayList<Object> cacheValues;
        cacheValues = getMetricsCache(deployment).get(key);
        
        for (Object valueComp : valuesToCompare) {
            for (Object value : cacheValues) {
                if (value.toString().compareTo(valueComp.toString()) == 0) {
                    isEqual = true;
                    break;
                }
                isEqual = false;
            }
        }
        
        return isEqual;
    }
    
    public static synchronized void cleanMetricsCache(String group)
    {
        MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(group).getMetricCache().clear();
    }
}
