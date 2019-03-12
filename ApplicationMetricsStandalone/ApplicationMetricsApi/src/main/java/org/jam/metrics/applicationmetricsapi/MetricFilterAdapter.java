/*
 * Copyleft 2016 
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

import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary2.CodeParamsCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class MetricFilterAdapter {

    protected static void metricFilterAdapter(String filterMetrics, MetricProperties properties, Object value, final String metricName, final String metricGroup, final double comparableValue, final String filterParamName, final String userName, final boolean largerThan, final boolean smallerThan, final boolean equalsWith) {

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
    }

}
