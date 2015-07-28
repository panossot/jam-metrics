package org.jboss.metrics;

import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.metrics.automatedmetricsapi.Metric;

/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 *
 * @author panos
 */
public class MetricsClass {
    private static AtomicInteger countAtomic;
    private static int count = 0;
    
    private static int count2 = 0;

    public MetricsClass(){
        countAtomic = new AtomicInteger(1);
    }
    
    @Metric(fieldName = {"count"}, deploymentName = "myTestDeployment")
    public synchronized void getAndSetCountIncreased() {
        count = this.countAtomic.getAndIncrement();
    }

    @Metric(fieldName = {"count2"}, deploymentName = "myTestDeployment")
    public synchronized void getAndSetCount2Increased() {
        count2 = this.count2+2;
    }
    
}
