package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 6/4/2018.
 */

public class NullAccumulator implements PIDAccumulator {
    @Override
    public double accumulate(double x) {
        return 0;
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public void add(double x) {

    }

    @Override
    public void reset() {

    }
}
