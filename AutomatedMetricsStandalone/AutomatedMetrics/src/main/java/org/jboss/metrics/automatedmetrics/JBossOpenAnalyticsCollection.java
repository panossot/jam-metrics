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
public class JBossOpenAnalyticsCollection {

    private static final JBossOpenAnalyticsCollection jboac = new JBossOpenAnalyticsCollection();
    private HashMap<String, JBossOpenAnalyticsInstance> jbossOpenAnalyticsInstances;

    private JBossOpenAnalyticsCollection() {
        jbossOpenAnalyticsInstances = new HashMap<String, JBossOpenAnalyticsInstance>();
    }
    
    public static JBossOpenAnalyticsCollection getJBossOpenAnalyticsCollection() {
        return jboac;
    }
    
    public synchronized HashMap<String, JBossOpenAnalyticsInstance> getJBossOpenAnalyticsInstances() {
        return jbossOpenAnalyticsInstances;
    }

    public synchronized void setJBossOpenAnalyticsInstances(HashMap<String, JBossOpenAnalyticsInstance> jbossOpenAnalyticsInstances) {
        this.jbossOpenAnalyticsInstances = jbossOpenAnalyticsInstances;
    }
    
    public synchronized JBossOpenAnalyticsInstance getJBossOpenAnalyticsInstance(String name) {
        return (this.jbossOpenAnalyticsInstances.get(name));
    }
    
    public synchronized void addJBossOpenAnalyticsInstance(String name, JBossOpenAnalyticsInstance jbossOpenAnalyticsInstance) {
        this.jbossOpenAnalyticsInstances.put(name, jbossOpenAnalyticsInstance);
    }
    
    public synchronized void removeJBossOpenAnalyticsInstance(String name) {
        this.jbossOpenAnalyticsInstances.remove(name);
    }
    
    public synchronized boolean existsJBossOpenAnalyticsInstance(String name) {
        return(this.jbossOpenAnalyticsInstances.containsKey(name));
    }

}
