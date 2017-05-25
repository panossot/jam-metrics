package org.jam.metrics.ejb.remote.client;

import java.awt.Color;
import java.util.HashMap;
import org.jam.metrics.ejb.remote.stateful.RemoteCalculator;
import org.jam.metrics.ejb.remote.stateless.RemoteCalculator2;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.jam.metrics.applicationmetricsapi.JMathPlotAdapter;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.math.plot.Plot3DPanel;

public class RemoteEJBClientWebStart {

    private static String groupName = "myTestGroup";
    private static HashMap<String, JFrame> frames = new HashMap<String, JFrame>();
    private static HashMap<String, Plot3DPanel> plots = new HashMap<String, Plot3DPanel>();

    
    public static void main(String[] args) throws Exception {
        // Invoke a stateful bean
        initializeMetricProperties();
        invokeStatefulBean();
        Thread.sleep(10000);
        invokeStatelessBean();
    }
    

    /**
     * Looks up a stateless bean and invokes on it
     *
     * @throws NamingException
     */
    private static void invokeStatelessBean() throws NamingException, IllegalArgumentException, IllegalAccessException {
        // Let's lookup the remote stateless calculator
        final RemoteCalculator2 statelessRemoteCalculator = lookupRemoteStatelessCalculator();
        double[][] count = statelessRemoteCalculator.countMethod();
        double[][] count2 = statelessRemoteCalculator.countMethod2();
        final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot1", "count2", "blue", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot2", "count", "red", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot1", "count", "red", "line", true);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot2", "count2", "blue", "line", true);
    }


    /**
     * Looks up and returns the proxy to remote stateless calculator bean
     *
     * @return
     * @throws NamingException
     */
    private static RemoteCalculator2 lookupRemoteStatelessCalculator() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateless session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : EAP allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.

        // let's do the lookup
        return (RemoteCalculator2) context.lookup("/jboss-ejb-remote-applet-server-side/CalculationBean2!"
            + RemoteCalculator2.class.getName());
    }

    /**
     * Looks up a stateful bean and invokes on it
     *
     * @throws NamingException
     */
    private static void invokeStatefulBean() throws NamingException, IllegalArgumentException, IllegalAccessException {
        final RemoteCalculator statefulRemoteCalculator = lookupRemoteStatefulCalculator();
        double[][] count = statefulRemoteCalculator.countMethod();
        double[][] count2 = statefulRemoteCalculator.countMethod2();
        final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot1", "count2", "blue", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot2", "count", "red", "scatter", true);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot1", "count", "red", "line", true);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot2", "count2", "blue", "line", true);
    }


    /**
     * Looks up and returns the proxy to remote stateful counter bean
     *
     * @return
     * @throws NamingException
     */
    private static RemoteCalculator lookupRemoteStatefulCalculator() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateful session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>?stateful
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : EAP allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.

        // let's do the lookup
        return (RemoteCalculator) context.lookup("/jboss-ejb-remote-applet-server-side/CalculationBean!"
            + RemoteCalculator.class.getName());
    }
    
    private static void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setCacheStore("false");
        metricProperties.setMetricPlot("true");
        DeploymentMetricProperties.getDeploymentMetricProperties().addDeploymentInternalParameters(groupName, new MetricInternalParameters());
        initializePlots(true);
        metricProperties.set3DPlots(plots);
        metricProperties.setFrames(frames);
        metricProperties.addColor("red", Color.RED);
        metricProperties.addColor("blue", Color.BLUE);
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
            if (properties != null && properties.get3DPlots().containsKey("plot1") && properties.getFrames().get("plot1") != null) {
                create = false;
            }

            if (create) {
                Plot3DPanel plot = new Plot3DPanel("SOUTH");

                JFrame frame = new JFrame("Plot 1");
                frame.setSize(600, 600);
                frame.setContentPane(plot);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).getFrames().remove("plot1");
                        DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(groupName).get3DPlots().remove("plot1");
                    }
                });

                metricPlots.put("plot1", plot);
                frames.put("plot1", frame);
            }

            create = true;
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
            
            plots = metricPlots;
        }
    }
}
