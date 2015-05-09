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

import org.jboss.as.automatedmetrics.subsystem.deployment.AutomatedMetricsBeanValidationFactoryProcessor;
//import org.jboss.as.automatedmetrics.subsystem.deployment.AutomatedMetricsInterceptorProcessor;
import org.jboss.as.automatedmetrics.subsystem.deployment.AutomatedMetricsDependencyProcessor;
import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Phase;
import org.jboss.dmr.ModelNode;

/**
 * Handler responsible for adding the subsystem resource to the model
 *
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
class AutomatedMetricsSubsystemAdd extends AbstractBoottimeAddStepHandler {

    static final AutomatedMetricsSubsystemAdd INSTANCE = new AutomatedMetricsSubsystemAdd();

    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        AutomatedMetricsRootDefinition.RHQ_MONITORING_ATTRIBUTE.validateAndSet(operation, model);
        AutomatedMetricsRootDefinition.CACHE_STORE_ATTRIBUTE.validateAndSet(operation, model);
    }

    @Override
    protected void performBoottime(OperationContext context, ModelNode operation, final ModelNode model) throws OperationFailedException {

        context.addStep(new AbstractDeploymentChainStep() {
            @Override
            protected void execute(DeploymentProcessorTarget processorTarget) {
            //    processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.STRUCTURE, Phase.STRUCTURE_AUTOMATED_METRICS, new AutomatedMetricsDependencyProcessor());
                processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.DEPENDENCIES, Phase.DEPENDENCIES_METRIC, new AutomatedMetricsDependencyProcessor());
                //    processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.DEPENDENCIES, Phase.DEPENDENCIES_METRIC, new AutomatedMetricsInterceptorProcessor());
                processorTarget.addDeploymentProcessor(AutomatedMetricsExtension.SUBSYSTEM_NAME, Phase.INSTALL, Phase.INSTALL_METRIC_VALIDATOR_FACTORY, new AutomatedMetricsBeanValidationFactoryProcessor());
            }
        }, OperationContext.Stage.RUNTIME);
        //  AutomatedMetricsLogger.ROOT_LOGGER.startedAutomatedMetricsSubsystem();

        /*    ServiceTarget target = context.getServiceTarget();
         target.addService(AutomatedMetricsServices.AUTOMATED_METRICS, new AutomatedMetricsServices())
         .setInitialMode(ServiceController.Mode.ACTIVE)
         .install();*/
    }
}
