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
package org.jboss.as.automatedmetrics.subsystem.logging;

import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.DotName;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import static org.jboss.logging.Logger.Level.INFO;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

/**
 * @author Panagiotis Sotiropoulos (c) 2015 Red Hat Inc.
 */
@MessageLogger(projectCode = "WFLYMETRICS", length = 3)
public interface AutomatedMetricsLogger extends BasicLogger {

    /**
     * A root logger with the category of the package name.
     */
    AutomatedMetricsLogger ROOT_LOGGER = Logger.getMessageLogger(AutomatedMetricsLogger.class, "org.jboss.metrics.automatedmetricssubsystem.logging");

    @LogMessage(level = INFO)
    @Message(id = 1, value = "Starting JBoss-Automated-Metrics Subsystem.")
    void startedAutomatedMetricsSubsystem();

    @LogMessage(level = INFO)
    @Message(id = 2, value = "Stopping JBoss-Automated-Metrics Subsystem.")
    void stoppingAutomatedMetricsSubsystem();

    @Message(id = 3, value = "Failed to load annotated class: %s")
    String classLoadingFailed(DotName clazz);

    @Message(id = 4, value = "Annotation %s in class %s is only allowed on classes")
    String invalidAnnotationLocation(Object annotation, AnnotationTarget classInfo);
}
