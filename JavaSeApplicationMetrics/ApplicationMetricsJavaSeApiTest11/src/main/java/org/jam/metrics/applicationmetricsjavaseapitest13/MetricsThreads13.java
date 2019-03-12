/*
 * Copyleft 2015 
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
package org.jam.metrics.applicationmetricsjavaseapitest13;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsThreads13 extends Thread {

    private Thread t;
    private String threadName;
    MetricsApiSeTestClass13 metricsApiSeTestClass;

   public  MetricsThreads13(String name, MetricsApiSeTestClass13 metricsApiSeTestClass) {
        threadName = name;
        this.metricsApiSeTestClass = metricsApiSeTestClass;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        try {
            System.out.println("Thread: " + threadName);
            
            metricsApiSeTestClass.countMethod();
            
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
