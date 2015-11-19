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
package org.jboss.metrics.automatedmetricsjavaseapitest;

import org.jboss.metrics.javase.automatedmetricsjavaseapi.JbossAutomatedJavaSeMetrics;
import org.jboss.metrics.javase.automatedmetricsjavaseapi.JbossAutomatedJavaSeMetricsDbStore;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParamsCollection;



/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSeTestClass {

    private int count = 0;
    
    private int count2 = 0;
    
    private static int i = 0;
    
    private String metricUser = "Niki";

    public synchronized int countMethod() throws Exception {
        count++;
        count2 += 2;
        JbossAutomatedJavaSeMetrics.metric(this,count,"count","myTestGroup");
        CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(metricUser).putIntegerCodeParam("sequenceNum", i++);
        JbossAutomatedJavaSeMetricsDbStore.metricsDbStore(this, new Object[]{count}, "myTestGroup", "statement_1", new String[]{"StoreDBMetric","count"}, metricUser);
        JbossAutomatedJavaSeMetrics.metric(this,count2,"count2","myTestGroup");
        CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(metricUser).putIntegerCodeParam("sequenceNum", i++);
        JbossAutomatedJavaSeMetricsDbStore.metricsDbStore(this, new Object[]{count2}, "myTestGroup", "statement_1", new String[]{"StoreDBMetric","count2"}, metricUser);

        return count;
    }

}
