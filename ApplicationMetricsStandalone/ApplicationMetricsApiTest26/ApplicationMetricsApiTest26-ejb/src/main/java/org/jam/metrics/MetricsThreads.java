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
package org.jam.metrics;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsThreads extends Thread {

    private Thread t;
    private String threadName;
    MetricsApiSessionBean metricsBean;

    public MetricsThreads(MetricsApiSessionBean metricsBean, String name) {
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
    
    public Thread getT(){
        return (this.t);
    }
}
