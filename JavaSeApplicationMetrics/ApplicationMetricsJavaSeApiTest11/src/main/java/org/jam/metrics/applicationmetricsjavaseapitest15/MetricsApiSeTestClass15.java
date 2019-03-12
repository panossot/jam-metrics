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
package org.jam.metrics.applicationmetricsjavaseapitest15;

import org.jam.metrics.applicationmetricsapi.ApplicationJavaSeMetrics;
import org.jam.metrics.applicationmetricsapi.JMathPlotAdapter;
import org.jam.metrics.applicationmetricsapi.Plot;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSeTestClass15 {

    private double count[][];
    
    private double count2[][];
    
    private double count3[][];
    
    private double count4[][];
    
    private double count5[][];
    
    private double count6[][];
    
    private double count7[][];
    
    private double count8[][];
    
    final MetricProperties properties;

    public MetricsApiSeTestClass15() {
        properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
    }

    public void countMethod() throws IllegalArgumentException, IllegalAccessException {
        count = new double[10][3];
        count2 = new double[10][3];
        count3 = new double[10][10];
        count4 = new double[10][10];
        count5 = new double[10][5];
        count6 = new double[10][5];
        count7 = new double[10][6];
        count8 = new double[10][6];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<3; j++) {
                count[i][j]=i*j+Math.random()*5;
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
            
            for(int j=0; j<10; j++) {
                count3[i][j]=i*j+Math.random()*5;
                count4[i][j]=i*j+Math.random()*4;
            }
            
            for(int j=0; j<5; j++) {
                if (j<3) {
                    count5[i][j]=i*j+Math.random()*5;
                    count6[i][j]=i*j+Math.random()*5;
                }else if (j<5) {
                    count5[i][j]=1;
                    count6[i][j]=1;
                }
            }
            
            for(int j=0; j<6; j++) {
                if (j<3) {
                    count7[i][j]=i*j+Math.random()*5;
                    count8[i][j]=i*j+Math.random()*5;
                }else {
                    count7[i][j]=1;
                    count8[i][j]=1;
                }
            }
        }
        
        
        JMathPlotAdapter.jMathPlotAdapter(count7, "myTestGroup", properties, "plot20", "count7", "red", "box", true);
        JMathPlotAdapter.jMathPlotAdapter(count8, "myTestGroup", properties, "plot20", "count8", "blue", "box", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot21", "count", "blue", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot21", "count2", "green", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count5, "myTestGroup", properties, "plot22", "count5", "green", "histogram", true);
        JMathPlotAdapter.jMathPlotAdapter(count6, "myTestGroup", properties, "plot22", "count6", "magenta", "histogram", true);
        JMathPlotAdapter.jMathPlotAdapter(count3, "myTestGroup", properties, "plot23", "count3", "yellow", "grid", true);
        JMathPlotAdapter.jMathPlotAdapter(count4, "myTestGroup", properties, "plot23", "count4", "red", "grid", true);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot24", "count2", "magenta", "line", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot24", "count", "blue", "line", true);
    }
}
