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

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import org.jam.metrics.applicationmetricsapi.DBStore;
import org.jam.metrics.applicationmetricsapi.HawkularApm;
import org.jam.metrics.applicationmetricsapi.Metric;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsClass {
    private static AtomicInteger countAtomic;
    private static int count = 0;
    
    private static int count2 = 0;
    private static AtomicInteger i;
    private String metricUser="Niki";
    
    private CodeParams cp;
    
    @Inject
    EndingClass endClass;

    public static AtomicInteger getCountAtomic() {
        return countAtomic;
    }

    public static void setCountAtomic(AtomicInteger countAtomic) {
        MetricsClass.countAtomic = countAtomic;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        MetricsClass.count = count;
    }

    public static int getCount2() {
        return count2;
    }

    public static void setCount2(int count2) {
        MetricsClass.count2 = count2;
    }

    public static AtomicInteger getI() {
        return i;
    }

    public static void setI(AtomicInteger i) {
        MetricsClass.i = i;
    }

    public String getMetricUser() {
        return metricUser;
    }
    
    public MetricsClass(){
        countAtomic = new AtomicInteger(1);
        i = new AtomicInteger(1);
    }
    
    @Metric(fieldName = {"count"}, groupName = "myTestGroup")
    @DBStore(groupName = "myTestGroup", queryUpdateDB = {"StoreDBMetric","count"}, statementName = "statement_1")
    @HawkularApm(childMethods = {"endClass"}, groupName = "myTestGroup")
    public synchronized void getAndSetCountIncreased(Callable<Object> func) throws Exception {
        func.call();
        endClass.endClass();
    }

    @Metric(fieldName = {"count2"}, groupName = "myTestGroup")
    @DBStore(groupName = "myTestGroup", queryUpdateDB = {"StoreDBMetric","count2"}, statementName = "statement_1")
    @HawkularApm(childMethods = {"endClass"}, groupName = "myTestGroup")
    public synchronized void getAndSetCount2Increased(Callable<Object> func) throws Exception {
        func.call();
        endClass.endClass();
    }
    
}
