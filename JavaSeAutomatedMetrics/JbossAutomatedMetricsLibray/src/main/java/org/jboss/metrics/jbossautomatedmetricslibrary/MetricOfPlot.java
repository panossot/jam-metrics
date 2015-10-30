/*
 * Copyright 2015 JBoss by Red Hat.
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
package org.jboss.metrics.jbossautomatedmetricslibrary;

import java.util.Objects;

/**
 *
 * @author panos
 */
public class MetricOfPlot {
    private String metricName;
    private String plotName;

    public MetricOfPlot(String metricName, String plotName) {
        this.metricName = metricName;
        this.plotName = plotName;
    }

    
    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MetricOfPlot other = (MetricOfPlot) obj;
        if (!Objects.equals(this.metricName, other.metricName)) {
            return false;
        }
        if (!Objects.equals(this.plotName, other.plotName)) {
            return false;
        }
        return true;
    }
    
    
}
