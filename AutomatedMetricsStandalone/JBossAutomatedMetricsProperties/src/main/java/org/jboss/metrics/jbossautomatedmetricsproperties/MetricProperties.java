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
package org.jboss.metrics.jbossautomatedmetricsproperties;

import java.util.HashMap;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricProperties {
 
    private String rhqMonitoring = "false";
    private String cacheStore = "false";
    private String rhqServerUrl = "localhost";
    private String rhqServerPort = "7080";
    private String rhqServerUsername = "rhqadmin";
    private String rhqServerPassword = "rhqadmin";
    private HashMap<String,String> rhqScheduleIds;

    public MetricProperties() {
        rhqScheduleIds = new HashMap<>();
    }
    
    public String getRhqMonitoring() {
        return rhqMonitoring;
    }

    public void setRhqMonitoring(String rhqMonitoring) {
        this.rhqMonitoring = rhqMonitoring;
    }

    public String getCacheStore() {
        return cacheStore;
    }

    public void setCacheStore(String cacheStore) {
        this.cacheStore = cacheStore;
    }

    public HashMap<String, String> getRhqScheduleIds() {
        return rhqScheduleIds;
    }

    public void setRhqScheduleIds(HashMap<String, String> rhqScheduleIds) {
        this.rhqScheduleIds = rhqScheduleIds;
    }
    
    public String getRhqScheduleId(String name) {
        return(this.rhqScheduleIds.get(name));
    }

    public String getRhqServerUrl() {
        return rhqServerUrl;
    }

    public void setRhqServerUrl(String rhqServerUrl) {
        this.rhqServerUrl = rhqServerUrl;
    }

    public String getRhqServerPort() {
        return rhqServerPort;
    }

    public void setRhqServerPort(String rhqServerPort) {
        this.rhqServerPort = rhqServerPort;
    }

    public String getRhqServerUsername() {
        return rhqServerUsername;
    }

    public void setRhqServerUsername(String rhqServerUsername) {
        this.rhqServerUsername = rhqServerUsername;
    }

    public String getRhqServerPassword() {
        return rhqServerPassword;
    }

    public void setRhqServerPassword(String rhqServerPassword) {
        this.rhqServerPassword = rhqServerPassword;
    }
    
    public void addRhqScheduleId(String name, String id) {
        this.rhqScheduleIds.put(name, id);
    }
    
    public void removeRhqScheduleId(String name) {
        this.rhqScheduleIds.remove(name);
    }

}
