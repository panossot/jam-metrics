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

import org.jboss.as.automatedmetrics.subsystem.logging.AutomatedMetricsLogger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
public final class AutomatedMetricsServices implements Service<AutomatedMetricsServices> {

    public static final ServiceName AUTOMATED_METRICS = ServiceName.JBOSS.append("metrics");

    protected AutomatedMetricsServices() {
    }

    @Override
    public void start(StartContext sc) throws StartException {
        AutomatedMetricsLogger.ROOT_LOGGER.startedAutomatedMetricsSubsystem();
    }

    @Override
    public void stop(StopContext sc) {
        AutomatedMetricsLogger.ROOT_LOGGER.stoppingAutomatedMetricsSubsystem();
    }

    @Override
    public AutomatedMetricsServices getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }
}
