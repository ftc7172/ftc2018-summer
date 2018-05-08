package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 5/8/2018.
 */

public class Vector2 {

    private double y;
    public double getY() {
        return y;

    }

    public void setY(double y) {
        this.y = y;
        toPolar();
    }


    private double x;
    public double getX() {
        return x;

    }

    public void setX(double x) {
        this.x = x;
        toPolar();
    }

    private double r;

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
        toCartesian();
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {

        this.theta = theta;
        toCartesian();
    }

    private double theta;
    public Vector2(double xR, double yTheta, boolean cartesian){
        if(cartesian)// define cartesian vector
        {
            x=xR;
            y=yTheta;
            toPolar();
        }
        else{
            r=xR;
            theta= yTheta%(2*Math.PI);
            toCartesian();
        }
    }
    private void toPolar(){
        r=Math.hypot(x,y);
        theta= Math.atan2(x,y);
    }
    private void toCartesian(){
        x=r*Math.cos(theta);
        y=r*Math.sin(theta);
    }
    public void scalarMultiplication(double scalar){
        r*=scalar;
        x*=scalar;
        y*=scalar;
    }
    public void rotateVector(double phi){
        theta+=phi;
        theta%=Math.PI*2;
        toCartesian();
    }
    public double dot(Vector2 v){
        return v.x*x+v.y*y;
    }

}



