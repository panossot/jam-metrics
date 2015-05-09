/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.metrics.jbossautomatedmetricsproperties;

import java.util.HashMap;

/**
 *
 * @author panos
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
