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

package org.jam.metrics.applicationmetrics;

import java.util.HashMap;
/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class OpenAnalyticsCollection {

    private static final OpenAnalyticsCollection jboac = new OpenAnalyticsCollection();
    private HashMap<String, OpenAnalyticsInstance> OpenAnalyticsInstances;

    private OpenAnalyticsCollection() {
        OpenAnalyticsInstances = new HashMap<String, OpenAnalyticsInstance>();
    }
    
    public static OpenAnalyticsCollection getOpenAnalyticsCollection() {
        return jboac;
    }
    
    public synchronized HashMap<String, OpenAnalyticsInstance> getOpenAnalyticsInstances() {
        return OpenAnalyticsInstances;
    }

    public synchronized void setOpenAnalyticsInstances(HashMap<String, OpenAnalyticsInstance> OpenAnalyticsInstances) {
        this.OpenAnalyticsInstances = OpenAnalyticsInstances;
    }
    
    public synchronized OpenAnalyticsInstance getOpenAnalyticsInstance(String name) {
        return (this.OpenAnalyticsInstances.get(name));
    }
    
    public synchronized void addOpenAnalyticsInstance(String name, OpenAnalyticsInstance OpenAnalyticsInstance) {
        this.OpenAnalyticsInstances.put(name, OpenAnalyticsInstance);
    }
    
    public synchronized void removeOpenAnalyticsInstance(String name) {
        this.OpenAnalyticsInstances.remove(name);
    }
    
    public synchronized boolean existsOpenAnalyticsInstance(String name) {
        return(this.OpenAnalyticsInstances.containsKey(name));
    }

}
