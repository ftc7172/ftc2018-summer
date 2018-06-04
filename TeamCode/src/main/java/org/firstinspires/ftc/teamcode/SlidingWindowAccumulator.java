package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

/**
 * Created by Owner on 6/4/2018.
 */

public class SlidingWindowAccumulator implements PIDAccumulator {
    private ArrayList<Double> buffer;
    private final short BUFFER_SIZE;

    public SlidingWindowAccumulator(short BUFFER_SIZE) {
        this.BUFFER_SIZE = BUFFER_SIZE;
        reset();
    }

    @Override
    public double accumulate(double x) {
        add(x);
        return getValue();
    }

    @Override
    public double getValue() {

        double sum=0;
        for(double i: buffer){
            sum+=i;
        }
        return sum;
    }

    @Override
    public void add(double x) {
        buffer.remove(0);
        buffer.add(x);
    }

    @Override
    public void reset() {
        buffer= new ArrayList<Double>();
        for(short i = 0; i<BUFFER_SIZE; i++) buffer.add(0.0);
    }
}
