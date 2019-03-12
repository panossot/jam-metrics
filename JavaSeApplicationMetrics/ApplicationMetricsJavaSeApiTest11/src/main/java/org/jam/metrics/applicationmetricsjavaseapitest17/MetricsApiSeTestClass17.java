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
package org.jam.metrics.applicationmetricsjavaseapitest17;

import org.jam.metrics.applicationmetricsapi.ApplicationJavaSeMetrics;
import org.jam.metrics.applicationmetricsapi.JMathPlotAdapter;
import org.jam.metrics.applicationmetricsapi.Plot;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSeTestClass17 {

    private double count[][];
    
    private double count2[][];
    
    
    final MetricProperties properties;

    public MetricsApiSeTestClass17() {
        properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
    }

    public void countMethod() throws IllegalArgumentException, IllegalAccessException {
        count = new double[10][3];
        count2 = new double[10][3];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<3; j++) {
                count[i][j]=i*j+Math.random()*5;
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
            
        }
        
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot27", "count2", "blue", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot28", "count", "green", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot27", "count", "magenta", "line", true);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot28", "count2", "blue", "line", true);
    }
}
