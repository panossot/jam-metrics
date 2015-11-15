/*
 * Copyright 2015 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.metrics.jbossautomatedmetricslibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author panos
 */
public class CodeParams {
    private Map<String,String> stringCodeParams;
    private Map<String,Integer> integerCodeParams;
    private Map<String,Double> doubleCodeParams;
    private Map<String,AtomicBoolean> filterParams;

    public CodeParams() {
        stringCodeParams =  new HashMap<>();
        integerCodeParams = new HashMap<>();
        doubleCodeParams = new HashMap<>();
        filterParams = new HashMap<>();
    }

    public synchronized Map<String, String> getStringCodeParams() {
        return stringCodeParams;
    }
    
    public synchronized String getStringCodeParam(String name) {
        return stringCodeParams.get(name);
    }

    public synchronized void setStringCodeParams(Map<String, String> stringCodeParams) {
        this.stringCodeParams = stringCodeParams;
    }

    public synchronized Map<String, Integer> getIntegerCodeParams() {
        return integerCodeParams;
    }
    
    public synchronized int getIntegerCodeParam(String name) {
        return integerCodeParams.get(name);
    }

    public synchronized void setIntegerCodeParams(Map<String, Integer> integerCodeParams) {
        this.integerCodeParams = integerCodeParams;
    }

    public synchronized Map<String, Double> getDoubleCodeParams() {
        return doubleCodeParams;
    }

    public synchronized void setDoubleCodeParams(Map<String, Double> doubleCodeParams) {
        this.doubleCodeParams = doubleCodeParams;
    }
    
    public synchronized double getDoubleCodeParam(String name) {
        return doubleCodeParams.get(name);
    }
    
    public synchronized void putStringCodeParam(String name, String value) {
        this.stringCodeParams.put(name, value);
    }
    
    public synchronized void putIntegerCodeParam(String name, int value) {
        this.integerCodeParams.put(name, value);
    }
    
    public synchronized void putDoubleCodeParams(String name, double value) {
        this.doubleCodeParams.put(name, value);
    }

    public synchronized Map<String, AtomicBoolean> getFilterParams() {
        return filterParams;
    }
    
    public synchronized boolean getFilterParam(String name) {
        return filterParams.get(name).get();
    }
    
    public synchronized void putFilterParam(String name, boolean value) {
        this.filterParams.put(name, new AtomicBoolean(value));
    }

    public synchronized void setFilterParam(String name, boolean allow) {
        this.filterParams.get(name).set(allow);
    }

}
