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
