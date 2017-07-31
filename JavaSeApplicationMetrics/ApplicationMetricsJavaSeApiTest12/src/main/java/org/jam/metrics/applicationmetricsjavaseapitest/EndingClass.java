/*
 * Copyright 2016 panos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jam.metrics.applicationmetricsjavaseapitest;

import org.jam.metrics.applicationmetricsapi.ApplicationJavaSeHawkularApm;
import org.jam.metrics.applicationmetricsapi.HawkularApm;

/**
 *
 * @author panos
 */
public class EndingClass {

    LastClass lastClass;

    public EndingClass() {
        this.lastClass = new LastClass();
    }
    
    public void endClass() throws Exception {
        System.out.println("End is close ...");
        ApplicationJavaSeHawkularApm.hawkularApm("endClass", "myTestGroup", new String[]{"lastClass","lastClass","lastClass"}, false);
        for(int i=0; i<3; i++)
            lastClass.lastClass();
    }
}
