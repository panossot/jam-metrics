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
package org.jam.metrics.subsystem;

import org.jam.metrics.subsystem.deployment.JamMetricsBeanValidationFactoryProcessor;
import org.jam.metrics.subsystem.deployment.JamMetricsDependencyProcessor;
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
class JamMetricsSubsystemAdd extends AbstractBoottimeAddStepHandler {

    static final JamMetricsSubsystemAdd INSTANCE = new JamMetricsSubsystemAdd();

    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        JamMetricsRootDefinition.RHQ_MONITORING_ATTRIBUTE.validateAndSet(operation, model);
        JamMetricsRootDefinition.CACHE_STORE_ATTRIBUTE.validateAndSet(operation, model);
    }

    @Override
    protected void performBoottime(OperationContext context, ModelNode operation, final ModelNode model) throws OperationFailedException {

        context.addStep(new AbstractDeploymentChainStep() {
            @Override
            protected void execute(DeploymentProcessorTarget processorTarget) {
            //    processorTarget.addDeploymentProcessor(jam-metricsExtension.SUBSYSTEM_NAME, Phase.STRUCTURE, Phase.STRUCTURE_AUTOMATED_METRICS, new jam-metricsDependencyProcessor());
                processorTarget.addDeploymentProcessor(JamMetricsExtension.SUBSYSTEM_NAME, Phase.DEPENDENCIES, Phase.DEPENDENCIES_METRIC, new JamMetricsDependencyProcessor());
                //    processorTarget.addDeploymentProcessor(jam-metricsExtension.SUBSYSTEM_NAME, Phase.DEPENDENCIES, Phase.DEPENDENCIES_METRIC, new jam-metricsInterceptorProcessor());
                processorTarget.addDeploymentProcessor(JamMetricsExtension.SUBSYSTEM_NAME, Phase.INSTALL, Phase.INSTALL_METRIC_VALIDATOR_FACTORY, new JamMetricsBeanValidationFactoryProcessor());
            }
        }, OperationContext.Stage.RUNTIME);
        //  jam-metricsLogger.ROOT_LOGGER.startedjam-metricsSubsystem();

        /*    ServiceTarget target = context.getServiceTarget();
         target.addService(jam-metricsServices.AUTOMATED_METRICS, new jam-metricsServices())
         .setInitialMode(ServiceController.Mode.ACTIVE)
         .install();*/
    }
}
