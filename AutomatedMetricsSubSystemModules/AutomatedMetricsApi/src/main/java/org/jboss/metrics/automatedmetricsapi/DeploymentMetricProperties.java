/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.metrics.automatedmetricsapi;

import java.util.HashMap;

/**
 *
 * @author panos
 */
public class DeploymentMetricProperties {
    private static final DeploymentMetricProperties deploymentMetricProperties = new DeploymentMetricProperties();
    
    private HashMap<String,MetricProperties> deploymentProperties;

    private DeploymentMetricProperties() {
        deploymentProperties = new HashMap<String, MetricProperties>();
    }
    
    public static DeploymentMetricProperties getDeploymentMetricProperties() {
        return deploymentMetricProperties;
    }

    public HashMap<String, MetricProperties> getDeploymentProperties() {
        return deploymentProperties;
    }

    public void setDeploymentProperties(HashMap<String, MetricProperties> deploymentProperties) {
        this.deploymentProperties = deploymentProperties;
    }
    
    public void addDeploymentProperties(String name, MetricProperties metricProperties) {
        this.deploymentProperties.put(name, metricProperties);
    }
    
    public void removeDeploymentProperties(String name) {
        this.deploymentProperties.remove(name);
    }
    
}
