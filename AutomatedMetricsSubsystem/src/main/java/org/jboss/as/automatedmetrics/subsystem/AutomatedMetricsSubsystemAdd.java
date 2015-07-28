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
