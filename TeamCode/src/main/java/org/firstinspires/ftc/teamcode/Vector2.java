package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

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

    }


    private double x;

    public double getX() {
        return x;

    }

    public void setX(double x) {
        this.x = x;

    }


    public Vector2(double x, double y) {


        this.x = x;
        this.y = y;

    }

    public void scalarMultiplication(double scalar) {

        x *= scalar;
        y *= scalar;
    }

    public void rotateVector(double phi) {
        phi = Math.toRadians(phi);
        double cos= Math.cos(phi);
        double sin= Math.sin(phi);
        double newx = (x * cos) - (y * sin);
        double newy =( x * sin) + (y * cos);
        x=newx;
        y=newy;
    }

    public double dot(Vector2 v) {
        return v.x * x + v.y * y;
    }

    public String toString() {
        return "x:" + getX() + "y:" + getY();
    }

}



