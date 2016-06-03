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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.logging.Logger;
import org.jboss.metrics.automatedmetricsapi.CodeParamsApi;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class JBossOpenAnalyticsInstance {

    private Logger logger = Logger.getLogger(JBossOpenAnalyticsInstance.class);

    public JBossOpenAnalyticsInstance() {
    }

    public void dbStoreAnalytics(boolean idRecord, boolean locationRecord, boolean numAccessRecord, boolean timeAccessRecord, boolean date, int time, String methodName, 
        String className, Object instance, String user, String recordDbName, String recordTableName, String statementName, String group) throws IllegalArgumentException, IllegalAccessException, SQLException {
        MetricProperties mProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
        Statement stmt = mProperties.getDatabaseStatement().get(statementName);
        String query = "INSERT INTO " + recordDbName + "." + recordTableName + "(IP_RECORD,LOCATION_RECORD,NUMACCESS_RECORD,TIMEACCESS_RECORD,METHOD_NAME,CLASS_NAME,INSTANCE,USER_NAME,RECORD_TIME) VALUES('[1]', '[2]', '[3]', '[4]', '[5]', '[6]', '[7]', '[8]', '[9]');";

        try {
            String serverId = "";
            if (idRecord) {
                serverId = System.getProperty("jboss.id");
                query = query.replace("[1]", serverId);
            } else
                query = query.replace("[1]", "");

            if (idRecord && locationRecord && serverId.compareTo("")!=0) {
	        String location = getLocation(serverId);
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

    private String getLocation(String serverId) {

        String serverLocation = "To be done ...";

        return serverLocation;

    }
    
    private int increaseNumAccess(String methodName) {
        CodeParamsApi.addUserName("JBossOpenAnalytics");
        AtomicInteger numAccess = CodeParamsApi.getCodeParams("JBossOpenAnalytics").getAtomicIntegerCodeParam(methodName);
        if (numAccess == null) {
            numAccess = new AtomicInteger(1);
            CodeParamsApi.getCodeParams("JBossOpenAnalytics").putAtomicIntegerCodeParam(methodName, numAccess);
        } else {
            numAccess.incrementAndGet();
        }
        
        return numAccess.intValue();
    }

}
