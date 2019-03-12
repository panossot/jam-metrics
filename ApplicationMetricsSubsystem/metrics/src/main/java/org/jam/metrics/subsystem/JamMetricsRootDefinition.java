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

package org.jam.metrics.subsystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

/**
 * @author <a href="mailto:panossot@gmail.com">Panagiotis Sotiropoulos</a> (c) 2015 
 */
class JamMetricsRootDefinition extends PersistentResourceDefinition {

    static final JamMetricsRootDefinition INSTANCE = new JamMetricsRootDefinition();

    protected static final SimpleAttributeDefinition RHQ_MONITORING_ATTRIBUTE
            = new SimpleAttributeDefinitionBuilder(Constants.RHQ_MONITORING, ModelType.BOOLEAN, true)
            .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
            .setDefaultValue(new ModelNode(false))
            .build();

    protected static final SimpleAttributeDefinition CACHE_STORE_ATTRIBUTE
            = new SimpleAttributeDefinitionBuilder(Constants.CACHE_STORE, ModelType.BOOLEAN, true)
            .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
            .setDefaultValue(new ModelNode(false))
            .build();

    static final PersistentResourceDefinition[] CHILDREN = {};

    static final AttributeDefinition[] ATTRIBUTES = {RHQ_MONITORING_ATTRIBUTE, CACHE_STORE_ATTRIBUTE};

    private JamMetricsRootDefinition() {
        super(JamMetricsExtension.SUBSYSTEM_PATH,
                JamMetricsExtension.getResolver(),
                JamMetricsSubsystemAdd.INSTANCE,
                ReloadRequiredRemoveStepHandler.INSTANCE);
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Arrays.asList(ATTRIBUTES);
    }

    @Override
    protected List<? extends PersistentResourceDefinition> getChildren() {
        return Arrays.asList(CHILDREN);
    }
}
