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
package org.jboss.metrics.jbossautomatedmetricslibrary;

import java.util.HashMap;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class DeploymentMetricProperties {
    private static final DeploymentMetricProperties deploymentMetricProperties = new DeploymentMetricProperties();
    
    private HashMap<String,MetricProperties> deploymentProperties;
    private HashMap<String,MetricInternalParameters>  deploymentInternalParameters;

    private DeploymentMetricProperties() {
        deploymentProperties = new HashMap<String, MetricProperties>();
         deploymentInternalParameters = new HashMap<String, MetricInternalParameters>();
    }
    
    public static synchronized DeploymentMetricProperties getDeploymentMetricProperties() {
        return deploymentMetricProperties;
    }

    public synchronized MetricProperties getDeploymentMetricProperty(String name) {
        return deploymentProperties.get(name);
    }
    
    public synchronized void addDeploymentProperties(String name, MetricProperties metricProperties) {
        if (this.deploymentProperties.containsKey(name))
            this.deploymentProperties.remove(name);
        
        this.deploymentProperties.put(name, metricProperties);
    }
    
    public synchronized void removeDeploymentProperties(String name) {
        this.deploymentProperties.remove(name);
    }

    public synchronized MetricInternalParameters getDeploymentInternalParameters(String name) {
        return  deploymentInternalParameters.get(name);
    }
    
    public synchronized void addDeploymentInternalParameters(String name, MetricInternalParameters metricInternalParameters) {
        this. deploymentInternalParameters.putIfAbsent(name, metricInternalParameters);
    }
    
}
