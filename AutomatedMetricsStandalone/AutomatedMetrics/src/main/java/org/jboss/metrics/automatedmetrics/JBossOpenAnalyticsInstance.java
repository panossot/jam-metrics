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

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.logging.Logger;
import org.jboss.metrics.automatedmetrics.utils.DbTableUtils;
import org.jboss.metrics.automatedmetrics.utils.ServerLocation;
import org.jboss.metrics.jbossautomatedmetricslibrary.DeploymentMetricProperties;
import org.jboss.metrics.jbossautomatedmetricslibrary2.CodeParamsCollection;
import org.jboss.metrics.jbossautomatedmetricsproperties.MetricProperties;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class JBossOpenAnalyticsInstance {

    private Logger logger = Logger.getLogger(JBossOpenAnalyticsInstance.class);

    public JBossOpenAnalyticsInstance() {
    }

    public void dbStoreAnalytics(boolean ipRecord, boolean locationRecord, boolean numAccessRecord, boolean timeAccessRecord, int time, String methodName, String className, Object instance, String user, String recordDbName,
            String recordTableName, String statementName, String group) throws IllegalArgumentException, IllegalAccessException, SQLException {
        MetricProperties mProperties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty(group);
        Statement stmt = mProperties.getDatabaseStatement().get(statementName);
        String query = "INSERT INTO " + recordDbName + "." + recordTableName + "(IP_RECORD,LOCATION_RECORD,NUMACCESS_RECORD,TIMEACCESS_RECORD,METHOD_NAME,CLASS_NAME,INSTANCE,USER_NAME) VALUES('[1]', '[2]', '[3]', '[4]', '[5]', '[6]', '[7]', '[8]');";

        try {
            if (!DbTableUtils.existsDbTable(recordDbName, recordTableName, stmt)) {
                try {
                    String sql = "CREATE DATABASE " + recordDbName + ";";
                    stmt.executeUpdate(sql);
                    logger.info("Database created successfully...");
                } catch (Exception e) {
                    // Database already exists.
                }

                String sql = "CREATE TABLE " + recordDbName + "." + recordTableName + "(ID int NOT NULL AUTO_INCREMENT, IP_RECORD varchar(255),"
                        + " LOCATION_RECORD varchar(255), NUMACCESS_RECORD varchar(255), TIMEACCESS_RECORD varchar(255), METHOD_NAME varchar(255),"
                        + " , CLASS_NAME varchar(255), INSTANCE varchar(255), USER_NAME varchar(255), PRIMARY KEY(ID));";

                stmt.executeUpdate(sql);
                logger.info("Table " + recordDbName + "." + recordTableName + " created successfully...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ArrayList<String> ips = new ArrayList();
            if (ipRecord) {
                String serverIps = "";
                Enumeration e = NetworkInterface.getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    NetworkInterface n = (NetworkInterface) e.nextElement();
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        serverIps = serverIps + "," + i.getHostAddress();
                        ips.add(i.getHostAddress());
                    }
                }
                query = query.replaceAll("[1]", serverIps);
            } else
                query = query.replaceAll("[1]", "");

            if (ipRecord && locationRecord) {
	        ServerLocation location = getLocation(ips.get(ips.size()-1));
                query = query.replaceAll("[2]", location.toString());
            } else
                query = query.replaceAll("[2]", "");
            
            if (numAccessRecord) {
                if (instance==null)
                    instance = "";
                
                AtomicInteger countAccess = null;
                
                if (user!=null)
                    countAccess = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(user).getAtomicIntegerCodeParam("numAccessRecord_" + methodName + "_" + instance);
                
                if (countAccess != null)
                    query = query.replaceAll("[3]", countAccess.toString());
            } else
                query = query.replaceAll("[3]", "");
            
            if (timeAccessRecord) {  
                query = query.replaceAll("[4]", String.valueOf(time));
            } else
                query = query.replaceAll("[4]", "");
            
            if (methodName!=null) {  
                query = query.replaceAll("[5]", methodName);
            } else
                query = query.replaceAll("[5]", "");
            
            if (className!=null) {  
                query = query.replaceAll("[6]", String.valueOf(className));
            } else
                query = query.replaceAll("[6]", "");
            
            if (instance!=null) {  
                query = query.replaceAll("[7]", String.valueOf(instance));
            } else
                query = query.replaceAll("[7]", "");
            
            if (user!=null) {  
                query = query.replaceAll("[8]", String.valueOf(user));
            } else
                query = query.replaceAll("[8]", "");

            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServerLocation getLocation(String ipAddress) {

        File file = new File(Paths.get(".").toAbsolutePath().normalize().toString()
                + "\\src\\main\\resources\\META-INF\\GeoLiteCity.dat");
        return getLocation(ipAddress, file);

    }

    private ServerLocation getLocation(String ipAddress, File file) {

        ServerLocation serverLocation = null;

        try {

            serverLocation = new ServerLocation();

            LookupService lookup = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
            Location locationServices = lookup.getLocation(ipAddress);

            serverLocation.setCountryCode(locationServices.countryCode);
            serverLocation.setCountryName(locationServices.countryName);
            serverLocation.setRegion(locationServices.region);
            serverLocation.setRegionName(regionName.regionNameByCode(
                    locationServices.countryCode, locationServices.region));
            serverLocation.setCity(locationServices.city);
            serverLocation.setPostalCode(locationServices.postalCode);
            serverLocation.setLatitude(String.valueOf(locationServices.latitude));
            serverLocation.setLongitude(String.valueOf(locationServices.longitude));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return serverLocation;

    }

}
