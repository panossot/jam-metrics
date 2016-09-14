/*
 * Copyright 2015  by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jam.metrics.applicationmetricslibrary;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author panos
 */
public class DbQueries {

    private HashMap<String, Integer> dbStorageCount;
    private HashMap<String, ArrayList<String>> dbStorageQueries;

    public DbQueries(String statement) {
        this.dbStorageCount = new HashMap<>();
        this.dbStorageQueries = new HashMap<>();
        dbStorageCount.put(statement,0);
        dbStorageQueries.put(statement, new ArrayList<String>());
    }

    public synchronized HashMap<String, Integer> getDbStorageCount() {
        return dbStorageCount;
    }

    public synchronized int getDbStorageCount(String query) {
        return dbStorageCount.get(query);
    }

    public synchronized void setDbStorageCount(HashMap<String, Integer> dbStorageCount) {
        this.dbStorageCount = dbStorageCount;
    }

    public synchronized void setDbStorageCount(String query, int count) {
        this.dbStorageCount.put(query, count);
    }

    public synchronized ArrayList<String> getDbStorageQueries(String statement) {
        return dbStorageQueries.get(statement);
    }

    public synchronized void setDbStorageQueries(String statement, ArrayList<String> dbStorageQueries) {
        this.dbStorageQueries.put(statement, dbStorageQueries);
        this.dbStorageCount.put(statement, this.dbStorageQueries.size());
    }
    
    public synchronized void addDbStorageQuery(String statement, String query) {
        this.dbStorageQueries.get(statement).add(query);
        this.dbStorageCount.put(statement,this.dbStorageCount.get(statement)+1);
    }
    
    public synchronized void resetDBqueries(String statement) {
        dbStorageCount.put(statement,0);
        dbStorageQueries.put(statement, new ArrayList<String>());
    }

}

