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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */

package org.jam.metrics.applicationmetrics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jam.metrics.applicationmetricsapi.OpenAnalytics;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@OpenAnalytics
@Interceptor
public class OpenAnalyticsInterceptor {

    private final static Object OpenAnalyticsLock = new Object();

    @AroundInvoke
    public Object OpenAnalyticsInterceptor(InvocationContext ctx) throws Exception {
        long millisStart = Calendar.getInstance().getTimeInMillis();
        Object result = ctx.proceed();
        long millisFinish = Calendar.getInstance().getTimeInMillis();
        Method method = ctx.getMethod();
        Object target = ctx.getTarget();
            
        try {
            final OpenAnalytics OpenAnalyticsAnnotation = method.getAnnotation(OpenAnalytics.class);
            if (OpenAnalyticsAnnotation != null) {
                final boolean idRecord = OpenAnalyticsAnnotation.serverIdRecord();
                final boolean locationRecord = OpenAnalyticsAnnotation.serverLocationRecord();
                final boolean numAccessRecord = OpenAnalyticsAnnotation.numAccessRecord();
                final boolean timeAccessRecord = OpenAnalyticsAnnotation.timeAccessRecord();
                final boolean date = OpenAnalyticsAnnotation.dateRecord();
                final boolean userRecord = OpenAnalyticsAnnotation.userRecord();
                final String methodName = (OpenAnalyticsAnnotation.methodName().compareTo("methodName")==0)?method.getName():OpenAnalyticsAnnotation.methodName();
                final String className = OpenAnalyticsAnnotation.className();
                final String recordTableName = OpenAnalyticsAnnotation.recordTableName();
                final String recordDbName = OpenAnalyticsAnnotation.recordDbName();
                final String locationTableName = OpenAnalyticsAnnotation.locationTableName();
                final String locationDbName = OpenAnalyticsAnnotation.locationDbName();
                final String statementName = OpenAnalyticsAnnotation.dbStatement();
                final String locationStatementName = OpenAnalyticsAnnotation.locationDbStatement();
                final String group = OpenAnalyticsAnnotation.groupName();
                String userName = null;
                final int time = (int)(millisFinish - millisStart);
                final Object instance = target;

                if (userRecord) {
                    Field field = method.getDeclaringClass().getDeclaredField(OpenAnalyticsAnnotation.userName());
                    field.setAccessible(true);
                    userName = String.valueOf(field.get(target));
                }
            
                final String user = userName;
                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                String OpenAnalytics = properties.getOpenAnalytics();    
                    
                if (OpenAnalytics != null && Boolean.parseBoolean(OpenAnalytics)) {
                    new Thread() {
                        public void run() {
                            try {
                                OpenAnalyticsInstance OpenAnalyticsInstance;
                                synchronized(OpenAnalyticsLock) {
                                    OpenAnalyticsInstance = OpenAnalyticsCollection.getOpenAnalyticsCollection().getOpenAnalyticsInstance(group);
                                    if (OpenAnalyticsInstance == null) {
                                        OpenAnalyticsInstance = new OpenAnalyticsInstance();
                                        OpenAnalyticsCollection.getOpenAnalyticsCollection().addOpenAnalyticsInstance(group, OpenAnalyticsInstance);
                                    }
                                }
                                
                                OpenAnalyticsInstance.dbStoreAnalytics(idRecord, locationRecord, numAccessRecord, timeAccessRecord, date, time, methodName, className, instance, user, recordDbName, recordTableName, locationDbName, locationTableName, statementName, locationStatementName, group);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                    
               
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

}
