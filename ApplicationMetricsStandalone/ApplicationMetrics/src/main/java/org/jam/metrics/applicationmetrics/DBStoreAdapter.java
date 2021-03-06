/*
 * Copyleft 2016 
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.jam.metrics.applicationmetricsapi.DBStore;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary2.CodeParamsCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author panos
 */
class DBStoreAdapter {

    private final static Object dbLock = new Object();
    
    protected static void dBStoreAdapter(DBStore dbStoreAnnotation, Object target, String fieldName, Method method, Map<String, Field> metricFields) throws IllegalArgumentException, IllegalAccessException, Exception {
        if (dbStoreAnnotation != null) {
            final String group = dbStoreAnnotation.groupName();
            boolean sync = dbStoreAnnotation.sync();
            MetricProperties properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
            String dataBaseStorage = properties.getDatabaseStore();
            if (dataBaseStorage != null && Boolean.parseBoolean(dataBaseStorage) && !sync) {
                final HashMap<String, Object> metricValues = new HashMap();
                int queryUpdateDBSize = dbStoreAnnotation.queryUpdateDB().length;

                for (int i = 1; i < queryUpdateDBSize; i++) {

                    Field field = accessField(metricFields, dbStoreAnnotation, method, i);
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
                        if (CodeParamsCollection.getCodeParamsCollection().existsCodeParamsInstance(metricUser.get(target).toString())) {
                            cp = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(metricUser.get(target).toString());
                        }
                    }
                } catch (Exception e) {
                    // Probably the metric user is not defined. Go on with the execution of the database storage.
                }

                final String user = mUser;
                final CodeParams cParams;

                if (cp != null) {
                    cParams = cp.deepClone();
                } else {
                    cParams = null;
                }

                new Thread() {
                    public void run() {
                        DBStoreInstance dBStoreInstance;
                        synchronized (dbLock) {
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
    }
    
    private static synchronized Field accessField(Map<String, Field> metricFields, DBStore dbStoreAnnotation, Method method, int fieldNum) throws Exception {
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
