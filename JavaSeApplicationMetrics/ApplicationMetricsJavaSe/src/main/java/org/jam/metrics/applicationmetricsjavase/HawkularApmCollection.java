/*
 * Copyleft 2016 
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

package org.jam.metrics.applicationmetricsjavase;

import java.util.HashMap;
/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Deprecated // MonitoringHawkularApmAdapter is replaced with HawkularApmAdapter
public class HawkularApmCollection {

    private static final HawkularApmCollection hawkularapmc = new HawkularApmCollection();
    private HashMap<String, MonitoringHawkularApm> hawkularApmInstances;

    private HawkularApmCollection() {
        hawkularApmInstances = new HashMap<String, MonitoringHawkularApm>();
    }
    
    public static HawkularApmCollection getHawkularApmCollection() {
        return hawkularapmc;
    }
    
    public synchronized HashMap<String, MonitoringHawkularApm> getHawkularApmInstances() {
        return hawkularApmInstances;
    }

    public synchronized void setHawkularApmInstances(HashMap<String, MonitoringHawkularApm> hawkularApmInstances) {
        this.hawkularApmInstances = hawkularApmInstances;
    }
    
    public synchronized MonitoringHawkularApm getHawkularApmInstance(String name) {
        return (this.hawkularApmInstances.get(name));
    }
    
    public synchronized void addHawkularApmInstance(String name, MonitoringHawkularApm hawkularApm) {
        this.hawkularApmInstances.put(name, hawkularApm);
    }
    
    public synchronized void removeHawkularApmInstance(String name) {
        this.hawkularApmInstances.remove(name);
    }
    
    public synchronized boolean existsHawkularApmInstance(String name) {
        return(this.hawkularApmInstances.containsKey(name));
    }

}
