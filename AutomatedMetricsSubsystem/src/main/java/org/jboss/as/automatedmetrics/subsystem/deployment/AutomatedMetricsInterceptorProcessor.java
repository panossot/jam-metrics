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
package org.jboss.as.automatedmetrics.subsystem.deployment;

import org.jboss.as.automatedmetrics.subsystem.component.Attachments;
import org.jboss.as.automatedmetrics.subsystem.component.MetricDeploymentAttachmentKeys;
import org.jboss.as.automatedmetrics.subsystem.component.MetricInterceptor;
import org.jboss.as.automatedmetrics.subsystem.component.MetricObject;
import org.jboss.as.automatedmetrics.subsystem.component.MetricsCacheSingleton;
import org.jboss.as.automatedmetrics.subsystem.component.MonitoringRhq;
import org.jboss.as.automatedmetrics.subsystem.component.Store;
import org.jboss.as.automatedmetrics.subsystem.component.utils.DoubleValue;
import org.jboss.as.automatedmetrics.subsystem.component.utils.RhqScheduleIds;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;

/**
 * {@link DeploymentUnitProcessor} implementation responsible for extracting JSF annotations from a deployment and attaching
 * them to the deployment unit to eventually be added to the {@link javax.servlet.ServletContext}.
 *
 * @author Panos
 */
public class AutomatedMetricsInterceptorProcessor implements DeploymentUnitProcessor {

    @Override
    public void deploy(final DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();

        final MetricInterceptor metricInterceptor = deploymentUnit.getAttachment(Attachments.METRICS_METRICINTERCEPTOR);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.METRICINTERCEPTOR_NAME, metricInterceptor);
        final MetricObject metricObject = deploymentUnit.getAttachment(Attachments.METRICS_METRICOBJECT);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.METRICOBJECT_NAME, metricObject);
        final MetricsCacheSingleton metricsCacheSingleton = deploymentUnit.getAttachment(Attachments.METRICS_METRICSCACHESINGLETON);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.METRICSCHACHESINGLETON_NAME, metricsCacheSingleton);
        final MonitoringRhq monitoringRhq = deploymentUnit.getAttachment(Attachments.METRICS_MONITORINGRHQ);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.MONITORING_NAME, monitoringRhq);
        final DoubleValue doubleValue = deploymentUnit.getAttachment(Attachments.METRICS_DOUBLEVALUE);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.DOUBLEVALUE_NAME, doubleValue);
        final RhqScheduleIds rhqScheduleIds = deploymentUnit.getAttachment(Attachments.METRICS_RHQSHEDULEIDS);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.RHQSCHEDULEIDS_NAME, rhqScheduleIds);
        final Store store = deploymentUnit.getAttachment(Attachments.METRICS_STORE);
        deploymentUnit.putAttachment(MetricDeploymentAttachmentKeys.STORE_NAME, store);

    }

    @Override
    public void undeploy(DeploymentUnit context) {

    }
}
