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
package org.jboss.as.automatedmetrics.subsystem;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;
import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;


/**
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
class AutomatedMetricsSubsystemParser_1_0 extends PersistentResourceXMLParser {

    static final AutomatedMetricsSubsystemParser_1_0 INSTANCE = new AutomatedMetricsSubsystemParser_1_0();

    private final PersistentResourceXMLDescription xmlDescription;

    private AutomatedMetricsSubsystemParser_1_0() {
        xmlDescription = builder(AutomatedMetricsRootDefinition.INSTANCE, Namespace.METRICS_1_0.getUriString())
                .addAttributes(
                        AutomatedMetricsRootDefinition.RHQ_MONITORING_ATTRIBUTE,
                        AutomatedMetricsRootDefinition.CACHE_STORE_ATTRIBUTE
                ).build();
    }

    @Override
    public PersistentResourceXMLDescription getParserDescription() {
        return xmlDescription;
    }
}
