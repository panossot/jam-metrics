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
package org.jboss.metrics;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import org.jboss.metrics.automatedmetricsapi.CodeParamsApi;
import org.jboss.metrics.automatedmetricsapi.Metric;
import org.jboss.metrics.automatedmetricsapi.MetricFilter;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParams;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Stateful
@LocalBean
public class MetricsApiSessionBean {

    private int count = 0;
    
    private int count2 = 0;
    
    private String user = "Niki";
    
    private CodeParams cp;

    public void MetricsApiSessionBean() {
        cp = CodeParamsApi.getCodeParams(user);
    }

    @Metric(fieldName = {"count","count2"}, groupName = "myTestGroup")
    @MetricFilter(fieldName = "count", comparableValue = 5, groupName = "myTestGroup", filterParamName = "count2Param", equalsWith = true, largerThan = true, smallerThan = false, user = "Niki")
    public int countMethod() {
        count++;
        
        if (cp.getFilterParam("count2Param", false))
            count2 += 2;

        return count;
    }

}
