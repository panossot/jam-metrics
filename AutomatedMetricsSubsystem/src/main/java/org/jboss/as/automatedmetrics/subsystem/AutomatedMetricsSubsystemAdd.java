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

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;
import org.jboss.as.automatedmetrics.subsystem.logging.AutomatedMetricsLogger;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceTarget;

/**
 * Handler responsible for adding the subsystem resource to the model
 *
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
class AutomatedMetricsSubsystemAdd extends AbstractBoottimeAddStepHandler {

    static final AutomatedMetricsSubsystemAdd INSTANCE = new AutomatedMetricsSubsystemAdd();

    private AutomatedMetricsSubsystemAdd() {
    }

    @Override
    protected void performBoottime(OperationContext context, ModelNode operation, Resource resource) throws OperationFailedException {

        context.addStep(new AbstractDeploymentChainStep() {
            @Override
            protected void execute(DeploymentProcessorTarget processorTarget) {
                /*        processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.STRUCTURE, Phase.STRUCTURE_REGISTER_JBOSS_ALL_UNDERTOW_SHARED_SESSION, new JBossAllXmlParserRegisteringProcessor<SharedSessionManagerConfig>(SharedSessionConfigParser_1_0.ROOT_ELEMENT, UndertowAttachments.SHARED_SESSION_MANAGER_CONFIG, SharedSessionConfigParser_1_0.INSTANCE));
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.STRUCTURE, Phase.STRUCTURE_REGISTER_JBOSS_ALL_WEB, new JBossAllXmlParserRegisteringProcessor<>(WebJBossAllParser.ROOT_ELEMENT, WebJBossAllParser.ATTACHMENT_KEY, new WebJBossAllParser()));
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.STRUCTURE, Phase.STRUCTURE_WAR_DEPLOYMENT_INIT, new WarDeploymentInitializingProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.STRUCTURE, Phase.STRUCTURE_WAR, new WarStructureDeploymentProcessor(sharedWebBuilder.create(), sharedTldsBuilder));
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_WEB_DEPLOYMENT, new WebParsingDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_WEB_DEPLOYMENT_FRAGMENT, new WebFragmentParsingDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_JBOSS_WEB_DEPLOYMENT, new JBossWebParsingDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_ANNOTATION_WAR, new WarAnnotationDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_EAR_CONTEXT_ROOT, new EarContextRootProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_WEB_MERGE_METADATA, new WarMetaDataProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_WEB_MERGE_METADATA + 1, new TldParsingDeploymentProcessor()); //todo: fix priority
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_WEB_MERGE_METADATA + 2, new org.wildfly.extension.undertow.deployment.WebComponentProcessor()); //todo: fix priority
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.DEPENDENCIES, Phase.DEPENDENCIES_WAR_MODULE, new UndertowDependencyProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.POST_MODULE, Phase.POST_MODULE_UNDERTOW_WEBSOCKETS, new UndertowJSRWebSocketDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.POST_MODULE, Phase.POST_MODULE_UNDERTOW_HANDLERS, new UndertowHandlersDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.POST_MODULE, Phase.POST_MODULE_UNDERTOW_HANDLERS + 1, new ExternalTldParsingDeploymentProcessor()); //todo: fix priority
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.INSTALL, Phase.INSTALL_SHARED_SESSION_MANAGER, new SharedSessionManagerDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.INSTALL, Phase.INSTALL_SERVLET_INIT_DEPLOYMENT, new ServletContainerInitializerDeploymentProcessor());
                 processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.INSTALL, Phase.INSTALL_WAR_DEPLOYMENT, new UndertowDeploymentProcessor(defaultVirtualHost, defaultContainer, defaultServer));
                 */            }

        }, OperationContext.Stage.RUNTIME);
        AutomatedMetricsLogger.ROOT_LOGGER.startedAutomatedMetricsSubsystem();

        ServiceTarget target = context.getServiceTarget();
        target.addService(AutomatedMetricsServices.AUTOMATED_METRICS, new AutomatedMetricsServices())
                .setInitialMode(ServiceController.Mode.ACTIVE)
                .install();
    }
}
