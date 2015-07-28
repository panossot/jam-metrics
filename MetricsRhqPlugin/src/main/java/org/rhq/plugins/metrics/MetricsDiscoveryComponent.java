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

import java.util.HashSet;
import java.util.Set;

import org.rhq.core.domain.configuration.Configuration;
import org.rhq.core.domain.resource.ResourceType;
import org.rhq.core.pluginapi.inventory.DiscoveredResourceDetails;
import org.rhq.core.pluginapi.inventory.InvalidPluginConfigurationException;
import org.rhq.core.pluginapi.inventory.ResourceDiscoveryComponent;
import org.rhq.core.pluginapi.inventory.ResourceDiscoveryContext;

/**
 * @author Panagiotis Sotiropoulos
 *
 */
public class MetricsDiscoveryComponent implements ResourceDiscoveryComponent {

    public Set discoverResources(ResourceDiscoveryContext context) throws InvalidPluginConfigurationException,
        Exception {

        Set<DiscoveredResourceDetails> result = new HashSet<DiscoveredResourceDetails>();

        String key = "Metrics";
        String name = key;
        String description = "Jboss-Automated-Metrics";
        Configuration configuration = null;
        ResourceType resourceType = context.getResourceType();
        DiscoveredResourceDetails detail = new DiscoveredResourceDetails(resourceType, key, name, null, description,
            configuration, null);

        result.add(detail);

        return result;
    }

}
