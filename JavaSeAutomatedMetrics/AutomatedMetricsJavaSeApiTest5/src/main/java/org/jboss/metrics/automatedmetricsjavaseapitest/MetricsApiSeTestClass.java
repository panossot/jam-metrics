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

import org.jboss.metrics.javase.automatedmetricsjavaseapi.JbossAutomatedJavaSeMetricFilter;
import org.jboss.metrics.javase.automatedmetricsjavaseapi.JbossAutomatedJavaSeMetrics;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParams;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParamsCollection;



/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSeTestClass {

    private int count = 0;
    
    private int count2 = 0;
    
    private String user = "Niki";
    
    private CodeParams cp;

    public MetricsApiSeTestClass() {
        cp = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(user);
    }

    public int countMethod() throws Exception {
        JbossAutomatedJavaSeMetricFilter.metricFilter(count, "count", "myTestGroup", 5, "count2Param", user, true, false, true);
        count++;
        if (cp.getFilterParam("count2Param", false))
            count2 += 2;
        JbossAutomatedJavaSeMetrics.metric(this,count,"count","myTestGroup");
        JbossAutomatedJavaSeMetrics.metric(this,count2,"count2","myTestGroup");

        return count;
    }

}
