package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

/**
 * Created by Owner on 9/4/2017.
 */

public class PID {
    //final short BUFFER_SIZE=5;
    double kP;
    double kI;
    double kD;

    private double lastPosition;
    private PIDAccumulator acc;

    public PID(PIDConstants x) {
        kP = x.kP;
        kI = x.kI;
        kD = x.kD;
        this.acc = x.acc;
        reset();
    }


    public PID(double p, double I, double D, PIDAccumulator acc) {
        kP = p;
        kI = I;
        kD = D;
        this.acc = acc;
        reset();
    }

    public PID(double p, double D) {
        kP = p;
        kI = 0;
        kD = D;
        acc = new NullAccumulator();
        reset();
    }

    public PID(double p) {
        kP = p;
        kI = 0;
        kD = 0;
        acc = new NullAccumulator();
        reset();
    }

    public void reset() {
        acc.reset();
        lastPosition = 0;

    }


    public double calc(double pos, double setPoint) {

        double error = setPoint - pos;
        double p = kP * error;


        acc.add(error);
        double i = kI * acc.getValue();
        double d = (pos - lastPosition);
        d *= kD;

        lastPosition = pos;

        return p + i + d;

    }


}
