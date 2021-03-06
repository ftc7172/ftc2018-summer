/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="GyroMecanum", group="teleop")
//@Disabled
public class MecanumGyroOp extends OpMode
{
    DcMotor rf;
    DcMotor rr;
    DcMotor lf;
    DcMotor lr;
    DcMotor intakel;
    DcMotor intaker;
    BNO055IMU imu;
    PID pid;
    Gyro gyro;

    double target;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        imu=hardwareMap.get(BNO055IMU.class, "imu");
        gyro= new Gyro(imu);



        rf= hardwareMap.get(DcMotor.class,"rf");
        rr= hardwareMap.get(DcMotor.class,"rr");
        lf= hardwareMap.get(DcMotor.class,"lf");
        lr= hardwareMap.get(DcMotor.class,"lr");
        rf.setDirection(DcMotorSimple.Direction.FORWARD);
        rr.setDirection(DcMotorSimple.Direction.FORWARD);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);
        pid= new PID(-0.015);

        intakel = hardwareMap.get(DcMotor.class, "intakel");
        intaker = hardwareMap.get(DcMotor.class, "intaker");
        intakel.setDirection(DcMotorSimple.Direction.FORWARD);
        intaker.setDirection(DcMotorSimple.Direction.REVERSE);
        }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    gyro.reset();

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double heading = gyro.heading();
        double forward = gamepad1.right_stick_y;
        double turn ;//= gamepad1.left_stick_x;
        preset();
        target += gamepad1.left_stick_x*-8;
        turn= pid.calc(heading, target);

        telemetry.addData("gyro", heading);

        double strafe= gamepad1.right_stick_x;
        Vector2 vec = new Vector2(strafe, forward);
        vec.rotateVector(heading);
        drive(vec.getY()+gamepad1.left_stick_y,vec.getX(),turn);

        double intakePower = (gamepad1.right_trigger - gamepad1.left_trigger) * 0.5;
        intakel.setPower(intakePower);
        intaker.setPower(intakePower);
    }

    public void drive(double forward, double strafe, double turn){
        double rfP = forward+turn+strafe;
        double rrP = forward+turn-strafe;
        double lfP = forward-turn-strafe;
        double lrP = forward-turn+strafe;
        rf.setPower(rfP);
        rr.setPower(rrP);
        lf.setPower(lfP);
        lr.setPower(lrP);
    }

    public void preset(){
        if (gamepad1.dpad_up) {
            target=360*gyro.turns;
        }
        else if (gamepad1.dpad_right) target=-90+360*gyro.turns;
        else if (gamepad1.dpad_left) target=90+360*gyro.turns;
        else if (gamepad1.dpad_down) target=180+360*gyro.turns;
        else if (gamepad1.a) { gyro.reset(); target=0; }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
