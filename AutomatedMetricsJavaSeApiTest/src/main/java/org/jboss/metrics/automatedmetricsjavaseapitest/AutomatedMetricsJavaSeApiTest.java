/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.metrics.automatedmetricsjavaseapitest;

import org.jboss.metrics.javase.automatedmetricsjavaseapi.MetricsCache;

/**
 *
 * @author panos
 */
public class AutomatedMetricsJavaSeApiTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MetricsApiSeTestClass mTC = new MetricsApiSeTestClass();
            mTC.countMethod();
            MetricsCache.printMetricsCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
