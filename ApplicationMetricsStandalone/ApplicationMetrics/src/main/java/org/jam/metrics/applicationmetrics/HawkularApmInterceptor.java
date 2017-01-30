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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentracing.APMSpan;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.hawkular.apm.client.opentracing.APMTracer;
import org.jam.metrics.applicationmetricsapi.HawkularApm;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.HawkularApmManagers;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.logging.Logger;
import org.jam.metrics.applicationmetricslibrary.ChildParentMethod;

/**
 *
 * @author panos
 */
@HawkularApm
@Interceptor
public class HawkularApmInterceptor {
    
    private Logger logger = Logger.getLogger(HawkularApmInterceptor.class);
    private final static Tracer tracer = new APMTracer();
    private final static Object hawkularApmLock = new Object();
    private final static Vertx vertx = Vertx.vertx();
    private final static EventBus eb = vertx.eventBus();
    
    public HawkularApmInterceptor() {
    }
    
    @AroundInvoke
    public Object hawkularApmInterceptor(InvocationContext ctx) throws Exception {
        
        Method method = ctx.getMethod();
        
        try {
            final HawkularApm hawkularApmAnnotation = method.getAnnotation(HawkularApm.class);
            
            if (hawkularApmAnnotation != null) {
                final String group = hawkularApmAnnotation.groupName();
                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                final String hawkularApm = properties.getHawkularApm();
                
                if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
                    String threadName = Thread.currentThread().getName();
                    String[] submethods = hawkularApmAnnotation.childMethods();
                    
                    final MetricInternalParameters internalParams = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group);
                    
                    HawkularApmManagers hApmManagers = internalParams.getHawkularApmManagers(threadName);
                    if (hApmManagers == null) {
                        internalParams.putHawkularApmManagers(threadName, new HawkularApmManagers());
                        hApmManagers = internalParams.getHawkularApmManagers(threadName);
                    }
                    
                    final HawkularApmManagers hm = hApmManagers;
                    MessageConsumer<JsonObject> getMethodConsumer = eb.consumer(group + "." + method.getName());
                    if (!getMethodConsumer.isRegistered()) {
                        getMethodConsumer.handler(message -> {
                            
                            try {
                                System.out.println(group + "." + method.getName());
                                System.out.println("hello : " + message.body().getString("parentspan"));
                                Span spanObject = null;
                                if (hm.getFromSpanStore(message.body().getString("parentspan"))!=null)
                                    spanObject = hm.getFromSpanStore(message.body().getString("parentspan"));
                                else
                                    spanObject = hm.getRootSpan();
                                //   Span spanObject = Json.mapper.convertValue ( message.body().getMap().get(hawkularApmLock), Span.class );
                                SpanContext parentSpan = spanObject.context();
                                System.out.println("pSpan : " + parentSpan.toString());
                                
                                
                                Span childSpan = tracer.buildSpan(group + "." + method.getName())
                                        .asChildOf(parentSpan)
                                        .withTag("service", method.getName())
                                        .start();
                                
                                hm.putInSpanStore(method.getName(), childSpan);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                    
                    int submethodLength = submethods.length;
                    if (submethodLength != 0) {
                        for (int i = submethodLength - 1; i >= 0; i--) {
                            hApmManagers.getMethodQueue().add(hApmManagers.getMethodQueueIndex(), new ChildParentMethod(submethods[i], method.getName()));
                        }
                    } else {
                        hApmManagers.getMethodQueue().add(hApmManagers.getMethodQueueIndex(), new ChildParentMethod(null, method.getName()));
                    }
                    
                    hApmManagers.setMethodQueueIndex(hApmManagers.getMethodQueueIndex() + 1);
                
                    HawkularApmService hawkularApmInstance;
                    synchronized (hawkularApmLock) {
                        hawkularApmInstance = HawkularApmCollection.getHawkularApmCollection().getHawkularApmInstance(group);
                        if (hawkularApmInstance == null) {
                            hawkularApmInstance = new HawkularApmService(group, tracer, eb);
                            HawkularApmCollection.getHawkularApmCollection().addHawkularApmInstance(group, hawkularApmInstance);
                        }
                    }

                    try {
                        hawkularApmInstance.hawkularApm(hApmManagers,group,eb);
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Object result = ctx.proceed();
        
        return result;
    }
    
}
