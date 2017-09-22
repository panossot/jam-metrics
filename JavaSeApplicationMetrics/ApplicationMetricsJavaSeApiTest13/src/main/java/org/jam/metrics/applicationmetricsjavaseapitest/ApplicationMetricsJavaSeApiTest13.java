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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.jam.metrics.applicationmetricsapi.JMathPlotAdapter;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationMetricsJavaSeApiTest13 {

    private static String groupName = "myTestGroup";
    private static HashMap<String, JFrame> frames = new HashMap<String, JFrame>();
    private static HashMap<String, Plot2DPanel> plots = new HashMap<String, Plot2DPanel>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int x = 0;
            int y = 0;
            char firstAmin = ' ';
            char currentAmin = ' ';
            ArrayList<double[]> DNAencoding = new ArrayList<>();
            Map<Character,int[]> A = new HashMap<>();
            A.put('A', new int[]{-1,1});
            A.put('T', new int[]{-1,-1});
            A.put('G', new int[]{1,1});
            A.put('C', new int[]{1,-1});
            Map<Character,int[]> T = new HashMap<>();
            T.put('T', new int[]{-1,1});
            T.put('G', new int[]{-1,-1});
            T.put('C', new int[]{1,1});
            T.put('A', new int[]{1,-1});
            Map<Character,int[]> G = new HashMap<>();
            G.put('G', new int[]{-1,1});
            G.put('C', new int[]{-1,-1});
            G.put('A', new int[]{1,1});
            G.put('T', new int[]{1,-1});
            Map<Character,int[]> C = new HashMap<>();
            C.put('C', new int[]{-1,1});
            C.put('A', new int[]{-1,-1});
            C.put('T', new int[]{1,1});
            C.put('G', new int[]{1,-1});
            
            initializeMetricProperties();
            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");

            Scanner inFile1 = new Scanner(new File("./hs_ref_GRCh38.p7_chr1.fa/data"));

            StringBuilder sb = new StringBuilder();
            if (inFile1.hasNext())
                System.out.println(inFile1.nextLine());
            
            while(inFile1.hasNext()) {
                String line = inFile1.nextLine();
                if(!line.contains(">"))
                    sb.append(line);
                else
                    break;
            }

            String CH1 = sb.toString();
            
            System.out.println("CHR1 seq length: " + CH1.length());
            char[] chars = CH1.toCharArray();
            
            int k=0;
            for (char c : chars) {
                if (firstAmin==' ') {
                    firstAmin = c;
                    x = 0;
                    y = 0;
                    DNAencoding.add(new double[]{x,y});
                    currentAmin = c;
                }else {
                    int[] mappedValue = new int[]{0,0};
                    if (currentAmin=='A')
                        mappedValue = A.get(c);
                    else if(currentAmin=='C')
                        mappedValue = C.get(c);
                    else if(currentAmin=='T')
                        mappedValue = T.get(c);
                    else if(currentAmin=='G')
                        mappedValue = G.get(c);
                            
                    
                    x += mappedValue[0];
                    y += mappedValue[1];;
                    
                    DNAencoding.add(new double[]{x,y});        
                    currentAmin = c;
                    k++;
                }
                
            }
            
            double[][] data = new double[5001][2];
            data = new ArrayList<double[]>(DNAencoding.subList(0, 5001)).toArray(data);
            
            
            JMathPlotAdapter.jMathPlotAdapter(data, "myTestGroup", properties, "plot1", "data", "red", "stair", false);
            
       //     System.out.println("Last row : " + data[DNAencoding.size()-1][0] + " " + data[DNAencoding.size()-1][1]);
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    private static void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setMetricPlot("true");
        initializePlots(true);
        metricProperties.setPlots(plots);
        metricProperties.setFrames(frames);
        metricProperties.addColor("red", Color.RED);
        metricProperties.addColor("blue", Color.BLUE);
        metricProperties.addColor("green", Color.GREEN);
        metricProperties.addColor("yellow", Color.YELLOW);
        metricProperties.setPlotRefreshRate(1);
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }

    private static  void initializePlots(boolean init) {
        if (init) {
            HashMap<String, Plot2DPanel> metricPlots;
            HashMap<String, JFrame> metricFrames;

            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName);

            if (properties == null || properties.getPlots().isEmpty()) {
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

            plots = metricPlots;
        }
    }
    
}
