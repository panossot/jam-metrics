/*
 * Copyleft 2017 Red Hat, Inc. and/or its affiliates
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
package org.jam.metrics;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.util.concurrent.CountDownLatch;
import org.apache.http.HttpResponse;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsApiSessionBean {

    private Vertx vertx;
    private Router router;
    private String url = "http://127.0.0.1:8180/apm";
    private HttpServer server = null;
    private HttpClient client;

    public MetricsApiSessionBean() {
        vertx = Vertx.vertx();
        router = Router.router(vertx);

        router.route(HttpMethod.POST, "/apm").handler(this::handleApmData);

        server = vertx.createHttpServer().requestHandler(router::accept).listen(8180);
    }

    public void countMethod() throws Exception {
        try {

            client = vertx.createHttpClient();

            HttpClientRequest cr = client.post(url, new Handler<HttpClientResponse>() {

                @Override
                public void handle(HttpClientResponse httpClientResponse) {

                    httpClientResponse.bodyHandler(new Handler<Buffer>() {
                        @Override
                        public void handle(Buffer buffer) {
                            System.out.println("Response (" + buffer.length() + "): ");
                            System.out.println(buffer.getString(0, buffer.length()));
                        }
                    });
                }
            });
            cr.end();

            System.out.println("\nSending 'POST' request to URL : " + url);

            CountDownLatch latch = new CountDownLatch(1);
            server.close(v -> latch.countDown());
            latch.await();
            System.out.println("11111111333333331111111111777777777");
        } catch (Exception ex) {
            try {
                System.out.println("11111111333333331111111111");
                CountDownLatch latch = new CountDownLatch(1);
                server.close(v -> latch.countDown());
                latch.await();
                System.out.println("111111113333333311111111116666666666666");
                ex.printStackTrace();
            } catch (InterruptedException ex1) {
                System.out.println("11111111333333331111111111");
                ex1.printStackTrace();
            }
        }

    }

    public void handleApmData(RoutingContext routingContext) {
        routingContext.request().bodyHandler(buf -> {
            try {
                JsonObject apmRoot = buf.toJsonObject();
                System.out.println("11111111333333331111111111" + buf.toString());
            } catch (Exception ex) {
                System.out.println("11111111333333331111111111");
                ex.printStackTrace();
            }
        });

    }

}
