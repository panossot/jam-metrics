/*
 * Copyleft 2015  by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

package org.jam.metrics.javase.applicationmetricsjavaseapi;

import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary2.CodeParamsCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
public class ApplicationJavaSeMetricFilter {

    public static void metricFilter(Object value, final String metricName, final String metricGroup, final double comparableValue, final String filterParamName, final String userName, final boolean largerThan, final boolean smallerThan, final boolean equalsWith) throws Exception {

        try {
            final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(metricGroup);
            String filterMetrics = properties.getFilterMetrics();

            if (filterMetrics != null && Boolean.parseBoolean(filterMetrics)) {
                final double fieldValue = Double.parseDouble(value.toString());
                CodeParams cp = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(userName);

                if (fieldValue > comparableValue) {
                    cp.setFilterParam(filterParamName, largerThan);
                }
                if (fieldValue < comparableValue) {
                    cp.setFilterParam(filterParamName, smallerThan);
                }
                if (fieldValue == comparableValue) {
                    cp.setFilterParam(filterParamName, equalsWith);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
