package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.SpinMotor;
@Disabled
@TeleOp
public class BeltSlideTest extends LinearOpMode {
    public void runOpMode(){
        SpinMotor motor = new SpinMotor("slideL", false, this);
        waitForStart();
        if(opModeIsActive()){
            while (opModeIsActive()){
                motor.startSpin(gamepad1.left_stick_y);
            }
        }
    }
}
