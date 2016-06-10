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
import java.lang.reflect.Method;
import java.util.Calendar;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.metrics.automatedmetricsapi.JBossOpenAnalytics;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@JBossOpenAnalytics
@Interceptor
public class JBossOpenAnalyticsInterceptor {

    private final static Object jbossOpenAnalyticsLock = new Object();

    @AroundInvoke
    public Object jbossOpenAnalyticsInterceptor(InvocationContext ctx) throws Exception {
        long millisStart = Calendar.getInstance().getTimeInMillis();
        Object result = ctx.proceed();
        long millisFinish = Calendar.getInstance().getTimeInMillis();
        Method method = ctx.getMethod();
        Object target = ctx.getTarget();
            
        try {
            final JBossOpenAnalytics jbossOpenAnalyticsAnnotation = method.getAnnotation(JBossOpenAnalytics.class);
            if (jbossOpenAnalyticsAnnotation != null) {
                final boolean idRecord = jbossOpenAnalyticsAnnotation.serverIdRecord();
                final boolean locationRecord = jbossOpenAnalyticsAnnotation.serverLocationRecord();
                final boolean numAccessRecord = jbossOpenAnalyticsAnnotation.numAccessRecord();
                final boolean timeAccessRecord = jbossOpenAnalyticsAnnotation.timeAccessRecord();
                final boolean date = jbossOpenAnalyticsAnnotation.dateRecord();
                final boolean userRecord = jbossOpenAnalyticsAnnotation.userRecord();
                final String methodName = (jbossOpenAnalyticsAnnotation.methodName().compareTo("methodName")==0)?method.getName():jbossOpenAnalyticsAnnotation.methodName();
                final String className = jbossOpenAnalyticsAnnotation.className();
                final String recordTableName = jbossOpenAnalyticsAnnotation.recordTableName();
                final String recordDbName = jbossOpenAnalyticsAnnotation.recordDbName();
                final String locationTableName = jbossOpenAnalyticsAnnotation.locationTableName();
                final String locationDbName = jbossOpenAnalyticsAnnotation.locationDbName();
                final String statementName = jbossOpenAnalyticsAnnotation.dbStatement();
                final String locationStatementName = jbossOpenAnalyticsAnnotation.locationDbStatement();
                final String group = jbossOpenAnalyticsAnnotation.groupName();
                String userName = null;
                final int time = (int)(millisFinish - millisStart);
                final Object instance = target;

                if (userRecord) {
                    Field field = method.getDeclaringClass().getDeclaredField(jbossOpenAnalyticsAnnotation.userName());
                    field.setAccessible(true);
                    userName = String.valueOf(field.get(target));
                }
            
                final String user = userName;
                final MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                String jbossOpenAnalytics = properties.getJBossOpenAnalytics();    
                    
                if (jbossOpenAnalytics != null && Boolean.parseBoolean(jbossOpenAnalytics)) {
                    new Thread() {
                        public void run() {
                            try {
                                JBossOpenAnalyticsInstance jbossOpenAnalyticsInstance;
                                synchronized(jbossOpenAnalyticsLock) {
                                    jbossOpenAnalyticsInstance = JBossOpenAnalyticsCollection.getJBossOpenAnalyticsCollection().getJBossOpenAnalyticsInstance(group);
                                    if (jbossOpenAnalyticsInstance == null) {
                                        jbossOpenAnalyticsInstance = new JBossOpenAnalyticsInstance();
                                        JBossOpenAnalyticsCollection.getJBossOpenAnalyticsCollection().addJBossOpenAnalyticsInstance(group, jbossOpenAnalyticsInstance);
                                    }
                                }
                                
                                jbossOpenAnalyticsInstance.dbStoreAnalytics(idRecord, locationRecord, numAccessRecord, timeAccessRecord, date, time, methodName, className, instance, user, recordDbName, recordTableName, locationDbName, locationTableName, statementName, locationStatementName, group);
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
