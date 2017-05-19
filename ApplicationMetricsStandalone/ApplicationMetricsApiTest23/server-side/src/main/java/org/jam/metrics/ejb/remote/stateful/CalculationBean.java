package org.jam.metrics.ejb.remote.stateful;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful
@Remote(RemoteCalculator.class)
public class CalculationBean implements RemoteCalculator {

    private double count[][];
    
    private double count2[][];

    @Override
    public double[][] countMethod() throws IllegalArgumentException, IllegalAccessException {
        count = new double[10][3];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<3; j++) {
                count[i][j]=i*j+Math.random()*5;
            }
        }
        
        return count;
    }
    
    @Override
    public double[][] countMethod2() throws IllegalArgumentException, IllegalAccessException {
        count2 = new double[10][3];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<3; j++) {
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
            
        }
        
        return count2;
    }
}
