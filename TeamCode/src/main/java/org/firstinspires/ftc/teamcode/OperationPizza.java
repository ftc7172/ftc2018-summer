/*
Copyright 2015 FIRST Tech Challenge Team 7172

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.reflect.Array;
import java.util.Arrays;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.*;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@Autonomous

public class OperationPizza extends LinearOpMode {

    PlotterBot bot;
    ElapsedTime timer;
    
    @Override
    public void runOpMode() {
        bot = new PlotterBot();
        bot.init(hardwareMap);
        timer = new ElapsedTime();
        boolean gold = false;
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        // run until the end of the match (driver presses STOP)
        waitForStart();
        timer.reset();
        /*
        while(opModeIsActive()) {
            telemetry.addData("Yellow: ", bot.yellow());
            telemetry.update();
        }
        */
        while (opModeIsActive()) {
            if(timer.seconds() > 3) break;
            bot.rdist(29.5);
        }
        bot.rmove(0, 0, 0);
        double heading = bot.heading();
        timer.reset();
        while(opModeIsActive()) {
            telemetry.addData("rgb: ", Arrays.toString(bot.rgb()));
            telemetry.addData("hsv: ", Arrays.toString(bot.hsv()));
            telemetry.update();
            if(timer.seconds() > 2.3) break;
            if(bot.yellow()) {
                telemetry.addData("rgb: ", Arrays.toString(bot.rgb()));
                telemetry.update();
                gold = true;   
                break;
            }
            bot.rmovehead(-.2,0,heading);
        }
        bot.rmove(0, 0, 0);
        timer.reset();
        while (!gold && opModeIsActive()) {
            telemetry.addData("rgb: ", Arrays.toString(bot.rgb()));
            telemetry.addData("hsv: ", Arrays.toString(bot.hsv()));
            telemetry.update();
            if(timer.seconds() > 4) break;
            if(bot.yellow()){
                gold = true;  
                telemetry.addData("rbg: ", Arrays.toString(bot.rgb()));
                telemetry.update();
                break;
            } 
            bot.rmovehead(.2,0,heading);
        }
        timer.reset();
        bot.rmove(0, 0, 0);
        heading = bot.heading();
        while(gold && opModeIsActive()) {
            if(timer.seconds() > 2) break;
            if(timer.seconds() > 1) bot.rmovehead(0,0.25,heading);
            else bot.rmovehead(0,-.25, heading);
        }
        bot.rmove(0, 0, 0);
        /*while(opModeIsActive()) {
            telemetry.addData("rgb: ", Arrays.toString(bot.rgb()));
            telemetry.addData("gold: ", bot.yellow());
            telemetry.update();
        }*/
        bot.rmove(0, 0, 0);
    }
}
