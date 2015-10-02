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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricObject {

    private ArrayList<Object> metric;
    private ArrayList<Double> doubleMetric;
    private String name;

    public MetricObject() {
        metric = new ArrayList<Object>();
        doubleMetric = new ArrayList<Double>();
    }

    public synchronized ArrayList<Object> getMetric() {
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
        metric.add(value);
        if (doubleValue) {
            doubleMetric.add(Double.parseDouble(value.toString()));
        }
    }

    public ArrayList<Double> getDoubleMetric() {
        return doubleMetric;
    }

    public void setDoubleMetric(ArrayList<Double> doubleMetric) {
        this.doubleMetric = doubleMetric;
    }
    
    public synchronized void addDoubleMetricValue(double value) {
        doubleMetric.add(value);
    }
}
