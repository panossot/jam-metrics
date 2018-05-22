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

 /*
 *  ΙΔΕΑ : Everything is a potential metric .
 */
package org.jam.metrics.applicationmetricsapi;

import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsPropertiesApi {

    static HttpServer server = null;
    
    public static void RestApi(String host, int port) {
        URI baseUri = UriBuilder.fromUri(host).port(port).build();
        ResourceConfig config = new ResourceConfig(RestJam.class);
        server = JdkHttpServerFactory.createHttpServer(baseUri, config);
    }
    
    public static void stopServer(){
        server.stop(1);
        server = null;
    }

    public static void storeProperties(String group, MetricProperties metricsProperties) {
        DeploymentMetricProperties.getDeploymentMetricProperties().addDeploymentProperties(group, metricsProperties);
        DeploymentMetricProperties.getDeploymentMetricProperties().addDeploymentInternalParameters(group, new MetricInternalParameters());
    }
    
    public static MetricProperties getProperties(String group) {
        return (DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group));
    }

    public static synchronized void clearProperties() {
        DeploymentMetricProperties.getDeploymentMetricProperties().clearDeploymentProperties();
    }
}
