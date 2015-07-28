/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.metrics.javase.automatedmetricsjavaseapi;

import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
public class MetricsPropertiesApi {
     public static void storeProperties(String deployment, MetricProperties metricsProperties){
        DeploymentMetricProperties.getDeploymentMetricProperties().addDeploymentProperties(deployment, metricsProperties);
    }
}
