/*
 *
 *  JBoss, Home of Professional Open Source.
 *  Copyright 2015, Red Hat, Inc., and individual contributors
 *  as indicated by the @author tags. See the copyright.txt file in the
 *  distribution for a full listing of individual contributors.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * /
 */
package org.jboss.as.automatedmetrics.subsystem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
class AutomatedMetricsApiResourceDefinition extends PersistentResourceDefinition {

    static final SimpleAttributeDefinition RHQ_MONITORING
            = new SimpleAttributeDefinitionBuilder(Constants.RHQ_MONITORING, ModelType.BOOLEAN, true)
            .setAllowExpression(true)
            .setDefaultValue(new ModelNode(true))
            .build();

    static AttributeDefinition[] ATTRIBUTES = new AttributeDefinition[]{
        RHQ_MONITORING
    };

    static final Map<String, AttributeDefinition> ATTRIBUTES_BY_XMLNAME;

    static {
        Map<String, AttributeDefinition> attrs = new HashMap<>();
        for (AttributeDefinition attr : ATTRIBUTES) {
            attrs.put(attr.getXmlName(), (AttributeDefinition) attr);
        }
        ATTRIBUTES_BY_XMLNAME = Collections.unmodifiableMap(attrs);
    }

    static final AutomatedMetricsApiResourceDefinition INSTANCE = new AutomatedMetricsApiResourceDefinition();

    private AutomatedMetricsApiResourceDefinition() {
        super(AutomatedMetricsExtension.AUTOMATED_METRICS_API_PATH,
                AutomatedMetricsExtension.getResolver(String.valueOf(Constants.AUTOMATED_METRICS_RECORD)),
                new AutomatedMetricsApiAdd(),
                ReloadRequiredRemoveStepHandler.INSTANCE
        );
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return (Collection) ATTRIBUTES_BY_XMLNAME.values();
    }
}
