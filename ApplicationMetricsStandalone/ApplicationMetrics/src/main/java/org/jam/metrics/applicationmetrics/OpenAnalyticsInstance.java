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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.jam.metrics.applicationmetricsapi.CodeParamsApi;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class OpenAnalyticsInstance {

    private Logger logger = Logger.getLogger(OpenAnalyticsInstance.class);

    public OpenAnalyticsInstance() {
    }

    public void dbStoreAnalytics(boolean idRecord, boolean locationRecord, boolean numAccessRecord, boolean timeAccessRecord, boolean date, int time, String methodName, 
        String className, Object instance, String user, String recordDbName, String recordTableName, String locationDbName, String locationTableName, String statementName, String locationStatementName, String group) throws IllegalArgumentException, IllegalAccessException, SQLException {
        MetricProperties mProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
        Statement stmt = mProperties.getDatabaseStatement().get(statementName);
        Statement locationStmt = mProperties.getDatabaseStatement().get(locationStatementName);
        String query = "INSERT INTO " + recordDbName + "." + recordTableName + "(IP_RECORD,LOCATION_RECORD,NUMACCESS_RECORD,TIMEACCESS_RECORD,METHOD_NAME,CLASS_NAME,INSTANCE,USER_NAME,RECORD_TIME) VALUES('[1]', '[2]', '[3]', '[4]', '[5]', '[6]', '[7]', '[8]', '[9]');";
        String query2 = "SELECT * FROM " + locationDbName + "." + locationTableName;

        try {
            String serverId = "";
            if (idRecord) {
                serverId = System.getProperty("server.id");
                if (serverId == null)
                    serverId = "";
                query = query.replace("[1]", serverId);
            } else
                query = query.replace("[1]", "");

            if (idRecord && locationRecord && serverId.compareTo("")!=0) {
	        String location = getLocation(locationStmt,query2,serverId);
                query = query.replace("[2]", (location!=null?location:"Not found"));
            } else
                query = query.replace("[2]", "");
            
            if (numAccessRecord) {
                int countAccess = increaseNumAccess("numAccessRecord_" + methodName + "_" + instance);
                query = query.replace("[3]", String.valueOf(countAccess));
            } else
                query = query.replace("[3]", null);
            
            if (timeAccessRecord) {  
                query = query.replace("[4]", String.valueOf(time));
            } else
                query = query.replace("[4]", "");
            
            if (methodName!=null) {  
                query = query.replace("[5]", methodName);
            } else
                query = query.replace("[5]", "");
            
            if (className!=null) {  
                query = query.replace("[6]", String.valueOf(className));
            } else
                query = query.replace("[6]", "");
            
            if (instance!=null) {  
                query = query.replace("[7]", String.valueOf(instance));
            } else
                query = query.replace("[7]", "");
            
            if (user!=null) {  
                query = query.replace("[8]", String.valueOf(user));
            } else
                query = query.replace("[8]", "");
            
            if (date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                GregorianCalendar calendar = new GregorianCalendar();
                String dateRecord = format.format(calendar.getTime());
                query = query.replace("[9]", dateRecord);
            } else {
                query = query.replace("[9]", "");
            }

            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLocation(Statement locationStmt, String query, String serverId) {

        String serverLocation = null;
        
        try {
            ResultSet rs = locationStmt.executeQuery(query + " WHERE SERVER_INSTANCE_NAME='" + serverId + "';");
            rs.next();
            if (rs != null)
                serverLocation = rs.getString(2);
            else
                serverLocation = "Not defined";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return serverLocation;

    }
    
    private int increaseNumAccess(String methodName) {
        CodeParamsApi.addUserName("OpenAnalytics");
        AtomicInteger numAccess = CodeParamsApi.getCodeParams("OpenAnalytics").getAtomicIntegerCodeParam(methodName);
        if (numAccess == null) {
            numAccess = new AtomicInteger(1);
            CodeParamsApi.getCodeParams("OpenAnalytics").putAtomicIntegerCodeParam(methodName, numAccess);
        } else {
            numAccess.incrementAndGet();
        }
        
        return numAccess.intValue();
    }

}
