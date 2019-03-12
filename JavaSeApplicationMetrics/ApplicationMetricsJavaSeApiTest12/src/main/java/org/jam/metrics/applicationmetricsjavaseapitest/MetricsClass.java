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
package org.jam.metrics.applicationmetricsjavaseapitest;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.jam.metrics.applicationmetricsapi.ApplicationJavaSeHawkularApm;


/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MetricsClass {
    private static AtomicInteger countAtomic;
    private static int count = 0;
    
    private static int count2 = 0;
    private static AtomicInteger i;
    
    MediumClass mediumClass;


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


    
    public MetricsClass(){
        countAtomic = new AtomicInteger(1);
        mediumClass = new MediumClass();
        i = new AtomicInteger(1);
    }
    
    public synchronized void getAndSetCountIncreased(Callable<Object> func) throws Exception {
        func.call();
        ApplicationJavaSeHawkularApm.hawkularApm("getAndSetCountIncreased", "myTestGroup", new String[]{"mediumClass","mediumClass"}, false);
        mediumClass.mediumClass();
        mediumClass.mediumClass();
    }

    public synchronized void getAndSetCount2Increased(Callable<Object> func) throws Exception {
        func.call();
        ApplicationJavaSeHawkularApm.hawkularApm("getAndSetCount2Increased", "myTestGroup", new String[]{"mediumClass","mediumClass"}, false);
        mediumClass.mediumClass();
        mediumClass.mediumClass();
    }
    
}
