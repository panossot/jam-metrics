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
package org.rhq.plugins.metrics;

import java.util.Set;
import org.rhq.core.domain.measurement.AvailabilityType;
import org.rhq.core.domain.measurement.MeasurementReport;
import org.rhq.core.domain.measurement.MeasurementScheduleRequest;
import org.rhq.core.pluginapi.inventory.InvalidPluginConfigurationException;
import org.rhq.core.pluginapi.inventory.ResourceComponent;
import org.rhq.core.pluginapi.inventory.ResourceContext;
import org.rhq.core.pluginapi.measurement.MeasurementFacet;

/**
 * @author Panagiotis Sotiropoulos
 *
 */
public class MetricsComponent implements ResourceComponent, MeasurementFacet {

    public AvailabilityType getAvailability() {
        return AvailabilityType.UP;
    }

    public void start(ResourceContext context) throws InvalidPluginConfigurationException, Exception {
    }

    public void stop() {
        // TODO Auto-generated method stub
    }

    public void getValues(MeasurementReport mr, Set<MeasurementScheduleRequest> set) throws Exception {
    }
}
