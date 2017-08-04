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
package org.jam.metrics.applicationmetricsapi;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import org.jam.metrics.applicationmetricslibrary.ChildParentMethod;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary.HawkularApmManagers;
import org.jam.metrics.applicationmetricslibrary.MetricInternalParameters;

/**
 *
 * @author panos
 */
class HawkularApmAdapter {

//    private final static Tracer tracer = new APMTracer();
    private final static Object hawkularApmLock = new Object();
 //   private final static Vertx vertx = Vertx.vertx();
    private final static String[] containExclude = new String[]{"getStackTrace", "Intercept", "invoke", "Invoke", "proceed", "hawkularApmAdapter", "hawkularApm"};
    private static CountDownLatch latch;

    protected static synchronized void hawkularApmAdapter(Tracer tracer, String hawkularApm, String method, String group, String[] submethods, boolean isEnd) throws IllegalArgumentException, IllegalAccessException, Exception {
        
        
        if (hawkularApm != null && Boolean.parseBoolean(hawkularApm)) {
            EventBus eb = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getEventBus();
            
            
            String threadName = Thread.currentThread().getName();

            final MetricInternalParameters internalParams = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group);

            HawkularApmManagers hApmManagers = internalParams.getHawkularApmManagers(threadName);
            if (hApmManagers == null) {
                internalParams.putHawkularApmManagers(threadName, new HawkularApmManagers());
                hApmManagers = internalParams.getHawkularApmManagers(threadName);
                //    System.out.println("threadname : " + threadName);
            }

            HawkularApmManagers hm = hApmManagers;
            MessageConsumer<JsonObject> getMethodConsumer = eb.consumer(threadName + "." + group + "." + method);
            if (!getMethodConsumer.isRegistered()) {
                getMethodConsumer.handler((Message<JsonObject> message) -> {
                    latch = hm.getLatch();
                    //    System.out.println("latch " + latch.getCount() + " " + hm.getThreadName());
                    try {
                        //        System.out.println(group + "." + method);
                        //        System.out.println("hello : " + message.body().getString("parentspan"));
                        Span spanObject = null;
                        if (message.body().getInteger("index") > 0) {
                            spanObject = hm.getFromSpanStore(message.body().getString("parentspan"));
                           SpanContext parentSpan = spanObject.context();
                           Span childSpan = tracer.buildSpan(group + "." + method)
                                    .asChildOf(parentSpan)
                                    .withTag("service", method)
                                    .withTag("transaction", method)
                                    .start();

                            //            System.out.println("method.getName2() " + method);
                            hm.addInSpanStore(method, childSpan);
                        } else if (message.body().getInteger("index") == 0) {
                            spanObject = hm.getRootSpan();
                            SpanContext parentSpan = spanObject.context();
                            Span childSpan = tracer.buildSpan(group + "." + method)
                                    //    .addReference(threadName, parentSpan)
                                    .withTag("thread", threadName)
                                    .withTag("http.url", method)
                                    .asChildOf(parentSpan)
                                    .withTag("service", method)
                                    .withTag("transaction", method)
                                    .start();

                            //            System.out.println("method " + method);
                            hm.addInSpanStore(method, childSpan);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        latch.countDown();
                        //        System.out.println("latch2 " + latch.getCount() + " " + hm.getThreadName());
                    }
                });
                
            }

            synchronized (hawkularApmLock) {
                if (hApmManagers.getMethodQueuesDone().size() == 0) {
                    hApmManagers.getMethodQueuesDone().add(new ArrayList<>());
                    hApmManagers.getMethodQueuesDone().get(0).add(new ChildParentMethod(method, null, null));
                }

                if (hApmManagers.getMethodQueuesToDo().size() == 0) {
                    hApmManagers.getMethodQueuesToDo().add(new ArrayList<>());
                }

                if (hApmManagers.getMethodQueuesDone().size() != 0) {
                    ChildParentMethod toDo = null;
                    if (hApmManagers.getMethodQueuesToDo().get(hApmManagers.getMethodQueuesToDo().size() - 1).size() != 0) {
                        toDo = hApmManagers.getMethodQueuesToDo().get(hApmManagers.getMethodQueuesToDo().size() - 1).remove(0);
                    }

                    String parentMethodName = null;
                    int j = 0;
                    while (excludeParentMethod(Thread.currentThread().getStackTrace()[j].getMethodName())) {
                        j++;
                    }
                    parentMethodName = Thread.currentThread().getStackTrace()[j].getMethodName();
                    if (parentMethodName.compareTo(method) == 0) {
                        j++;
                        while (excludeParentMethod(Thread.currentThread().getStackTrace()[j].getMethodName())) {
                            j++;
                        }
                        parentMethodName = Thread.currentThread().getStackTrace()[j].getMethodName();
                    }
                    //    System.out.println("parentMethodName = " + parentMethodName + " " + method);
                    if (hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).size() != 0
                            && hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).size() - 1).getChildMethod().compareTo(parentMethodName) == 0
                            || (hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).size() != 0
                            && hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).size() - 1).getParentMethod() == null)
                            || (toDo != null && toDo.getChildMethod().compareTo(method) == 0) && toDo.getParentMethod().compareTo(parentMethodName) == 0) {
                        while (hApmManagers.getMethodQueuesToDo().size() < hApmManagers.getMethodQueuesDone().size()) {
                            hApmManagers.getMethodQueuesToDo().add(new ArrayList<>());
                        }
                        if (toDo != null) {
                            hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).add(toDo);
                        }
                    } else {
                        if (toDo != null) {
                            hApmManagers.getMethodQueuesToDo().get(hApmManagers.getMethodQueuesToDo().size() - 1).add(0, toDo);
                        }

                        if (hApmManagers.getMethodQueuesDone().size() != 0) {
                            hApmManagers.getMethodQueuesDone().add(new ArrayList<>());
                            hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).add(new ChildParentMethod(method, null, null));
                            hApmManagers.getMethodQueuesToDo().add(new ArrayList<>());
                        }
                    }

                    int submethodLength = submethods.length;

                    if (submethodLength == 0) {
                        hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).size() - 1).setIsEnd(isEnd);
                    }

                    if (submethodLength != 0) {
                        for (int i = submethodLength - 1; i >= 0; i--) {
                            hApmManagers.getMethodQueuesToDo().get(hApmManagers.getMethodQueuesToDo().size() - 1).add(0, new ChildParentMethod(submethods[i], method, parentMethodName));
                        }
                    } else if (hApmManagers.getMethodQueuesToDo().get(hApmManagers.getMethodQueuesToDo().size() - 1).isEmpty()) {
                        hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).add(new ChildParentMethod(null, method, parentMethodName));

                        if (hm.getRootSpan() == null) {
                            Span sObject = tracer.buildSpan(group + " - thread : " + threadName)
                                    //        .addReference(group + ".main", internalParams.getConnector().context())
                                    .withTag("http.url", threadName)
                                    .start();
                            hm.setRootSpan(sObject);
                            hm.getRootSpan().finish();
                        }

                        for (int i = 0; i < hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).size(); i++) {
                            //        System.out.println("child : " + hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i).getChildMethod() + ", parent : " + hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i).getParentMethod());
                            String parentMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i).getParentMethod();
                            String childMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i).getChildMethod();
                            JsonObject spanObject = new JsonObject();
                            latch = new CountDownLatch(1);
                            hm.setLatch(latch);

                            spanObject.put("parentspan", parentMethod);
                            if (parentMethod == null) {
                                spanObject.put("index", 0);
                            } else {
                                spanObject.put("index", 1);
                            }
                            if (childMethod != null) {
                                eb.send(threadName + "." + group + "." + childMethod, spanObject);
                            //                System.out.println("****** " + group + "." + childMethod);
                                //                System.out.println("latch4 " + latch.getCount() + " " + threadName);
                                latch.await();
                            }

                            if (hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i).isIsEnd()) {
                                String nextParentMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i + 1).getParentMethod();
                                int k = 0;

                                String prevParentMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i - k).getParentMethod();
                                String prevChildMethod = null;
                                if (prevParentMethod != null) {
                                    while (prevParentMethod.compareTo(nextParentMethod) != 0) {
                                        prevChildMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i - k).getChildMethod();
                                        Span rSpan = hApmManagers.removeFromSpanStore(prevChildMethod);
                                        if (rSpan != null) {
                                            rSpan.finish();
                                        }
                                        k++;
                                        prevParentMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i - k).getParentMethod();
                                        if (prevParentMethod == null) {
                                            break;
                                        }
                                    }
                                }
                                prevChildMethod = hApmManagers.getMethodQueuesDone().get(hApmManagers.getMethodQueuesDone().size() - 1).get(i - k).getChildMethod();
                                if (prevChildMethod != null) {
                                    Span rSpan = hApmManagers.removeFromSpanStore(prevChildMethod);
                                    if (rSpan != null) {
                                        rSpan.finish();
                                    }
                                }
                            }

                        }

                        for (ArrayList<Span> arraySpan : hApmManagers.getSpanStore().values()) {
                            for (Span value : arraySpan) {
                                value.finish();
                            }
                        }

                        hApmManagers.getMethodQueuesDone().remove(hApmManagers.getMethodQueuesDone().size() - 1);
                        hApmManagers.getMethodQueuesToDo().remove(hApmManagers.getMethodQueuesToDo().size() - 1);

                        if (hApmManagers.getMethodQueuesDone().isEmpty() && hApmManagers.getMethodQueuesToDo().isEmpty()) {
                            hApmManagers.setRootSpan(null);
                        }
                    }
                }
            }
        }
    }

    private static boolean excludeParentMethod(String parentMethod) {
        boolean found = false;

        for (int i = 0; i < containExclude.length; i++) {
            if (parentMethod.contains(containExclude[i])) {
                found = true;
                break;
            }
        }

        return found;
    }

}
