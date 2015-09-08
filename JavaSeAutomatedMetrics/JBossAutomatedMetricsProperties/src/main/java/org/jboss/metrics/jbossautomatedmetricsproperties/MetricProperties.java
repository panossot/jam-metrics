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

import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricProperties {
 
    private String rhqMonitoring = "false";
    private String cacheStore = "false";
    private String databaseStore = "false";
    private HashMap<String,Statement> databaseStatement;
    private HashMap<String,String> updateDbQueries;
    private String rhqServerUrl = "localhost";
    private String rhqServerPort = "7080";
    private String rhqServerUsername = "rhqadmin";
    private String rhqServerPassword = "rhqadmin";
    private HashMap<String,String> rhqScheduleIds;

    public MetricProperties() {
        rhqScheduleIds = new HashMap<>();
        databaseStatement = new HashMap<>();
        updateDbQueries = new HashMap<>();
    }
    
    public synchronized String getRhqMonitoring() {
        return rhqMonitoring;
    }

    public synchronized void setRhqMonitoring(String rhqMonitoring) {
        this.rhqMonitoring = rhqMonitoring;
    }

    public synchronized String getCacheStore() {
        return cacheStore;
    }

    public synchronized void setCacheStore(String cacheStore) {
        this.cacheStore = cacheStore;
    }

    public synchronized HashMap<String, String> getRhqScheduleIds() {
        return rhqScheduleIds;
    }

    public synchronized void setRhqScheduleIds(HashMap<String, String> rhqScheduleIds) {
        this.rhqScheduleIds = rhqScheduleIds;
    }
    
    public synchronized String getRhqScheduleId(String name) {
        return(this.rhqScheduleIds.get(name));
    }

    public synchronized String getRhqServerUrl() {
        return rhqServerUrl;
    }

    public synchronized void setRhqServerUrl(String rhqServerUrl) {
        this.rhqServerUrl = rhqServerUrl;
    }

    public synchronized String getRhqServerPort() {
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

    public String getDatabaseStore() {
        return databaseStore;
    }

    public void setDatabaseStore(String databaseStore) {
        this.databaseStore = databaseStore;
    }

    public HashMap<String, Statement> getDatabaseStatement() {
        return databaseStatement;
    }

    public void setDatabaseStatement(HashMap<String, Statement> databaseStatement) {
        this.databaseStatement = databaseStatement;
    }

    public HashMap<String, String> getUpdateDbQueries() {
        return updateDbQueries;
    }

    public void setUpdateDbQueries(HashMap<String, String> updateDbQueries) {
        this.updateDbQueries = updateDbQueries;
    }

}
