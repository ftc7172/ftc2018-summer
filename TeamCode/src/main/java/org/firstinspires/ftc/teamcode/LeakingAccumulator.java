package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 6/4/2018.
 */

public class LeakingAccumulator implements PIDAccumulator {
    private double leak;
    private double acc;
    public LeakingAccumulator() {
        leak=2.0/3;
        acc=0;
    }

    public LeakingAccumulator(double leak) {
        this.leak = leak;
        acc=0;
    }

    @Override
    public double accumulate(double x) {
        add(x);
        return getValue();
    }

    @Override
    public double getValue() {
        return acc;
    }

    @Override
    public void add(double x) {
        acc= acc*leak+x;
    }

    @Override
    public void reset() {
    acc=0;
    }
}
