package org.jam.metrics.ejb.remote.stateful;

public interface RemoteCalculator {

    public double[][] countMethod() throws IllegalArgumentException, IllegalAccessException;

    public double[][] countMethod2() throws IllegalArgumentException, IllegalAccessException;
}
