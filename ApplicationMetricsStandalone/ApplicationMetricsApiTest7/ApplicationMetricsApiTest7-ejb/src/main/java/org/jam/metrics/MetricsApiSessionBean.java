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
package org.jam.metrics;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import org.jam.metrics.applicationmetricsapi.Metric;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Stateful
@LocalBean
public class MetricsApiSessionBean {

    private int count = 0;
    
    private int count2 = 0;

    public MetricsApiSessionBean() {
    }

    @Metric(fieldName = {"count","count2"}, groupName = "myTestGroup", plot = {"plot1","plot2","plot3","plot4","plot5"}, plotHandlerName = {"bar","scatter","histogram","stair","line"}, data = {"count","count2", "count","count2", "count"}, color = {"red", "blue","green","yellow","magenta"}, typePlot={"bar","scatter","histogram","stair","line"})
    public int countMethod() {
        count++;
        count2 += 2;

        return count;
    }

}
