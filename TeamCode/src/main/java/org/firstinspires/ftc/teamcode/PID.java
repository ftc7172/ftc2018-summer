package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

/**
 * Created by Owner on 9/4/2017.
 */

public class PID {
    final short BUFFER_SIZE=5;
    double kP;
    double kI;
    double kD;

    private double lastPosition;

    private double acc;

    private ArrayList<Double> buffer;
    public PID(PIDConstants x){
        kP=x.kP;
        kI=x.kI;
        kD=x.kD;
        reset();
    }


    public PID(double p, double I, double D) {
        kP =p;
        kI = I;
        kD = D;
        reset();
    }

    public PID(double p, double D) {
        kP = p;
        kI = 0;
        kD = D;
        reset();
    }

    public PID(double p) {
        kP = p;
        kI = 0;
        kD = 0;
        reset();
    }

    public void reset() {
        acc = 0;
        lastPosition = 0;
        for(short i = 0; i<BUFFER_SIZE; i++) buffer.add(0.0);
    }


    public double calcNoI(double pos, double setPoint){
        double error = setPoint - pos;
        double p = kP * error;



        double d = (pos - lastPosition);
        d *= kD;

        lastPosition = pos;

        return p + d;

    }
    public double calc(double pos, double setPoint) {

        double error = setPoint - pos;
        double p = kP * error;


        acc=accumulatorBuffer(error);
        double i = kI * acc;
        double d = (pos - lastPosition);
        d *= kD;

        lastPosition = pos;

        return p + i + d;

    }
    private double accumulatorBuffer(double x){
        buffer.remove(0);
        buffer.add(x);
        double sum=0;
        for(double i: buffer){
            sum+=i;
        }
        return sum;
    }




}
