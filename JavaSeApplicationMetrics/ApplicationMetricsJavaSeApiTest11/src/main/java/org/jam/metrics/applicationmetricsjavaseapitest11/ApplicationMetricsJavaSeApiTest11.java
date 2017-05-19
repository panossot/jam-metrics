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
package org.jam.metrics.applicationmetricsjavaseapitest11;

import java.awt.Color;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;

import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricsjavaseapitest12.MetricsApiSeTestClass12;
import org.jam.metrics.applicationmetricsjavaseapitest12.MetricsThreads12;
import org.jam.metrics.applicationmetricsjavaseapitest13.MetricsApiSeTestClass13;
import org.jam.metrics.applicationmetricsjavaseapitest13.MetricsThreads13;
import org.jam.metrics.applicationmetricsjavaseapitest14.MetricsApiSeTestClass14;
import org.jam.metrics.applicationmetricsjavaseapitest14.MetricsThreads14;
import org.jam.metrics.applicationmetricsjavaseapitest15.MetricsApiSeTestClass15;
import org.jam.metrics.applicationmetricsjavaseapitest15.MetricsThreads15;
import org.jam.metrics.applicationmetricsjavaseapitest16.MetricsApiSeTestClass16;
import org.jam.metrics.applicationmetricsjavaseapitest16.MetricsThreads16;
import org.jam.metrics.applicationmetricsjavaseapitest17.MetricsApiSeTestClass17;
import org.jam.metrics.applicationmetricsjavaseapitest17.MetricsThreads17;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest11 {

    private static String groupName = "myTestGroup";
    private static HashMap<String, JFrame> frames = new HashMap<String, JFrame>();
    private static HashMap<String, Plot2DPanel> plots = new HashMap<String, Plot2DPanel>();
    private static HashMap<String, Plot3DPanel> plots3D = new HashMap<String, Plot3DPanel>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass11 mTC = new MetricsApiSeTestClass11();
            MetricsThreads11 mTreads =  new MetricsThreads11("1",mTC);
            mTreads.start();
            
            MetricsThreads11 mTreads2 =  new MetricsThreads11("2",mTC);
            mTreads2.start();
            
            MetricsThreads11 mTreads3 =  new MetricsThreads11("3",mTC);
            mTreads3.start();
            
            MetricsApiSeTestClass12 mTC12 = new MetricsApiSeTestClass12();
            MetricsThreads12 mTreads4 =  new MetricsThreads12("4",mTC12);
            mTreads4.start();
            
            MetricsApiSeTestClass13 mTC13 = new MetricsApiSeTestClass13();
            MetricsThreads13 mTreads5 =  new MetricsThreads13("5",mTC13);
            mTreads5.start();
            
            MetricsApiSeTestClass14 mTC14 = new MetricsApiSeTestClass14();
            MetricsThreads14 mTreads6 =  new MetricsThreads14("6",mTC14);
            mTreads6.start();
            
            MetricsApiSeTestClass15 mTC15 = new MetricsApiSeTestClass15();
            MetricsThreads15 mTreads7 =  new MetricsThreads15("7",mTC15);
            mTreads7.start();
            
            MetricsApiSeTestClass16 mTC16 = new MetricsApiSeTestClass16();
            MetricsThreads16 mTreads8 =  new MetricsThreads16("8",mTC16);
            mTreads8.start();
            
            MetricsApiSeTestClass17 mTC17 = new MetricsApiSeTestClass17();
            MetricsThreads17 mTreads9 =  new MetricsThreads17("9",mTC17);
            mTreads9.start();

            
            while (mTreads.getT().isAlive() || mTreads2.getT().isAlive() || mTreads3.getT().isAlive() || mTreads4.getT().isAlive() || mTreads5.getT().isAlive() || mTreads6.getT().isAlive() || mTreads7.getT().isAlive() || mTreads8.getT().isAlive() || mTreads9.getT().isAlive()){};
            
       /*     Thread.sleep(30000);
            
            try {
                frames.get("plot1").setVisible(false);
                frames.get("plot1").dispose();
                frames.get("plot2").setVisible(false);
                frames.get("plot2").dispose();
                frames.get("plot3").setVisible(false);
                frames.get("plot3").dispose();
                frames.get("plot4").setVisible(false);
                frames.get("plot4").dispose();
                frames.get("plot5").setVisible(false);
                frames.get("plot5").dispose();
                frames.get("plot6").setVisible(false);
                frames.get("plot6").dispose();
                frames.get("plot7").setVisible(false);
                frames.get("plot7").dispose();
                frames.get("plot8").setVisible(false);
                frames.get("plot8").dispose();
                frames.get("plot9").setVisible(false);
                frames.get("plot9").dispose();
                frames.get("plot10").setVisible(false);
                frames.get("plot10").dispose();
                frames.get("plot11").setVisible(false);
                frames.get("plot11").dispose();
                frames.get("plot12").setVisible(false);
                frames.get("plot12").dispose();
                frames.get("plot13").setVisible(false);
                frames.get("plot13").dispose();
                frames.get("plot14").setVisible(false);
                frames.get("plot14").dispose();
                frames.get("plot15").setVisible(false);
                frames.get("plot15").dispose();
                frames.get("plot16").setVisible(false);
                frames.get("plot16").dispose();
                frames.get("plot17").setVisible(false);
                frames.get("plot17").dispose();
                frames.get("plot18").setVisible(false);
                frames.get("plot18").dispose();
                frames.get("plot19").setVisible(false);
                frames.get("plot19").dispose();
                frames.get("plot20").setVisible(false);
                frames.get("plot20").dispose();
                frames.get("plot21").setVisible(false);
                frames.get("plot21").dispose();
                frames.get("plot22").setVisible(false);
                frames.get("plot22").dispose();
                frames.get("plot23").setVisible(false);
                frames.get("plot23").dispose();
                frames.get("plot24").setVisible(false);
                frames.get("plot24").dispose();
                frames.get("plot25").setVisible(false);
                frames.get("plot25").dispose();
                frames.get("plot26").setVisible(false);
                frames.get("plot26").dispose();
                frames.get("plot27").setVisible(false);
                frames.get("plot27").dispose();
                frames.get("plot28").setVisible(false);
                frames.get("plot28").dispose();
            }catch(Exception e){}*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setCacheStore("true");
        metricProperties.setMetricPlot("true");
        DeploymentMetricProperties.getDeploymentMetricProperties().addDeploymentInternalParameters(groupName, new MetricInternalParameters());
        initializePlots(true);
        metricProperties.setPlots(plots);
        metricProperties.set3DPlots(plots3D);
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

    private static  void initializePlots(boolean init) {
        if (init) {
            HashMap<String, Plot2DPanel> metricPlots;
            HashMap<String, Plot3DPanel> metricPlots3D;
            HashMap<String, JFrame> metricFrames;

            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName);

            if (properties == null || properties.getPlots().isEmpty()) {
                metricPlots = new HashMap<String, Plot2DPanel>();
            } else {
                metricPlots = properties.getPlots();
            }
            
            if (properties == null || properties.get3DPlots().isEmpty()) {
                metricPlots3D = new HashMap<String, Plot3DPanel>();
            } else {
                metricPlots3D = properties.get3DPlots();
            }

            if (properties == null || properties.getFrames().isEmpty()) {
                metricFrames = new HashMap<String, JFrame>();
            } else {
                metricFrames = properties.getFrames();
            }

            boolean create = true;
            if (properties != null && properties.getPlots().containsKey("plot1") && properties.getFrames().get("plot1") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 1");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot1");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getPlots().remove("plot1");
                    }
                });

                metricPlots.put("plot1", plot);
                frames.put("plot1", frame);
            }

            create = true;
            if (properties != null && properties.getPlots().containsKey("plot2") && properties.getFrames().get("plot2") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot2 = new Plot2DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 2");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot2");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getPlots().remove("plot2");
                    }
                });

                metricPlots.put("plot2", plot2);
                frames.put("plot2", frame2);
            }
            
            
            if (properties != null && properties.get3DPlots().containsKey("plot3") && properties.getFrames().get("plot3") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot = new Plot3DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 3");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot3");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot3");
                    }
                });

                metricPlots3D.put("plot3", plot);
                frames.put("plot3", frame);
            }

            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot4") && properties.getFrames().get("plot4") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot2 = new Plot3DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 4");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot4");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot4");
                    }
                });

                metricPlots3D.put("plot4", plot2);
                frames.put("plot4", frame2);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot5") && properties.getFrames().get("plot5") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot3 = new Plot3DPanel("SOUTH");

                JFrame frame3 = new JFrame("Plot 5");
                frame3.setSize(600, 600);
                frame3.setContentPane(plot3);
                frame3.setVisible(true);
                frame3.setResizable(true);
                frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame3.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot5");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot5");
                    }
                });

                metricPlots3D.put("plot5", plot3);
                frames.put("plot5", frame3);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot6") && properties.getFrames().get("plot6") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot4 = new Plot3DPanel("SOUTH");

                JFrame frame4 = new JFrame("Plot 6");
                frame4.setSize(600, 600);
                frame4.setContentPane(plot4);
                frame4.setVisible(true);
                frame4.setResizable(true);
                frame4.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame4.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot6");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot6");
                    }
                });

                metricPlots3D.put("plot6", plot4);
                frames.put("plot6", frame4);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot7") && properties.getFrames().get("plot7") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot5 = new Plot3DPanel("SOUTH");

                JFrame frame5 = new JFrame("Plot 7");
                frame5.setSize(600, 600);
                frame5.setContentPane(plot5);
                frame5.setVisible(true);
                frame5.setResizable(true);
                frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame5.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot7");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot7");
                    }
                });

                metricPlots3D.put("plot7", plot5);
                frames.put("plot7", frame5);
            }
            
            if (properties != null && properties.get3DPlots().containsKey("plot8") && properties.getFrames().get("plot8") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 8");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot8");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot8");
                    }
                });

                metricPlots.put("plot8", plot);
                frames.put("plot8", frame);
            }

            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot9") && properties.getFrames().get("plot9") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot2 = new Plot2DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 9");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot9");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot9");
                    }
                });

                metricPlots.put("plot9", plot2);
                frames.put("plot9", frame2);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot10") && properties.getFrames().get("plot10") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot3 = new Plot2DPanel("SOUTH");

                JFrame frame3 = new JFrame("Plot 10");
                frame3.setSize(600, 600);
                frame3.setContentPane(plot3);
                frame3.setVisible(true);
                frame3.setResizable(true);
                frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame3.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot10");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot10");
                    }
                });

                metricPlots.put("plot10", plot3);
                frames.put("plot10", frame3);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot11") && properties.getFrames().get("plot11") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot4 = new Plot2DPanel("SOUTH");

                JFrame frame4 = new JFrame("Plot 11");
                frame4.setSize(600, 600);
                frame4.setContentPane(plot4);
                frame4.setVisible(true);
                frame4.setResizable(true);
                frame4.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame4.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot11");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot11");
                    }
                });

                metricPlots.put("plot11", plot4);
                frames.put("plot11", frame4);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot12") && properties.getFrames().get("plot12") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot5 = new Plot2DPanel("SOUTH");

                JFrame frame5 = new JFrame("Plot 12");
                frame5.setSize(600, 600);
                frame5.setContentPane(plot5);
                frame5.setVisible(true);
                frame5.setResizable(true);
                frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame5.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot12");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot12");
                    }
                });

                metricPlots.put("plot12", plot5);
                frames.put("plot12", frame5);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot13") && properties.getFrames().get("plot13") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot6 = new Plot2DPanel("SOUTH");

                JFrame frame6 = new JFrame("Plot 13");
                frame6.setSize(600, 600);
                frame6.setContentPane(plot6);
                frame6.setVisible(true);
                frame6.setResizable(true);
                frame6.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame6.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot13");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot13");
                    }
                });

                metricPlots.put("plot13", plot6);
                frames.put("plot13", frame6);
            }
            
            if (properties != null && properties.get3DPlots().containsKey("plot14") && properties.getFrames().get("plot14") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 14");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot14");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot14");
                    }
                });

                metricPlots.put("plot14", plot);
                frames.put("plot14", frame);
            }

            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot15") && properties.getFrames().get("plot15") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot2 = new Plot2DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 15");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot15");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot15");
                    }
                });

                metricPlots.put("plot15", plot2);
                frames.put("plot15", frame2);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot16") && properties.getFrames().get("plot16") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot3 = new Plot2DPanel("SOUTH");

                JFrame frame3 = new JFrame("Plot 16");
                frame3.setSize(600, 600);
                frame3.setContentPane(plot3);
                frame3.setVisible(true);
                frame3.setResizable(true);
                frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame3.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot16");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot16");
                    }
                });

                metricPlots.put("plot16", plot3);
                frames.put("plot16", frame3);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot17") && properties.getFrames().get("plot17") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot4 = new Plot2DPanel("SOUTH");

                JFrame frame4 = new JFrame("Plot 17");
                frame4.setSize(600, 600);
                frame4.setContentPane(plot4);
                frame4.setVisible(true);
                frame4.setResizable(true);
                frame4.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame4.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot17");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot17");
                    }
                });

                metricPlots.put("plot17", plot4);
                frames.put("plot17", frame4);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot18") && properties.getFrames().get("plot18") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot5 = new Plot2DPanel("SOUTH");

                JFrame frame5 = new JFrame("Plot 18");
                frame5.setSize(600, 600);
                frame5.setContentPane(plot5);
                frame5.setVisible(true);
                frame5.setResizable(true);
                frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame5.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot18");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot18");
                    }
                });

                metricPlots.put("plot18", plot5);
                frames.put("plot18", frame5);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot19") && properties.getFrames().get("plot19") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot6 = new Plot2DPanel("SOUTH");

                JFrame frame6 = new JFrame("Plot 19");
                frame6.setSize(600, 600);
                frame6.setContentPane(plot6);
                frame6.setVisible(true);
                frame6.setResizable(true);
                frame6.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame6.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot19");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot19");
                    }
                });

                metricPlots.put("plot19", plot6);
                frames.put("plot19", frame6);
            }
            
            if (properties != null && properties.get3DPlots().containsKey("plot20") && properties.getFrames().get("plot20") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot = new Plot3DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 20");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot20");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot20");
                    }
                });

                metricPlots3D.put("plot20", plot);
                frames.put("plot20", frame);
            }

            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot21") && properties.getFrames().get("plot21") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot2 = new Plot3DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 21");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot21");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot21");
                    }
                });

                metricPlots3D.put("plot21", plot2);
                frames.put("plot21", frame2);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot22") && properties.getFrames().get("plot22") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot3 = new Plot3DPanel("SOUTH");

                JFrame frame3 = new JFrame("Plot 22");
                frame3.setSize(600, 600);
                frame3.setContentPane(plot3);
                frame3.setVisible(true);
                frame3.setResizable(true);
                frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame3.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot22");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot22");
                    }
                });

                metricPlots3D.put("plot22", plot3);
                frames.put("plot22", frame3);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot23") && properties.getFrames().get("plot23") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot4 = new Plot3DPanel("SOUTH");

                JFrame frame4 = new JFrame("Plot 23");
                frame4.setSize(600, 600);
                frame4.setContentPane(plot4);
                frame4.setVisible(true);
                frame4.setResizable(true);
                frame4.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame4.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot23");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot23");
                    }
                });

                metricPlots3D.put("plot23", plot4);
                frames.put("plot23", frame4);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot24") && properties.getFrames().get("plot24") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot5 = new Plot3DPanel("SOUTH");

                JFrame frame5 = new JFrame("Plot 24");
                frame5.setSize(600, 600);
                frame5.setContentPane(plot5);
                frame5.setVisible(true);
                frame5.setResizable(true);
                frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame5.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot24");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot24");
                    }
                });

                metricPlots3D.put("plot24", plot5);
                frames.put("plot24", frame5);
            }
            
            if (properties != null && properties.get3DPlots().containsKey("plot25") && properties.getFrames().get("plot25") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot3 = new Plot2DPanel("SOUTH");

                JFrame frame3 = new JFrame("Plot 25");
                frame3.setSize(600, 600);
                frame3.setContentPane(plot3);
                frame3.setVisible(true);
                frame3.setResizable(true);
                frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame3.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot25");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot25");
                    }
                });

                metricPlots.put("plot25", plot3);
                frames.put("plot25", frame3);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot26") && properties.getFrames().get("plot26") != null) {
                create = false;
            }
            
            if (create) {
                Plot2DPanel plot6 = new Plot2DPanel("SOUTH");

                JFrame frame6 = new JFrame("Plot 26");
                frame6.setSize(600, 600);
                frame6.setContentPane(plot6);
                frame6.setVisible(true);
                frame6.setResizable(true);
                frame6.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame6.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot26");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot26");
                    }
                });

                metricPlots.put("plot26", plot6);
                frames.put("plot26", frame6);
            }
            
            if (properties != null && properties.get3DPlots().containsKey("plot27") && properties.getFrames().get("plot27") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot2 = new Plot3DPanel("SOUTH");

                JFrame frame2 = new JFrame("Plot 27");
                frame2.setSize(600, 600);
                frame2.setContentPane(plot2);
                frame2.setVisible(true);
                frame2.setResizable(true);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot27");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot27");
                    }
                });

                metricPlots3D.put("plot27", plot2);
                frames.put("plot27", frame2);
            }
            
            create = true;
            if (properties != null && properties.get3DPlots().containsKey("plot28") && properties.getFrames().get("plot28") != null) {
                create = false;
            }
            
            if (create) {
                Plot3DPanel plot5 = new Plot3DPanel("SOUTH");

                JFrame frame5 = new JFrame("Plot 28");
                frame5.setSize(600, 600);
                frame5.setContentPane(plot5);
                frame5.setVisible(true);
                frame5.setResizable(true);
                frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame5.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot28");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot28");
                    }
                });

                metricPlots3D.put("plot28", plot5);
                frames.put("plot28", frame5);
            }

            plots = metricPlots;
            plots3D = metricPlots3D;
        }
    }
    
}
