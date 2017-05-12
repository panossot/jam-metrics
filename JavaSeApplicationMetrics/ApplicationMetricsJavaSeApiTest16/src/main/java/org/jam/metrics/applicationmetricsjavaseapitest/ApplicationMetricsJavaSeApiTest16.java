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
package org.jam.metrics.applicationmetricsjavaseapitest;

import java.awt.Color;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest16 {

    private static String groupName = "myTestGroup";
    private static HashMap<String, JFrame> frames = new HashMap<String, JFrame>();
    private static HashMap<String, Plot2DPanel> plots = new HashMap<String, Plot2DPanel>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass mTC = new MetricsApiSeTestClass();
            MetricsThreads mTreads =  new MetricsThreads("1",mTC);
            mTreads.start();
            
            Thread.sleep(10000);
            
            try {
                frames.get("plot3").setVisible(false);
                frames.get("plot3").dispose();
                frames.get("plot6").setVisible(false);
                frames.get("plot6").dispose();
            }catch(Exception e){}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setCacheStore("false");
        metricProperties.setMetricPlot("true");
        DeploymentMetricProperties.getDeploymentMetricProperties().addDeploymentInternalParameters(groupName, new MetricInternalParameters());
        initializePlots(false);
        metricProperties.setPlots(plots);
        metricProperties.setFrames(frames);
        metricProperties.addColor("red", Color.RED);
        metricProperties.addColor("blue", Color.BLUE);
        metricProperties.addColor("green", Color.GREEN);
        metricProperties.addColor("yellow", Color.YELLOW);
        metricProperties.addColor("magenta", Color.MAGENTA);
        metricProperties.addColor("cyan", Color.CYAN);
        metricProperties.setPlotRefreshRate(1);
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }

    private static void initializePlots(boolean init) {
        if (init) {
            HashMap<String, Plot2DPanel> metricPlots;
            HashMap<String, JFrame> metricFrames;

            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName);

            if (properties == null || properties.get3DPlots().isEmpty()) {
                metricPlots = new HashMap<String, Plot2DPanel>();
            } else {
                metricPlots = properties.getPlots();
            }

            if (properties == null || properties.getFrames().isEmpty()) {
                metricFrames = new HashMap<String, JFrame>();
            } else {
                metricFrames = properties.getFrames();
            }

            boolean create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot3") && properties.getFrames().get("plot3") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot3 = new Plot2DPanel("SOUTH");

                JFrame frame3 = new JFrame("Plot 3");
                frame3.setSize(600, 600);
                frame3.setContentPane(plot3);
                frame3.setVisible(true);
                frame3.setResizable(true);
                frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame3.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot3");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot3");
                    }
                });

                metricPlots.put("plot3", plot3);
                frames.put("plot3", frame3);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot6") && properties.getFrames().get("plot6") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot6 = new Plot2DPanel("SOUTH");

                JFrame frame6 = new JFrame("Plot 6");
                frame6.setSize(600, 600);
                frame6.setContentPane(plot6);
                frame6.setVisible(true);
                frame6.setResizable(true);
                frame6.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame6.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot6");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot6");
                    }
                });

                metricPlots.put("plot6", plot6);
                frames.put("plot6", frame6);
            }
            
            plots = metricPlots;
        }
    }
    
}
