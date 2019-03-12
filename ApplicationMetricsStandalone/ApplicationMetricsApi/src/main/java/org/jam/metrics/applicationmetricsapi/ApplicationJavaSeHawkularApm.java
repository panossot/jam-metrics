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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */
package org.jam.metrics.applicationmetricsapi;

import io.opentracing.Tracer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hawkular.apm.client.opentracing.APMTracer;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ApplicationJavaSeHawkularApm {

    static final Tracer tracer = new APMTracer();

    public static synchronized void hawkularApm(final String method, String group, String[] submethods, boolean isEnd) throws Exception {
        String hawkularApm = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getHawkularApm();
        
        try {
            HawkularApmAdapter.hawkularApmAdapter(tracer, hawkularApm, method, group, submethods, isEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
