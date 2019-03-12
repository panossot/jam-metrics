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
package org.jam.metrics.applicationmetricsapi;

import java.sql.SQLException;
import org.jam.metrics.applicationmetricsjavase.DBStoreCollection;
import org.jam.metrics.applicationmetricsjavase.DBStoreInstance;
import org.jam.metrics.applicationmetricslibrary2.CodeParams;
import org.jam.metrics.applicationmetricslibrary2.CodeParamsCollection;

/**
 *
 * @author panos
 */
class DBStoreSyncAdapter {

    private final static Object dbLock = new Object();

    protected static void dBStoreSyncAdapter(String dataBaseStorage, Object instance, Object[] values, String group, String statementName, String[] queryUpdateDB, String metricUser) throws Exception {

        if (dataBaseStorage != null && Boolean.parseBoolean(dataBaseStorage)) {
            CodeParams cp = null;

            if (metricUser == null) {
                metricUser = "default";
            }

            String mUser = metricUser;

            if (CodeParamsCollection.getCodeParamsCollection().existsCodeParamsInstance(mUser)) {
                cp = CodeParamsCollection.getCodeParamsCollection().getCodeParamsInstance(mUser);
            }

            DBStoreInstance dBStoreInstance;
            synchronized (dbLock) {
                dBStoreInstance = DBStoreCollection.getDBStoreCollection().getDbStoreInstance(group);
                if (dBStoreInstance == null) {
                    dBStoreInstance = new DBStoreInstance();
                    DBStoreCollection.getDBStoreCollection().addDbStoreInstance(group, dBStoreInstance);
                }
            }

            try {
                dBStoreInstance.dbStore(queryUpdateDB, instance, values, statementName, group, cp, mUser);
            } catch (IllegalArgumentException | IllegalAccessException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
