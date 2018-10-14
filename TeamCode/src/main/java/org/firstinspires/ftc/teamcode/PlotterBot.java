package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;


public class PlotterBot {

    private BNO055IMU imu;
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor rb;
    private DcMotor lb;
    private DistanceSensor rDist;
    private DistanceSensor lDist;
    ColorSensor colorSensor = null;
    DistanceSensor ds = null;
    float hsv[] = {0F,0F,0F};
    double turns = 0;
    double lastZ = 0;
    
    public void init(HardwareMap hardwareMap) {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rDist = hardwareMap.get(DistanceSensor.class, "rDist");
        lDist = hardwareMap.get(DistanceSensor.class, "lDist");
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(params);
        
        colorSensor = hardwareMap.get(ColorSensor.class,"colorSensor");
        ds = hardwareMap.get(DistanceSensor.class,"colorSensor");

    }
    
    public void rmove(double rx, double ry, double rw) {
        lf.setPower(ry + rx + rw);
        rf.setPower(ry - rx - rw);
        lb.setPower(ry - rx + rw);
        rb.setPower(ry + rx - rw);
    }
    
    public void rdist(double dist) {
            double distanceRight = rDist.getDistance(DistanceUnit.INCH);
            double distanceLeft = lDist.getDistance(DistanceUnit.INCH);
            
            double leftPower = ((distanceLeft - dist) * 0.0225);
            double rightPower = ((distanceRight - dist) * 0.0225);
            
            lf.setPower(leftPower);
            rf.setPower(rightPower);
            lb.setPower(leftPower);
            rb.setPower(rightPower);
    }
    
    public float hue() {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsv);
        return hsv[0];
    }
    
    public float saturation() {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsv);
        return hsv[1];
    }
    
    public float value() {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsv);
        return hsv[2];
    }
    
    public float[] hsv() {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsv);
        return hsv;
    }
    
    public float[] rgb() {
        float[] rgb = {colorSensor.red(), colorSensor.green(), colorSensor.blue()};
        return rgb;
    }
    
    public boolean yellow() {
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();
        return red < 200 && green < 200 && blue < 0.75 * green && this.colorDist() < 5/* && this.hue() < 142 && this.value() > 0.45 && this.saturation() > 0.45*/;
    }
    
    public double heading() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double z = angles.firstAngle;
        if(z<-140 && lastZ > 140) turns++;
        if(z > 140 && lastZ < -140) turns--;
        lastZ = z;
        return turns * 360 + z;
    }
    
    public void rmovehead(double rx, double ry, double target){
        rmove(rx,ry, (heading()-target) * 0.02);
    }
    
    public double colorDist() {
        double distance = ds.getDistance(DistanceUnit.INCH);
        return distance;
    }
}
