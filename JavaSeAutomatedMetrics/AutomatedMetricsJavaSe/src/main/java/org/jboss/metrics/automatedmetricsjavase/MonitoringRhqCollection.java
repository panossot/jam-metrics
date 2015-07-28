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
package org.jboss.metrics.automatedmetricsjavase;

import java.util.HashMap;
/**
 *
 * @author panos
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
    
    public HashMap<String, MonitoringRhq> getMonitoringRhqInstances() {
        return monitoringRhqInstances;
    }

    public void setMonitoringRhqInstances(HashMap<String, MonitoringRhq> monitoringRhqInstances) {
        this.monitoringRhqInstances = monitoringRhqInstances;
    }
    
    public MonitoringRhq getMonitoringRhqInstance(String name) {
        return (this.monitoringRhqInstances.get(name));
    }
    
    public void addMonitoringRhqInstance(String name, MonitoringRhq monitoringRhq) {
        this.monitoringRhqInstances.put(name, monitoringRhq);
    }
    
    public void removeMonitoringRhqInstance(String name) {
        this.monitoringRhqInstances.remove(name);
    }
    
    public boolean existsMonitoringRhqInstance(String name) {
        return(this.monitoringRhqInstances.containsKey(name));
    }

}
