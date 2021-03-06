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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */

package org.jam.metrics.applicationmetricsjavase;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class ParseDbQuery {
    public static String parse(String[] queryParams, Object[] metricValues, Object target, String group, CodeParams cp){
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
                    query = query.replace(replacable, metricValues[i-1].toString());
                    i++;
                    queryParamNum --;
                }
                
                while (query.contains("~")) {
                    int index1 = query.indexOf("~");
                    int index2 = query.indexOf("~", index1+1);

                    String stringParam = query.substring(index1, index2+1);
                    if (cp!=null)
                        query = query.replaceAll(stringParam, cp.getStringCodeParam(stringParam.replaceAll("~", "")));
                    else
                        query = query.replaceAll(stringParam, "");
                }
                
                while (query.contains("#")) {
                    int index1 = query.indexOf("#");
                    int index2 = query.indexOf("#", index1+1);
                    String stringParam = query.substring(index1, index2+1);
                    if (cp!=null)
                        query = query.replaceAll(stringParam, String.valueOf(cp.getIntegerCodeParam(stringParam.replaceAll("#", ""))));
                    else
                        query = query.replaceAll(stringParam, "NULL");
                    
                }
                
                while (query.contains("**")) {
                    int index1 = query.indexOf("**");
                    int index2 = query.indexOf("**", index1+1);

                    String stringParam = query.substring(index1, index2+1);
                    if (cp!=null)
                        query = query.replaceAll(stringParam, String.valueOf(cp.getDoubleCodeParam(stringParam.replaceAll("**", ""))));
                    else
                        query = query.replaceAll(stringParam, "NULL");
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
