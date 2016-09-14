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
public @interface MetricFilter {
    @Nonbinding
    String filterName() default "defaultFilter";
    @Nonbinding
    String fieldName() default "";
    @Nonbinding
    String groupName() default "testGroup";
    @Nonbinding
    boolean smallerThan() default false;
    @Nonbinding
    boolean equalsWith() default false;
    @Nonbinding
    boolean largerThan() default false;
    @Nonbinding
    double comparableValue() default 0.0;
    @Nonbinding
    String filterParamName() default "";
    @Nonbinding
    String user() default "default";
}
