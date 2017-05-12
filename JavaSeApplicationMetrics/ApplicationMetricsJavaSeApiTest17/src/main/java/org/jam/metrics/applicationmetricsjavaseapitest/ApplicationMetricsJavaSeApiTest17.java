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
import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;

import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.math.plot.Plot3DPanel;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest17 {

    private static String groupName = "myTestGroup";
    private static HashMap<String, JFrame> frames = new HashMap<String, JFrame>();
    private static HashMap<String, Plot3DPanel> plots = new HashMap<String, Plot3DPanel>();
    
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
                frames.get("plot2").setVisible(false);
                frames.get("plot2").dispose();
                frames.get("plot5").setVisible(false);
                frames.get("plot5").dispose();
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
        metricProperties.set3DPlots(plots);
        metricProperties.setFrames(frames);
        metricProperties.addColor("red", Color.RED);
        metricProperties.addColor("blue", Color.BLUE);
        metricProperties.addColor("green", Color.GREEN);
        metricProperties.addColor("yellow", Color.YELLOW);
        metricProperties.addColor("magenta", Color.MAGENTA);
        metricProperties.setPlotRefreshRate(1);
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }

    private static void initializePlots(boolean init) {
        if (init) {
            HashMap<String, Plot3DPanel> metricPlots;
            HashMap<String, JFrame> metricFrames;

            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName);

            if (properties == null || properties.get3DPlots().isEmpty()) {
                metricPlots = new HashMap<String, Plot3DPanel>();
            } else {
                metricPlots = properties.get3DPlots();
            }

            if (properties == null || properties.getFrames().isEmpty()) {
                metricFrames = new HashMap<String, JFrame>();
            } else {
                metricFrames = properties.getFrames();
            }

            boolean create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot2") && properties.getFrames().get("plot2") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot2 = new Plot3DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 2");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot2");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot2");
                    }
                });

                metricPlots.put("plot2", plot2);
                frames.put("plot2", frame2);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot5") && properties.getFrames().get("plot5") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot5 = new Plot3DPanel("SOUTH");

                JFrame frame5 = new JFrame("Plot 5");
                frame5.setSize(600, 600);
                frame5.setContentPane(plot5);
                frame5.setVisible(true);
                frame5.setResizable(true);
                frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame5.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot5");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot5");
                    }
                });

                metricPlots.put("plot5", plot5);
                frames.put("plot5", frame5);
            }
            
            plots = metricPlots;
        }
    }
    
}
