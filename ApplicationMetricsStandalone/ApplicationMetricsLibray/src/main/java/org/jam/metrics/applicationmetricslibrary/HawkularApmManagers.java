/*
 * Copyleft 2016  by Red Hat.
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

/*
 *  ΙΔΕΑ : Everything is a potential metric .
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
    private ArrayList<ChildParentMethod> methodQueue;
    private int methodQueueIndex;
    private HashMap<String,Span> spanStore;
    private Span rootSpan;

    public HawkularApmManagers() {
        methodQueue = new ArrayList<>();
        methodQueueIndex = 0;
        spanStore = new HashMap();
    }

    public HashMap<String, Span> getSpanStore() {
        return spanStore;
    }

    public void setSpanStore(HashMap<String, Span> spanStore) {
        this.spanStore = spanStore;
    }

    public Span getRootSpan() {
        return rootSpan;
    }

    public void setRootSpan(Span rootSpan) {
        this.rootSpan = rootSpan;
    }

    public synchronized void putInSpanStore(String methodName, Span span) {
        spanStore.put(methodName, span);
    }
    
    public synchronized Span getFromSpanStore(String methodName) {
        return spanStore.get(methodName);
    }
    
    public synchronized ArrayList<ChildParentMethod> getMethodQueue() {
        return methodQueue;
    }

    public synchronized void setMethodQueue(ArrayList<ChildParentMethod> methodQueue) {
        this.methodQueue = methodQueue;
    }

    public synchronized int getMethodQueueIndex() {
        return methodQueueIndex;
    }

    public synchronized void setMethodQueueIndex(int methodQueueIndex) {
        this.methodQueueIndex = methodQueueIndex;
    }
    
}
