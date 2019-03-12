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

import java.util.HashMap;
/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class DBStoreCollection {

    private static final DBStoreCollection dbsc = new DBStoreCollection();
    private HashMap<String, DBStoreInstance> dbStoreInstances;

    private DBStoreCollection() {
        dbStoreInstances = new HashMap<String, DBStoreInstance>();
    }
    
    public static DBStoreCollection getDBStoreCollection() {
        return dbsc;
    }
    
    public synchronized HashMap<String, DBStoreInstance> getDbStoreInstances() {
        return dbStoreInstances;
    }

    public synchronized void setDbStoreInstances(HashMap<String, DBStoreInstance> dbStoreInstances) {
        this.dbStoreInstances = dbStoreInstances;
    }
    
    public synchronized DBStoreInstance getDbStoreInstance(String name) {
        return (this.dbStoreInstances.get(name));
    }
    
    public synchronized void addDbStoreInstance(String name, DBStoreInstance dBStoreInstance) {
        this.dbStoreInstances.put(name, dBStoreInstance);
    }
    
    public synchronized void removeDbStoreInstance(String name) {
        this.dbStoreInstances.remove(name);
    }
    
    public synchronized boolean existsDbStoreInstance(String name) {
        return(this.dbStoreInstances.containsKey(name));
    }

}
