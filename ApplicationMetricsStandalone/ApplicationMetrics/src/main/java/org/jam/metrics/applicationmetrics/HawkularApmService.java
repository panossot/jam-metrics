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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jam.metrics.applicationmetrics.utils.HttpHeadersExtractAdapter;
import org.jam.metrics.applicationmetricslibrary.HawkularApmManagers;

/**
 *
 * @author panos
 */
public class HawkularApmService {

    private Vertx vertx;
    private Router router;
    private EventBus eb;
    private Tracer tracer;
    private String group;
    private HawkularApmManagers hApmManagers;
    private HttpClient client;
    private String url = "localhost";
    private HttpServer server = null;

    public HawkularApmService(String deployment, Tracer tracer, EventBus eb) {
        vertx = Vertx.vertx();
        router = Router.router(vertx);
        this.eb = eb;
        this.tracer = tracer;
        MessageConsumer<JsonObject> getMethodConsumer = eb.consumer(group + ".endofStructure");
        if (!getMethodConsumer.isRegistered()) {
            getMethodConsumer.handler(message -> {
            });
        }

    }

    public synchronized void hawkularApm(HawkularApmManagers hApmManagers, final String group, EventBus eb) throws IllegalArgumentException, IllegalAccessException {
        try {
            this.hApmManagers = hApmManagers;
            this.group = group;
            handleApmData();

        } catch (Exception ex) {

            ex.printStackTrace();

        };

    }

    public void handleApmData() {
        try {

            Span apmRootSpan = tracer.buildSpan("POST")
                    .withTag("http.url", "/apm")
                    .withTag("service", "APMManager")
                    .withTag("transaction", "Display Code Structure")
                    .start();

            HashMap<String,Span> rootHM = new HashMap();
            rootHM.put("root", apmRootSpan);
            hApmManagers.setRootSpan(apmRootSpan);
            //       JsonObject apmRoot = buf.toJsonObject();
          //  apmRootSpan.finish();
            constructSpanSturcture();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void constructSpanSturcture() {
        try {
            if (hApmManagers.getMethodQueueIndex() > 0) {
                for (int i = 0; i < hApmManagers.getMethodQueueIndex(); i++) {
                    System.out.println("child : " + hApmManagers.getMethodQueue().get(i).getChildMethod() + ", parent : " + hApmManagers.getMethodQueue().get(i).getParentMethod());
                    final String parentMethod = hApmManagers.getMethodQueue().get(i).getParentMethod();
                    final String childMethod = hApmManagers.getMethodQueue().get(i).getChildMethod();
                    JsonObject spanObject = new JsonObject();
                    spanObject.put("parentspan", parentMethod);
                       eb.send(group + "." + childMethod, spanObject);
                }
            }
            //    eb.publish(group + ".endofStructure", apmRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
