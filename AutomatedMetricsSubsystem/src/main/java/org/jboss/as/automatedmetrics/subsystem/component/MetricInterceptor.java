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
package org.jboss.as.automatedmetrics.subsystem.component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.metrics.automatedmetricsapi.Metric;

/**
 *
 * @author panos
 */
@Metric
@Interceptor
public class MetricInterceptor {

    private final Map<String, Field> metricFields = new HashMap();
    private Field field;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {

        Object result = ctx.proceed();

        Method method = ctx.getMethod();

        Metric metricAnnotation = method.getAnnotation(Metric.class);
        if (metricAnnotation != null) {
            int fieldNameSize = metricAnnotation.fieldName().length;

            for (int i = 0; i < fieldNameSize; i++) {

                accessField(metricAnnotation, method, i);
                Store.CacheStore(ctx.getTarget(), field);
                if (Boolean.parseBoolean(System.getProperty("rhqMonitoring", "false"))) {
                    MonitoringRhq.getRhq().rhqMonitoring(ctx.getTarget(), field);
                }
            }
        }

        return result;
    }

    private void accessField(Metric metricAnnotation, Method method, int fieldNum) throws Exception {
        if (metricFields.containsKey(metricAnnotation.fieldName()[fieldNum])) {
            field = metricFields.get(metricAnnotation.fieldName()[fieldNum]);
        } else {
            field = method.getDeclaringClass().getDeclaredField(metricAnnotation.fieldName()[fieldNum]);
            field.setAccessible(true);
            metricFields.put(metricAnnotation.fieldName()[fieldNum], field);
        }
    }
}
