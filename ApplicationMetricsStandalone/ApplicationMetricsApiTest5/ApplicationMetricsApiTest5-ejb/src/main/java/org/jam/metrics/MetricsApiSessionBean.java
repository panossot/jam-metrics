/*
 * Copyleft 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org.icenses/LICENSE-2.0
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
public class MetricsApiSessionBean {
    
    MetricsClass metricsClass;

    public MetricsApiSessionBean(MetricsClass metricsClass) {
        this.metricsClass = metricsClass;
    }
    

    public void countMethod() {
        for (int i=0; i<100; i++) {
            metricsClass.getAndSetCountIncreased();
            metricsClass.getAndSetCount2Increased();
        }
    }
    

}
