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

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
class AutomatedMetricsApiAdd extends AbstractAddStepHandler {

    AutomatedMetricsApiAdd() {
        super(AutomatedMetricsApiResourceDefinition.ATTRIBUTES);
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        final PathAddress address = PathAddress.pathAddress(operation.get(OP_ADDR));
        final PathAddress hostAddress = address.subAddress(0, address.size() - 1);
        final PathAddress serverAddress = hostAddress.subAddress(0, hostAddress.size() - 1);
        final String rhqMonitoring = AutomatedMetricsApiResourceDefinition.RHQ_MONITORING.resolveModelAttribute(context, model).asString();
        final AutomatedMetricsApiService service = new AutomatedMetricsApiService(rhqMonitoring);
        final String serverName = serverAddress.getLastElement().getValue();
        final String hostName = hostAddress.getLastElement().getValue();
        final ServiceName serviceName = AutomatedMetricsServices.accessAutomatedMetricsApi(serverName, hostName);
        final ServiceBuilder<AutomatedMetricsApiService> builder = context.getServiceTarget().addService(serviceName, service);
        builder.setInitialMode(ServiceController.Mode.ACTIVE)
                .install();
    }
}
