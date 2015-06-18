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
package org.jboss.metrics;

import org.jboss.metrics.automatedmetricsapi.MetricsCacheApi;
import org.jboss.metrics.jbossautomatedmetricslibrary.MetricsCacheCollection;

/**
 *
 * @author panos
 */
public class MetricsThreads extends Thread {

    private Thread t;
    private String threadName;
    MetricsApiSessionBean metricsBean;
    
    private String deploymentName = "myTestDeployment";
    private String deploymentName2 = "myTestDeployment2";

   public  MetricsThreads(MetricsApiSessionBean metricsBean, String name) {
        threadName = name;
        this.metricsBean = metricsBean;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        try {
            System.out.println("Thread: " + threadName);
            
            metricsBean.countMethod();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
