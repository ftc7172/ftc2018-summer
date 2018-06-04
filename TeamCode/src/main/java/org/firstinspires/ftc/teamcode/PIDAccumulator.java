package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 6/4/2018.
 */

public interface PIDAccumulator {
    public double accumulate(double x);
    public double getValue();
    public void add(double x);
    public void reset();
}
