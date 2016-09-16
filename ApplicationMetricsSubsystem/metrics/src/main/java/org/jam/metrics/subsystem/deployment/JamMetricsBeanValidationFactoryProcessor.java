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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */

package org.jam.metrics.subsystem.deployment;

import static javax.faces.validator.BeanValidator.VALIDATOR_FACTORY_KEY;

import javax.validation.ValidatorFactory;

import org.jboss.as.ee.beanvalidation.BeanValidationAttachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.web.common.ServletContextAttribute;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */

/**
 * Deployment processor that adds the CDI-enabled ValidatorFactory to the servlet context.
 */
public class JamMetricsBeanValidationFactoryProcessor implements DeploymentUnitProcessor {

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();

        // Get the CDI-enabled ValidatorFactory and add it to the servlet context
        ValidatorFactory validatorFactory = deploymentUnit.getAttachment(BeanValidationAttachments.VALIDATOR_FACTORY);

        deploymentUnit.addToAttachmentList(ServletContextAttribute.ATTACHMENT_KEY,
                new ServletContextAttribute(VALIDATOR_FACTORY_KEY, validatorFactory));
    }

    @Override
    public void undeploy(DeploymentUnit context) {
    }
}
