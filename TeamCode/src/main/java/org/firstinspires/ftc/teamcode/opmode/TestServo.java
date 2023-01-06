package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.SpinServo;
@TeleOp(name = "testServo")
public class TestServo extends LinearOpMode {
    public void runOpMode(){
        SpinServo servo = new SpinServo("clawOpener",0,1,this);
        double startPos = 0;
        double endPos = 1;
        ElapsedTime t = new ElapsedTime();
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_left && t.seconds() >= 2.5) {
                startPos -= 0.05;
                t.reset();
            }
            if (gamepad1.dpad_right && t.seconds() >= 2.5) {
                startPos += 0.05;
                t.reset();
            }
            if (gamepad1.x && t.seconds() >= 2.5) {
                startPos -= 0.05;
                t.reset();
            }
            if (gamepad1.b && t.seconds() >= 2.5) {
                startPos += 0.05;
                t.reset();
            }
            if (gamepad1.right_bumper && t.seconds() >= 2.5){
                servo.goToStart();
                t.reset();
            }
            if (gamepad1.left_bumper && t.seconds() >= 2.5){
                servo.goToEnd();
                t.reset();
            }
            servo.setStartpos(startPos);
            servo.setEndpos(endPos);
            telemetry.addData("startPos", startPos);
            telemetry.addData("endPos", endPos);
            telemetry.update();
        }
    }
}
