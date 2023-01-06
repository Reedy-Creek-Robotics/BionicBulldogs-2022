package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.SpinCrServo;
import org.firstinspires.ftc.teamcode.modules.SpinMotor;
import org.firstinspires.ftc.teamcode.modules.SpinServo;

@TeleOp(name = "OpModeGrabber")
public class OpModeGrabber extends LinearOpMode {
    public void runOpMode(){
        double slidePower = 0.7;

        Movement movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, false);
        movement.telOpRunMode();

        SpinCrServo left = new SpinCrServo("leftClaw", this);
        SpinCrServo right = new SpinCrServo("rightClaw", this);

        SpinMotor slideL = new SpinMotor("slideL", true, this);
        SpinMotor slideR = new SpinMotor("slideR", true, this);

        SpinMotor turret = new SpinMotor("turret", true, this);

        ElapsedTime t = new ElapsedTime();
        boolean fieldRelative = true;
        waitForStart();
        while(opModeIsActive()){
            movement.mecanumTeleOpUpdate(0.8, fieldRelative);
            if(gamepad1.back && t.seconds() >= 2.5){
                fieldRelative = !fieldRelative;
            }
            if(gamepad1.dpad_up){
                slideL.startSpin(slidePower);
                slideR.startSpin(-slidePower);
            }
            else if(gamepad1.dpad_down){
                slideL.startSpin(-slidePower);
                slideR.startSpin(slidePower);
            }
            else{
                slideL.stopSpin();
                slideR.stopSpin();
            }
            if (gamepad1.left_bumper){
                if(slideL.getPosition()>400) {
                    turret.startSpin(0.5);
                }else{
                    slideL.startSpin(slidePower);
                    slideR.startSpin(-slidePower);
                }
            }else if(gamepad1.right_bumper){
                if(slideL.getPosition()>400) {
                    turret.startSpin(-0.5);
                }else{
                    slideL.startSpin(slidePower);
                    slideR.startSpin(-slidePower);
                }
            }else{
                turret.stopSpin();
            }
            if(gamepad1.a && t.seconds() >= 2.5){
                left.startSpin(1);
                right.startSpin(-1);
            }
            if(gamepad1.b && t.seconds() >= 2.5){
                left.stopSpin();
                right.stopSpin();
            }
        }
    }
}
