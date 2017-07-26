/*
 * Copyleft 2016 Red Hat, Inc. and/or its affiliates
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
package org.jam.metrics.applicationmetrics;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.hawkular.apm.client.opentracing.APMTracer;
import org.jam.metrics.applicationmetricsapi.HawkularApm;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.HawkularApmManagers;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jam.metrics.applicationmetricslibrary.ChildParentMethod;

/**
 *
 * @author panos
 */
@HawkularApm
@Interceptor
public class HawkularApmInterceptor {

    

    @AroundInvoke
    public Object hawkularApmInterceptor(InvocationContext ctx) throws Exception {
        Method method = ctx.getMethod();

        try {
            final HawkularApm hawkularApmAnnotation = method.getAnnotation(HawkularApm.class);

            if (hawkularApmAnnotation != null) {
                HawkularApmAdapter.hawkularApmAdapter(hawkularApmAnnotation, method);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Object result = ctx.proceed();

        return result;
    }

}
