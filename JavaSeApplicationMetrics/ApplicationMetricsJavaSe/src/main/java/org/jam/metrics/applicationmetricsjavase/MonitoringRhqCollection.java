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

package org.jam.metrics.applicationmetricsjavase;

import java.util.HashMap;
/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MonitoringRhqCollection {

    private static final MonitoringRhqCollection mrhqc = new MonitoringRhqCollection();
    private HashMap<String, MonitoringRhq> monitoringRhqInstances;

    private MonitoringRhqCollection() {
        monitoringRhqInstances = new HashMap<String, MonitoringRhq>();
    }
    
    public static MonitoringRhqCollection getRhqCollection() {
        return mrhqc;
    }
    
    public synchronized HashMap<String, MonitoringRhq> getMonitoringRhqInstances() {
        return monitoringRhqInstances;
    }

    public synchronized void setMonitoringRhqInstances(HashMap<String, MonitoringRhq> monitoringRhqInstances) {
        this.monitoringRhqInstances = monitoringRhqInstances;
    }
    
    public synchronized MonitoringRhq getMonitoringRhqInstance(String name) {
        return (this.monitoringRhqInstances.get(name));
    }
    
    public synchronized void addMonitoringRhqInstance(String name, MonitoringRhq monitoringRhq) {
        this.monitoringRhqInstances.put(name, monitoringRhq);
    }
    
    public synchronized void removeMonitoringRhqInstance(String name) {
        this.monitoringRhqInstances.remove(name);
    }
    
    public synchronized boolean existsMonitoringRhqInstance(String name) {
        return(this.monitoringRhqInstances.containsKey(name));
    }

}
