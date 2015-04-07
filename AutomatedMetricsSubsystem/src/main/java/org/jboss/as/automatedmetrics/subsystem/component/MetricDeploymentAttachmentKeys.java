/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.as.automatedmetrics.subsystem.component;

import org.jboss.as.automatedmetrics.subsystem.component.utils.DoubleValue;
import org.jboss.as.automatedmetrics.subsystem.component.utils.RhqScheduleIds;
import org.jboss.as.server.deployment.AttachmentKey;

/**
 *
 * @author panos
 */
public class MetricDeploymentAttachmentKeys {

    public static final AttachmentKey<MetricInterceptor> METRICINTERCEPTOR_NAME = AttachmentKey.create(MetricInterceptor.class);
    public static final AttachmentKey<MetricObject> METRICOBJECT_NAME = AttachmentKey.create(MetricObject.class);
    public static final AttachmentKey<MetricsCacheSingleton> METRICSCHACHESINGLETON_NAME = AttachmentKey.create(MetricsCacheSingleton.class);
    public static final AttachmentKey<MonitoringRhq> MONITORING_NAME = AttachmentKey.create(MonitoringRhq.class);
    public static final AttachmentKey<DoubleValue> DOUBLEVALUE_NAME = AttachmentKey.create(DoubleValue.class);
    public static final AttachmentKey<RhqScheduleIds> RHQSCHEDULEIDS_NAME = AttachmentKey.create(RhqScheduleIds.class);
    public static final AttachmentKey<Store> STORE_NAME = AttachmentKey.create(Store.class);

}
