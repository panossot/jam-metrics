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
package org.jboss.metrics;

import java.io.IOException;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import org.apache.commons.io.FileSystemUtils;
import org.jboss.metrics.automatedmetricsapi.Metric;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Stateful
@LocalBean
public class FreeDiskSpaceMetricBean {

    private double freeDiskSpaceGB = 0;

    public FreeDiskSpaceMetricBean() {
    }

    @Metric(fieldName = {"freeDiskSpaceGB"}, groupName = "freeDiskSpace")
    public double freeDiskSpace() {
        try {
            //calculate free disk space 
            double freeDiskSpace = FileSystemUtils.freeSpaceKb("/"); 

            //convert the number into gigabyte
            freeDiskSpaceGB = freeDiskSpace / 1024 / 1024;

            System.out.println("Free Disk Space of a Linux OS (GB):" + freeDiskSpaceGB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return freeDiskSpaceGB;
    }

}
