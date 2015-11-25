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

import com.rits.cloning.Cloner;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.metrics.automatedmetricsapi.DBStore;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParams;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParamsCollection;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@DBStore
@Interceptor
public class DBStoreInterceptor {

    private Map<String, Field> metricFields = new HashMap();
    private final static Object dbLock = new Object();

    @AroundInvoke
    public Object metricsInterceptor(InvocationContext ctx) throws Exception {
        Object result = ctx.proceed();
        Method method = ctx.getMethod();
        final Object target = ctx.getTarget();
            
        try {
            final DBStore dbStoreAnnotation = method.getAnnotation(DBStore.class);
            if (dbStoreAnnotation != null) {  
                final String group = dbStoreAnnotation.groupName();
                MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
                String dataBaseStorage = properties.getDatabaseStore();
                if (dataBaseStorage != null && Boolean.parseBoolean(dataBaseStorage)) {
                    final HashMap<String, Object> metricValues = new HashMap();
                    int queryUpdateDBSize = dbStoreAnnotation.queryUpdateDB().length;
                            
                    for (int i = 1; i < queryUpdateDBSize; i++) {

                        Field field = accessField(dbStoreAnnotation, method, i);
                        metricValues.put(dbStoreAnnotation.queryUpdateDB()[i], field.get(target));
                    }
                    
                    Field metricUser = null;
                    CodeParams cp = null;
                    String mUser = "default";
                    try {
                        metricUser = method.getDeclaringClass().getDeclaredField("metricUser");
                        if (metricUser != null) {
                            metricUser.setAccessible(true);
                            mUser = metricUser.get(target).toString();
                            if (CodeParamsCollection.getCodeParamsCollection().existsCodeParamsInstance(metricUser.get(target).toString()))
                                cp = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(metricUser.get(target).toString());
                        }
                    }catch(Exception e) {
                        // Probably the metric user is not defined. Go on with the execution of the database storage.
                    }
                    
                    final String user = mUser;
                    Cloner cloner = new Cloner();
                    final CodeParams cParams = cloner.deepClone(cp);
                    new Thread() {
                        public void run() {
                            DBStoreInstance dBStoreInstance;
                            synchronized(dbLock) {
                                dBStoreInstance = DBStoreCollection.getDBStoreCollection().getDbStoreInstance(group);
                                if (dBStoreInstance == null) {
                                    dBStoreInstance = new DBStoreInstance();
                                    DBStoreCollection.getDBStoreCollection().addDbStoreInstance(group, dBStoreInstance);
                                }
                            }

                            try {
                                dBStoreInstance.dbStore(dbStoreAnnotation, target, metricValues, group, cParams, user);
                            } catch (IllegalArgumentException | IllegalAccessException | SQLException ex) {
                                ex.printStackTrace();
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

    private synchronized Field accessField(DBStore dbStoreAnnotation, Method method, int fieldNum) throws Exception {
        Field field;
        if (metricFields.containsKey(dbStoreAnnotation.queryUpdateDB()[fieldNum])) {
            field = metricFields.get(dbStoreAnnotation.queryUpdateDB()[fieldNum]);
        } else {
            field = method.getDeclaringClass().getDeclaredField(dbStoreAnnotation.queryUpdateDB()[fieldNum]);
            field.setAccessible(true);
            metricFields.put(dbStoreAnnotation.queryUpdateDB()[fieldNum], field);
        }
        
        return field;
    }
    
}
