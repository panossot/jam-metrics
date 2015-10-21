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
package org.jboss.metrics.automatedmetrics.utils;

import java.util.Map;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class MDataPoint {

    long timeStamp;
    Double value;
    private int scheduleId;

    public MDataPoint() {
    }

    public MDataPoint(Map<String,Object> in) {
        timeStamp = (Long) in.get("timeStamp");
        value = (Double) in.get("value");
        scheduleId = (Integer)in.get("scheduleId");
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MDataPoint that = (MDataPoint) o;

        if (scheduleId != that.scheduleId) return false;
        if (timeStamp != that.timeStamp) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + scheduleId;
        return result;
    }
}