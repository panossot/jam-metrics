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
import java.util.concurrent.CountDownLatch;


/**
 *
 * @author panos
 */
public class HawkularApmManagers {
    private ArrayList<ArrayList<ChildParentMethod>> methodQueuesDone;
    private ArrayList<ArrayList<ChildParentMethod>> methodQueuesToDo;
    private HashMap<String,Span> spanStore;
    private Span rootSpan;
    private CountDownLatch latch;
    private String threadName;

    public HawkularApmManagers() {
        methodQueuesDone = new ArrayList<>();
        methodQueuesToDo = new ArrayList<>();
        spanStore = new HashMap();
        latch = new CountDownLatch(1);
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public ArrayList<ArrayList<ChildParentMethod>> getMethodQueuesDone() {
        return methodQueuesDone;
    }

    public void setMethodQueuesDone(ArrayList<ArrayList<ChildParentMethod>> methodQueuesDone) {
        this.methodQueuesDone = methodQueuesDone;
    }
    
    public void addMethodQueuesDoneElement(int methodQueueIndex, int index, ChildParentMethod childParentMethog) {
        this.methodQueuesDone.get(methodQueueIndex).add(index, childParentMethog);
    }
    
    public void removeMethodQueuesDoneElement(int methodQueueIndex, int index) {
        this.methodQueuesDone.get(methodQueueIndex).remove(index);
    }

    public ArrayList<ArrayList<ChildParentMethod>> getMethodQueuesToDo() {
        return methodQueuesToDo;
    }

    public void setMethodQueuesToDo(ArrayList<ArrayList<ChildParentMethod>> methodQueuesToDo) {
        this.methodQueuesToDo = methodQueuesToDo;
    }
    
    public void addMethodQueuesToDoElement(int methodQueueIndex, int index, ChildParentMethod childParentMethog) {
        this.methodQueuesToDo.get(methodQueueIndex).add(index, childParentMethog);
    }
    
    public void removeMethodQueuesToDoElement(int methodQueueIndex, int index) {
        this.methodQueuesToDo.get(methodQueueIndex).remove(index);
    }
    
    public HashMap<String,Span> getSpanStore() {
        return spanStore;
    }

    public void setSpanStore(HashMap<String,Span> spanStore) {
        this.spanStore = spanStore;
    }

    public Span getRootSpan() {
        return rootSpan;
    }

    public void setRootSpan(Span rootSpan) {
        this.rootSpan = rootSpan;
    }

    public synchronized void addInSpanStore(String spanName, Span span) {
        spanStore.put(spanName,span);
    }
    
    public synchronized Span getFromSpanStore(String spanName) {
        return spanStore.get(spanName);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
    
}
