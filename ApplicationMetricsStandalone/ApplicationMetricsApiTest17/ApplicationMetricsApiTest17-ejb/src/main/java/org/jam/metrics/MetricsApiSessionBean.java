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
import org.jam.metrics.applicationmetricsapi.Plot;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Stateful
@LocalBean
public class MetricsApiSessionBean {

    private double count[][];
    
    private double count2[][];

    public MetricsApiSessionBean() {
    }

    @Plot(fieldData = {"count","count2","count","count2","count"}, groupName = "myTestGroup", plot = {"plot1","plot2","plot3","plot4","plot5"}, color = {"red", "blue","green","yellow","magenta"}, typePlot={"box","scatter","histogram","grid"}, threeD=true)
    public void countMethod() {
        count = new double[10][10];
        count2 = new double[10][10];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<3; j++) {
                count[i][j]=i*j;
                count2[i][j]=i*j +2*i;
            }
        }
    }

}
