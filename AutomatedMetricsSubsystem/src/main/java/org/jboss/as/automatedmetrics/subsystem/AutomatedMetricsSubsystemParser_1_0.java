/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
