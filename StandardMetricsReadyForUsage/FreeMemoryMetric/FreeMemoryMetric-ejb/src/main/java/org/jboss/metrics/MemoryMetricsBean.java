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

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import org.jam.metrics.applicationmetricsapi.Metric;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Stateful
@LocalBean
public class MemoryMetricsBean {

    private double usedMemory = 0;
    private double freeMemory = 0;
    private double totalMemory = 0;
    private double maxMemory = 0;

    public MemoryMetricsBean() {
    }

    @Metric(fieldName = {"usedMemory","freeMemory","totalMemory","maxMemory"}, groupName = "memoryMetrics")
    public void memoryMetrics() {
        try {
            int mb = 1024*1024;
         
            //Getting the runtime reference from system
            Runtime runtime = Runtime.getRuntime();

            System.out.println("##### Heap utilization statistics [MB] #####");

            //Print used memory
            usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / mb;
            System.out.println("Used Memory:"+ usedMemory);

            //Print free memory
            freeMemory = runtime.freeMemory() / mb;
            System.out.println("Free Memory:" + freeMemory);

            //Print total available memory
            totalMemory = runtime.totalMemory() / mb;
            System.out.println("Total Memory:" + totalMemory);

            //Print Maximum available memory
            maxMemory = runtime.maxMemory() / mb;
            System.out.println("Max Memory:" + maxMemory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
