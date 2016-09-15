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
package org.jam.metrics.applicationmetricslibrary2;

import java.util.HashMap;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
public class CodeParamsCollection {
    private static CodeParamsCollection codeParamsCollection = new CodeParamsCollection();
    private HashMap<String, CodeParams> codeParamsCollectionInstances;

    private CodeParamsCollection() {
        codeParamsCollectionInstances = new HashMap<String, CodeParams>();
    }
    
    public static synchronized CodeParamsCollection getCodeParamsCollection() {
        return codeParamsCollection;
    }
    
    public synchronized CodeParams getCodeParamsInstance(String name) {
        return (this.codeParamsCollectionInstances.get(name));
    }
    
    public synchronized void addCodeParamsInstance(String name) {
        if (!this.codeParamsCollectionInstances.containsKey(name))
            this.codeParamsCollectionInstances.put(name, new CodeParams());
    }
    
    public synchronized void removeCodeParamsInstance(String name) {
        this.codeParamsCollectionInstances.remove(name);
    }
    
    public synchronized boolean existsCodeParamsInstance(String name) {
        return(this.codeParamsCollectionInstances.containsKey(name));
    }
}
