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

package org.jam.metrics.applicationmetrics;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import org.jboss.logging.Logger;
import org.jam.metrics.applicationmetricsapi.DBStore;
import org.jam.metrics.applicationmetricslibrary.DbQueries;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class DBStoreInstance {

    private Logger logger = Logger.getLogger(DBStoreInstance.class);
    private static Object dbLock = new Object();

    public DBStoreInstance() {
    }

    public void dbStore(DBStore dbStoreAnnotation, Object target, Map<String, Object> metricValues, String group, CodeParams cp, String user) throws IllegalArgumentException, IllegalAccessException, SQLException {
        String statementName = dbStoreAnnotation.statementName();
        String query = ParseDbQuery.parse(dbStoreAnnotation.queryUpdateDB(),metricValues,target,group,cp);
        MetricProperties mProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
        DbQueries dbQueries = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentInternalParameters(group).getDbQueries(user,statementName);
        
        synchronized(dbLock){
            dbQueries.addDbStorageQuery(statementName, query);
            int queryNum = dbQueries.getDbStorageCount(statementName);
            int updateQueryNum = mProperties.getUpdateRateOfDbQuery(dbStoreAnnotation.queryUpdateDB()[0]);
        
            if (queryNum>=updateQueryNum) {
                Statement stmt = mProperties.getDatabaseStatement().get(statementName);
                ArrayList<String> queries = dbQueries.getDbStorageQueries(statementName);
                for (String someQuery : queries) {
                    stmt.addBatch(someQuery);
                }

                stmt.executeBatch();
                dbQueries.resetDBqueries(statementName);
            }
        }
    }

}
