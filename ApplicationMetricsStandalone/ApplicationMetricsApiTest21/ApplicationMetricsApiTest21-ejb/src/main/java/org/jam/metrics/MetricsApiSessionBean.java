/*
 * Copyleft 2015 
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

    @Plot(fieldData = {"count2","count","count","count2"}, groupName = "myTestGroup", plotHandlerName = {"scatter","scatter2","line","line2"}, plot = {"plot1","plot2","plot1","plot2"}, color = {"red", "blue","cyan","green"}, typePlot={"scatter","scatter","line","line"}, threeD=false)
    public void countMethod() {
        count = new double[10][2];
        count2 = new double[10][2];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<2; j++) {
                count[i][j]=i*j+Math.random()*5;
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
        }
    }

}
