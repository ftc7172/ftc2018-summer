package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 5/18/2018.
 */

public class PIDConstants {
    public double kP;
    public double kI;
    public double kD;
    PIDAccumulator acc;

    public PIDConstants(){
        kP=0;
        kI=0;
        kD=0;
        acc=new NullAccumulator();

    }

    public PIDConstants(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        acc=new NullAccumulator();
    }

    public PIDConstants(double kP, double kD) {
        this.kP = kP;
        this.kD = kD;
        kI=0;
        acc=new NullAccumulator();
    }

    public PIDConstants(double kP) {
        this.kP = kP;
        kD=0;
        kI=0;
        acc=new NullAccumulator();
    }

    public PIDConstants(double kP, double kI, double kD, PIDAccumulator acc) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.acc = acc;
    }
}
