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

import org.jam.metrics.applicationmetricsapi.Metric;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsClass {
    private static volatile int count = 0;
    
    private static volatile int count2 = 0;
    
    private static volatile int count3 = 0;

    public synchronized int getCount() {
        return count;
    }

    @Metric(fieldName = {"count"}, groupName = "myTestGroup")
    public synchronized void setCount(int count) {
        this.count = count;
    }

    public synchronized int getCount2() {
        return count2;
    }

    @Metric(fieldName = {"count2"}, groupName = "myTestGroup")
    public synchronized void setCount2(int count2) {
        this.count2 = count2;
    }
    
    public synchronized int getCount3() {
        return count3;
    }
    
    @Metric(fieldName = {"count3"}, groupName = "myTestGroup2")
    public synchronized void setCount3(int count3) {
        this.count3 = count3;
    }
    
}
