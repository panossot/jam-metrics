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
package org.jboss.metrics.automatedmetrics;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ParseDbQuery {
    public static String parse(String[] queryParams, Map<String, Field> metricFields, Object target, String group){
        String query = "";
        
        try {
            int queryParamNum = queryParams.length;

            if (queryParamNum>0) {
                query =  DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group).getUpdateDbQueries().get(queryParams[0]);
                queryParamNum --;
                int i = 1;
                String replacable = "";
                while(queryParamNum>0) {
                    replacable = "{"+i+"}";
                    query = query.replace(replacable, queryParams[i]);
                    replacable = "[" + i + "]";
                    query = query.replace(replacable, metricFields.get(queryParams[i]).get(target).toString());
                    i++;
                    queryParamNum --;
                }
                query = query.replace("{instance}", target.toString());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                GregorianCalendar calendar = new GregorianCalendar();
                String time = format.format(calendar.getTime());
                query = query.replace("{time}", time);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        return query;
    }
}
