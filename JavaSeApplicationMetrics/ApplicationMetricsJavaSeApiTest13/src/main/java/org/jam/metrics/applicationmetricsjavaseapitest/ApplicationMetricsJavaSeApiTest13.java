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
package org.jam.metrics.applicationmetricsjavaseapitest;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private static int limit = 5000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            
            HashMap<Character,int[]> A = new HashMap<>();
            A.put('A', new int[]{-1,1});
            A.put('T', new int[]{-1,-1});
            A.put('G', new int[]{1,1});
            A.put('C', new int[]{1,-1});
            HashMap<Character,int[]> T = new HashMap<>();
            T.put('T', new int[]{-1,1});
            T.put('G', new int[]{-1,-1});
            T.put('C', new int[]{1,1});
            T.put('A', new int[]{1,-1});
            HashMap<Character,int[]> G = new HashMap<>();
            G.put('G', new int[]{-1,1});
            G.put('C', new int[]{-1,-1});
            G.put('A', new int[]{1,1});
            G.put('T', new int[]{1,-1});
            HashMap<Character,int[]> C = new HashMap<>();
            C.put('C', new int[]{-1,1});
            C.put('A', new int[]{-1,-1});
            C.put('T', new int[]{1,1});
            C.put('G', new int[]{1,-1});
            
            initializeMetricProperties();
            

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
            
            int k = 0;
            for (int iA=0; iA<4; iA++) {
                if (iA!=0) {
                    rotateMap(A);
                }
                for (int iT=0; iT<4; iT++) {
                    if (iT!=0) {
                        rotateMap(T);
                    }
                    for (int iG=0; iG<4; iG++) {
                        if (iG!=0) {
                            rotateMap(G);
                        }
                        for (int iC=0; iC<4; iC++) {
                            if (iC!=0) {
                                rotateMap(C);
                            }
                            k++;
                            displayDNA(chars,A,T,G,C,k);
                        }
                    }
                }
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
        
    private static HashMap<Character,int[]> rotateMap(HashMap<Character,int[]> mapArray) {
        
        Iterator<Character> iter = mapArray.keySet().iterator();
        Character firstChar = null;
        int[] firstValue = null;
        Character thisChar = null;
        Character prevChar = null;
        int[] prevValue = null;
        
        if (iter.hasNext()) {
            firstChar = iter.next();
            firstValue = mapArray.get(firstChar);
            prevChar = firstChar;
            prevValue = mapArray.get(thisChar);
        }
        
        while (iter.hasNext()) {
            thisChar = iter.next();
            mapArray.put(prevChar, mapArray.get(thisChar));
            prevChar = thisChar;
        }
        
        if (mapArray.size()>0) {
            mapArray.put(prevChar,firstValue);
        }
        
        return mapArray;
    }
    
    private static void displayDNA(char[] chars, Map<Character,int[]> A, Map<Character,int[]> T, Map<Character,int[]> G,Map<Character,int[]> C, int num) throws IllegalArgumentException, IllegalAccessException{
        int x = 0;
            int y = 0;
            char firstAmin = ' ';
            char currentAmin = ' ';
            ArrayList<double[]> DNAencoding = new ArrayList<>();
            ArrayList<double[]> A_ = new ArrayList<>();
            ArrayList<double[]> C_ = new ArrayList<>();
            ArrayList<double[]> T_ = new ArrayList<>();
            ArrayList<double[]> G_ = new ArrayList<>();
            
            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
            
        int k=0;
            for (char c : chars) {
                if (k<=limit) {
                    if (firstAmin==' ') {
                        firstAmin = c;
                        x = 0;
                        y = 0;
                        DNAencoding.add(new double[]{x,y});
                        currentAmin = c;

                        if (currentAmin=='A')
                            A_.add(new double[]{0,0});
                        else if(currentAmin=='C')
                            C_.add(new double[]{0,0});
                        else if(currentAmin=='T')
                            T_.add(new double[]{0,0});
                        else if(currentAmin=='G')
                            G_.add(new double[]{0,0});

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
                        y += mappedValue[1];

                        DNAencoding.add(new double[]{x,y});        
                        currentAmin = c;

                        if (currentAmin=='A')
                            A_.add(new double[]{x,y});
                        else if(currentAmin=='C')
                            C_.add(new double[]{x,y});
                        else if(currentAmin=='T')
                            T_.add(new double[]{x,y});
                        else if(currentAmin=='G')
                            G_.add(new double[]{x,y});
                    }
                    k++;
                }
            }
            
            double[][] data = new double[DNAencoding.size()][2];
            data = new ArrayList<double[]>(DNAencoding).toArray(data);
            
            
            JMathPlotAdapter.jMathPlotAdapter(data, "myTestGroup", properties, "plot1", "data_" + num, "red", "line", false);
            
            double[][] A2Array = new double[A_.size()][2];
            A2Array = new ArrayList<double[]>(A_).toArray(A2Array);
            double[][] T2Array = new double[T_.size()][2];
            T2Array = new ArrayList<double[]>(T_).toArray(T2Array);
            double[][] C2Array = new double[C_.size()][2];
            C2Array = new ArrayList<double[]>(C_).toArray(C2Array);
            double[][] G2Array = new double[G_.size()][2];
            G2Array = new ArrayList<double[]>(G_).toArray(G2Array);

            JMathPlotAdapter.jMathPlotAdapter(A2Array, "myTestGroup", properties, "plot2", "A_" + num, "magenta", "scatter", false);
            JMathPlotAdapter.jMathPlotAdapter(C2Array, "myTestGroup", properties, "plot3", "C_" + num, "blue", "scatter", false);
            JMathPlotAdapter.jMathPlotAdapter(T2Array, "myTestGroup", properties, "plot4", "T_" + num, "green", "scatter", false);
            JMathPlotAdapter.jMathPlotAdapter(G2Array, "myTestGroup", properties, "plot5", "G_" + num, "yellow", "scatter", false);
            
            System.out.println("Last row : " + data[DNAencoding.size()-1][0] + " " + data[DNAencoding.size()-1][1]);
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
        metricProperties.addColor("magenta", Color.MAGENTA);
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
            
            create = true;
            if (properties != null && properties.getPlots().containsKey("plot2") && properties.getFrames().get("plot2") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 2");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot2");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getPlots().remove("plot2");
                    }
                });

                metricPlots.put("plot2", plot);
                frames.put("plot2", frame);
            }

            create = true;
            if (properties != null && properties.getPlots().containsKey("plot3") && properties.getFrames().get("plot3") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 3");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot3");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getPlots().remove("plot3");
                    }
                });

                metricPlots.put("plot3", plot);
                frames.put("plot3", frame);
            }
            
            create = true;
            if (properties != null && properties.getPlots().containsKey("plot4") && properties.getFrames().get("plot4") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 4");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot4");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getPlots().remove("plot4");
                    }
                });

                metricPlots.put("plot4", plot);
                frames.put("plot4", frame);
            }
            
            create = true;
            if (properties != null && properties.getPlots().containsKey("plot5") && properties.getFrames().get("plot5") != null) {
                create = false;
            }

            if (create) {
                Plot2DPanel plot = new Plot2DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 5");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot5");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getPlots().remove("plot5");
                    }
                });

                metricPlots.put("plot5", plot);
                frames.put("plot5", frame);
            }
            
            plots = metricPlots;
        }
    }
    
}
