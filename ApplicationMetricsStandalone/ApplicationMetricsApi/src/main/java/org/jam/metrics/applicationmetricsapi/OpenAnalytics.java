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

package org.jam.metrics.applicationmetricsapi;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@Inherited
@InterceptorBinding
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface OpenAnalytics {
    @Nonbinding
    boolean serverIdRecord() default true;
    @Nonbinding
    boolean serverLocationRecord() default true;
    @Nonbinding
    boolean numAccessRecord() default true;
    @Nonbinding
    boolean timeAccessRecord() default true;
    @Nonbinding
    boolean dateRecord() default true;
    @Nonbinding
    boolean userRecord() default false;
    @Nonbinding
    String className() default "className";
    @Nonbinding
    String methodName() default "methodName";
    @Nonbinding
    String userName() default "default";
    @Nonbinding
    String recordDbName() default "OpenAnalytics";
    @Nonbinding
    String recordTableName() default "Analytics";
    @Nonbinding
    String locationDbName() default "OpenAnalytics";
    @Nonbinding
    String locationTableName() default "LocationData";
    @Nonbinding
    String dbStatement() default "_analytics_statement";
    @Nonbinding
    String locationDbStatement() default "_analytics_location_data_statement";
    @Nonbinding
    String groupName() default "testGroup";
}
