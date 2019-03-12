/*
 * Copyleft 2015 
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricObject {

    private List<Object> metric;
    private String name;
    private int sequenceNum;
    private int metricCacheObjectDeleted;

    public MetricObject() {
        metric = Collections.synchronizedList(new ArrayList<Object>());
        metricCacheObjectDeleted = 0;
        sequenceNum = 0;
    }

    public int getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(int sequenceNum) {
        this.sequenceNum = sequenceNum;
    }
    
    public void increaseSequenceNum() {
        this.sequenceNum++;
    }
    
    public synchronized int getMetricCacheObjectDeleted() {
        return metricCacheObjectDeleted;
    }

    public synchronized void setMetricCacheObjectDeleted(int metricCacheObjectDeleted) {
        this.metricCacheObjectDeleted = metricCacheObjectDeleted;
    }
    
    public synchronized void increaseMetricCacheObjectDeleted() {
        this.metricCacheObjectDeleted = this.metricCacheObjectDeleted + 1;
    }

    public synchronized List<Object> getMetric() {
        return metric;
    }

    public synchronized void setMetric(ArrayList<Object> metric) {
        this.metric = metric;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }
    
    public synchronized void addMetricValue(Object value) {
        metric.add(value);
    }
    
    public synchronized void addMetricValue(Object value, boolean doubleValue) {
        if (doubleValue) {
            metric.add(Double.parseDouble(value.toString()));
        }else {
            metric.add(value);
        }
    }

}
