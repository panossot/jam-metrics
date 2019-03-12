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

import org.jam.metrics.subsystem.logging.JamMetricsLogger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * @author <a href="mailto:panossot@gmail.com">Panagiotis Sotiropoulos</a> (c) 2015 
 */
public final class JamMetricsServices implements Service<JamMetricsServices> {

    public static final ServiceName AUTOMATED_METRICS = ServiceName.JBOSS.append("metrics");

    protected JamMetricsServices() {
    }

    @Override
    public void start(StartContext sc) throws StartException {
        JamMetricsLogger.ROOT_LOGGER.startedJamMetricsSubsystem();
    }

    @Override
    public void stop(StopContext sc) {
        JamMetricsLogger.ROOT_LOGGER.stoppingJamMetricsSubsystem();
    }

    @Override
    public JamMetricsServices getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }
}
