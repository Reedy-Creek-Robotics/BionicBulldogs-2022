package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Slide;
@TeleOp(name = "MainTelop")
public class Teleop extends LinearOpMode {
    enum SlideMode{
        auto, manual;
    }
    enum IntakeState{
        intake, stop, outtake;
    }
    public void runOpMode(){
        Movement movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, false);
        Slide slide = new Slide("slideL", "slideR", "leftWheel", "rightWheel", "open", "turret", "touchSensor", this);
        movement.telOpRunMode();
        SlideMode slideMode = SlideMode.auto;
        boolean closed = true;
        ElapsedTime t = new ElapsedTime();
        t.reset();
        IntakeState intakeState = IntakeState.stop;
        waitForStart();
        while (opModeIsActive()) {
            movement.mecanumTeleOpUpdate(slide.getSlidePosiion() > 4000 ? 0.4 : (slide.getSlidePosiion() > 3000 ? 0.6 : 0.8),false);
            if (slideMode == SlideMode.auto) {
                if (gamepad1.dpad_down) {
                    slide.goToPos(Slide.height.ground);
                }
                if (gamepad1.dpad_left) {
                    slide.goToPos(Slide.height.low);
                }
                if (gamepad1.dpad_up) {
                    slide.goToPos(Slide.height.med);
                }
                if (gamepad1.dpad_right) {
                    slide.goToPos(Slide.height.high);
                }
            } else {
                if (gamepad1.dpad_up) {
                    slide.setSlidePower(slide.slidePower);
                } else if (gamepad1.dpad_down) {
                    slide.setSlidePower(-slide.slidePower);
                } else {
                    slide.setSlidePower(0);
                }
            }
            if (gamepad1.x && t.seconds() >= 0.5) {
                if (closed) {
                    slide.open();
                    closed = false;
                } else {
                    slide.close();
                    closed = true;
                }
                t.reset();
            }
            if (gamepad1.left_bumper) {
                //if (slide.getSlidePosiion() > 400) {
                    slide.spinTurret(-0.4);
                //}
            } else if (gamepad1.right_bumper) {
                //if (slide.getSlidePosiion() > 400) {
                    slide.spinTurret(0.4);
                //}
            } else {
                slide.spinTurret(0);
            }
            if(gamepad1.b){
                switch (intakeState){
                    case stop:
                    case outtake:
                        slide.startIntake();
                        break;
                    case intake:
                        slide.stopIntake();
                        break;
                }
            }
            if(gamepad1.a){
                switch (intakeState){
                    case stop:
                    case intake:
                        slide.startOutake();
                    case outtake:
                        slide.stopIntake();
                }
            }
            if(gamepad1.back && t.seconds() >= 0.5){
                if(slideMode == SlideMode.auto) {
                    slideMode = SlideMode.manual;
                }else{
                    slideMode = SlideMode.auto;
                }
                t.reset();
            }
            if (slide.touchSensorPressed()) {
                slide.stopIntake();
            }
            if(slide.atPosition(Slide.height.ground) && !slide.isBusy()){
                slide.resetSlideEncoders();
            }
            telemetry.addData("slideMode", slideMode);
            telemetry.addData("slideHeight", slide.getSlidePosiion());
            telemetry.update();
        }
    }
}
