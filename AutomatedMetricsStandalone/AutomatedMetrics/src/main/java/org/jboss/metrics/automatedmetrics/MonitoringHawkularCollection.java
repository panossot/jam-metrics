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

package org.jboss.metrics.automatedmetrics;

import java.util.HashMap;
/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MonitoringHawkularCollection {

    private static final MonitoringHawkularCollection mhawkularc = new MonitoringHawkularCollection();
    private HashMap<String, MonitoringHawkular> monitoringHawkularInstances;

    private MonitoringHawkularCollection() {
        monitoringHawkularInstances = new HashMap<String, MonitoringHawkular>();
    }
    
    public static MonitoringHawkularCollection getHawkularCollection() {
        return mhawkularc;
    }
    
    public synchronized HashMap<String, MonitoringHawkular> getMonitoringHawkularInstances() {
        return monitoringHawkularInstances;
    }

    public synchronized void setMonitoringHawkularInstances(HashMap<String, MonitoringHawkular> monitoringHawkularInstances) {
        this.monitoringHawkularInstances = monitoringHawkularInstances;
    }
    
    public synchronized MonitoringHawkular getMonitoringHawkularInstance(String name) {
        return (this.monitoringHawkularInstances.get(name));
    }
    
    public synchronized void addMonitoringHawkularInstance(String name, MonitoringHawkular monitoringHawkular) {
        this.monitoringHawkularInstances.put(name, monitoringHawkular);
    }
    
    public synchronized void removeMonitoringHawkularInstance(String name) {
        this.monitoringHawkularInstances.remove(name);
    }
    
    public synchronized boolean existsMonitoringHawkularInstance(String name) {
        return(this.monitoringHawkularInstances.containsKey(name));
    }

}
