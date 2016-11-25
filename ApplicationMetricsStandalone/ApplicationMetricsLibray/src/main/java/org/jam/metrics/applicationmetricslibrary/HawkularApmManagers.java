/*
 * Copyright 2016 panos.
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

import io.opentracing.Span;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author panos
 */
public class HawkularApmManagers {
    private ArrayList<String> methodNames;
    private HashMap<String,ArrayList<Span>> parentSpans;

    public HawkularApmManagers() {
        methodNames = new ArrayList<>();
        parentSpans = new HashMap<>();
    }

    public ArrayList<String> getMethodNames() {
        return methodNames;
    }
    
     public int getMethodIndex(String methodName) {
        return methodNames.indexOf(methodName);
    }

    public void setMethodNames(ArrayList<String> methodNames) {
        this.methodNames = methodNames;
    }
    
    public void addMethodName(String methodName) {
        this.methodNames.add(methodName);
    }
    
    public void addMethodName(String methodName, int index) {
        this.methodNames.add(index,methodName);
    }
    
    public void removeMethodName(String methodName) {
        this.methodNames.remove(methodName);
    }

    public ArrayList<Span> getParentSpans(String methodName) {
        return parentSpans.get(methodName);
    }

    public void setParentSpans(String methodName, ArrayList<Span> parentSpan) {
        this.parentSpans.put(methodName,parentSpan);
    }
    
    public void removeParentSpans(String methodName) {
        parentSpans.remove(methodName);
    }
    
    public void addParentSpan(String methodName, int pos, Span spanContext) {
        if (parentSpans.get(methodName)!=null) {
            parentSpans.get(methodName).add(pos,spanContext);
        } else {
            parentSpans.put(methodName, new ArrayList<Span>());
            parentSpans.get(methodName).add(pos,spanContext);
        }
    }
    
    public void removeParentSpan(String methodName, int pos) {
        parentSpans.get(methodName).remove(pos);
    }
    
    
}
