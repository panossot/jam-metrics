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

/**
 *
 * @author panos
 */
public class ChildParentMethod {
    String childMethod;
    String parentMethod;
    String grandpaMethod;
    boolean isEnd;

    public ChildParentMethod(String childMethod, String parentMethod) {
        this.childMethod = childMethod;
        this.parentMethod = parentMethod;
        this.isEnd = false;
    }
    
    public ChildParentMethod(String childMethod, String parentMethod, String grandpaMethod) {
        this.childMethod = childMethod;
        this.parentMethod = parentMethod;
        this.grandpaMethod = grandpaMethod;
        this.isEnd = false;
    }

    public boolean isIsEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public String getChildMethod() {
        return childMethod;
    }

    public void setChildMethod(String childMethod) {
        this.childMethod = childMethod;
    }

    public String getParentMethod() {
        return parentMethod;
    }

    public void setParentMethod(String parentMethod) {
        this.parentMethod = parentMethod;
    }

    public String getGrandpaMethod() {
        return grandpaMethod;
    }

    public void setGrandpaMethod(String grandpaMethod) {
        this.grandpaMethod = grandpaMethod;
    }
    
    
}
