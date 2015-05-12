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
package org.jboss.metrics.automatedmetricsjavase;

import org.jboss.logging.Logger;
import org.jboss.metrics.automatedmetricsjavase.utils.DoubleValue;
import org.jboss.metrics.automatedmetricsjavase.utils.RhqScheduleIds;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author panos
 */
public class MonitoringRhq {

    private static final MonitoringRhq mrhq = new MonitoringRhq();

    private final String APPLICATION_JSON;
    private final String BINARY;
    private final int REST_SERVER_PORT;
    private final String REST_SERVER_ADDRESS;
    private final String REST_SERVER_USERNAME;
    private final String REST_SERVER_PASSWORD;
    private final PostDataRhq postRhq;
    private Logger logger = Logger.getLogger(MonitoringRhq.class);
     
    private MonitoringRhq() {
        APPLICATION_JSON = "application/json";
        BINARY = "application/octet-stream";
        REST_SERVER_PORT = Integer.parseInt(System.getProperty("rest.port", "7080"));
        REST_SERVER_USERNAME = System.getProperty("rhqUsername", "rhqadmin");
        REST_SERVER_PASSWORD = System.getProperty("rhqPassword", "rhqadmin");
        REST_SERVER_ADDRESS = System.getProperty("rest.server", "localhost");

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://" + REST_SERVER_ADDRESS + ":" + REST_SERVER_PORT);
        target.register(new BasicAuthentication(REST_SERVER_USERNAME, REST_SERVER_PASSWORD));
        postRhq = target.proxy(PostDataRhq.class);

    }

    public static MonitoringRhq getRhq() {
        return mrhq;
    }

    public boolean rhqMonitoring(Object instance, Object value, String metricName) throws IllegalArgumentException, IllegalAccessException {
        boolean dataSent = false;
        String isMetricIdLoaded = System.getProperty(metricName);
        
        if (isMetricIdLoaded == null) {
            RhqScheduleIds.loadScheduleIds();
            isMetricIdLoaded = System.getProperty(metricName);
        }
        if (isMetricIdLoaded != null) {
            int numericScheduleId = Integer.parseInt(System.getProperty(metricName));
            long now = System.currentTimeMillis();

            DoubleValue dataPoint = new DoubleValue(Double.parseDouble(value.toString()));
            try {
                postRhq.postDataRhq(dataPoint, numericScheduleId, now, APPLICATION_JSON);
            } catch (Exception e) {
                try {
                    postRhq.getScheduleId(numericScheduleId, APPLICATION_JSON);
                    throw e;
                } catch(Exception e1) {
                    // Schedule id does not exist
                    logger.info("Rhq Schedule Id is not existent on the server, thus it is removed from System Properties.");
                    System.clearProperty(metricName);
                }
            }
        }

        return dataSent;
    }

}
