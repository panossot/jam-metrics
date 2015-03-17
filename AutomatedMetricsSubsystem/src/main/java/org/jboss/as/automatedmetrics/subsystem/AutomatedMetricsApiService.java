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

import java.util.ArrayList;
import java.util.Map;
import static org.jboss.metrics.automatedmetricsapi.MetricsCache.getMetricsCache;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panagiotis Sotiropoulos</a> (c) 2015 Red Hat Inc.
 */
public class AutomatedMetricsApiService implements Service<AutomatedMetricsApiService> {

    private volatile Map<String, ArrayList<Object>> metricsCache;

    public AutomatedMetricsApiService(String rhqMonitoring) {
        System.setProperty(Constants.RHQ_MONITORING, rhqMonitoring);
    }

    @Override
    public void start(StartContext startContext) throws StartException {
        try {
            metricsCache = getMetricsCache();
        } catch (IllegalStateException e) {
            throw new StartException(e);
        }
    }

    @Override
    public void stop(StopContext stopContext) {
    }

    @Override
    public AutomatedMetricsApiService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    Map<String, ArrayList<Object>> getMetrics() {
        return getMetricsCache();
    }

    public void disableRhqMonitoring() {
        System.setProperty(Constants.RHQ_MONITORING, "false");
    }

    public void enableRhqMonitoring() {
        System.setProperty(Constants.RHQ_MONITORING, "true");
    }
}
