package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 9/4/2017.
 */

public class PID {
    double kP;
    double kI;
    double kD;

    private double lastError;

    private double acc;
    private double lastT;

    public PID(double K, double I, double D) {
        kP = K;
        kI = I;
        kD = D;
        reset();
    }

    public PID(double K, double D) {
        kP = K;
        kI = 0;
        kD = D;
        reset();
    }

    public PID(double K) {
        kP = K;
        kI = 0;
        kD = 0;
        reset();
    }

    public void reset() {
        acc = 0;
        lastT = 0;
        lastError = 0;
    }
    public void reset(double t) {
        acc = 0;
        lastT = t;
        lastError = 0;
    }


    public double calc(double pos, double setPoint, double t) {

        double error = setPoint - pos;
        double p = kP * error;
        if (lastT == 0)
            lastT = t;
        double dT = 1;//t - lastT;
        acc += trapazoidInt(error, dT);
        double i = kI * acc;
        double d = (error - lastError) / dT;
        d *= kD;

        lastError = error;
        lastT = t;
        return p + i + d;


    }

    private double trapazoidInt(double error, double dT) {
        return dT * (lastError + error) / 2;
    }

}
