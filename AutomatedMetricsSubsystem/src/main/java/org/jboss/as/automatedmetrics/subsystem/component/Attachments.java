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
public class Attachments {

    public static final AttachmentKey<MetricInterceptor> METRICS_METRICINTERCEPTOR = AttachmentKey.create(MetricInterceptor.class);
    public static final AttachmentKey<MetricObject> METRICS_METRICOBJECT = AttachmentKey.create(MetricObject.class);
    public static final AttachmentKey<MetricsCacheSingleton> METRICS_METRICSCACHESINGLETON = AttachmentKey.create(MetricsCacheSingleton.class);
    public static final AttachmentKey<MonitoringRhq> METRICS_MONITORINGRHQ = AttachmentKey.create(MonitoringRhq.class);
    public static final AttachmentKey<DoubleValue> METRICS_DOUBLEVALUE = AttachmentKey.create(DoubleValue.class);
    public static final AttachmentKey<RhqScheduleIds> METRICS_RHQSHEDULEIDS = AttachmentKey.create(RhqScheduleIds.class);
    public static final AttachmentKey<Store> METRICS_STORE = AttachmentKey.create(Store.class);

}
