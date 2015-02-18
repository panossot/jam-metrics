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
package org.jboss.metrics.automatedmetrics;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.metrics.automatedmetrics.utils.DoubleValue;

/**
 *
 * @author panos
 */
public class MonitoringRhq {

    private static final MonitoringRhq mrhq = new MonitoringRhq();

    private final String APPLICATION_JSON;
    private final Header acceptJson;
    private static int REST_SERVER_PORT;
    private static InetAddress REST_SERVER_ADDRESS;

    private MonitoringRhq() {
        APPLICATION_JSON = "application/json";
        REST_SERVER_PORT = Integer.parseInt(System.getProperty("rest.port", "7080"));
        try {
            REST_SERVER_ADDRESS = InetAddress.getByName(System.getProperty("rest.server", "localhost"));
        } catch (UnknownHostException ex) {
            Logger.getLogger(MonitoringRhq.class.getName()).log(Level.SEVERE, null, ex);
        }
        RestAssured.baseURI = "http://" + System.getProperty("rest.server", "localhost");
        RestAssured.port = REST_SERVER_PORT;
        RestAssured.basePath = "/rest/";
        RestAssured.authentication = basic(System.getProperty("rhqUsername", "rhqadmin"), System.getProperty("rhqPassword", "rhqadmin"));

        acceptJson = new Header("Accept", APPLICATION_JSON);
    }

    public static MonitoringRhq getRhq() {
        return mrhq;
    }

    public boolean rhqMonitoring(Object target, Field field) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;

        if (hostAvailabilityCheck()) {
            if (System.getProperty(field.getName()) != null) {
                int numericScheduleId = Integer.parseInt(System.getProperty(field.getName()));//13701;
                long now = System.currentTimeMillis();

                DoubleValue dataPoint = new DoubleValue(Double.parseDouble(field.get(target).toString()));
                given()
                        .header(acceptJson)
                        .contentType(ContentType.JSON)
                        .pathParam("id", numericScheduleId)
                        .pathParam("timestamp", now)
                        .body(dataPoint)
                        .expect()
                        .statusCode(201)
                        .log().ifError()
                        .when()
                        .put("/metric/data/{id}/raw/{timestamp}");
            }
        }

        return dataSent;
    }

    public boolean hostAvailabilityCheck() {
        try {
            Socket s = new Socket(REST_SERVER_ADDRESS, REST_SERVER_PORT);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
