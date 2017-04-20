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
    
    private double count3[][];
    
    private double count4[][];

    public MetricsApiSessionBean() {
    }

    @Plot(fieldData = {"count4","count","count2","count2","count3","count"}, groupName = "myTestGroup", plotHandlerName = {"box","bar","scatter","stair","histogram","line"}, plot = {"plot1","plot2","plot3","plot4","plot5","plot6"}, color = {"red", "blue","green","yellow","magenta","cyan"}, typePlot={"box","bar","scatter","stair","histogram","line"}, threeD=false)
    public void countMethod() {
        count = new double[10][2];
        count2 = new double[10][2];
        count3 = new double[10][3];
        count4 = new double[10][10];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<2; j++) {
                count[i][j]=i*j+Math.random()*5;
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
            
            for(int j=0; j<3; j++) {
                count3[i][j]=i*j+Math.random()*5;
            }
            
            for(int j=0; j<10; j++) {
                count4[i][j]=i*j+Math.random()*5;
            }
        }
    }

}
