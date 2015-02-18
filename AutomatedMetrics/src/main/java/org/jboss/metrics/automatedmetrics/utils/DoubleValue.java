/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.metrics.automatedmetrics.utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author panos
 */
@XmlRootElement(name =  "value")
public class DoubleValue {

    Double value;

    public DoubleValue() {
    }

    public DoubleValue(Double value) {
        this.value = value;
    }

    @XmlAttribute
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}