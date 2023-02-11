package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Slide;
@TeleOp(name = "MainTelop")
public class Teleop extends LinearOpMode {
    double baseSpeed = 0.85;
    double medSpeed = 0.5;
    double highSpeed = 0.3;

    double btnDelay = 0;

    boolean sensorState = false;
    enum SlideMode{
        auto, manual
    }
    enum IntakeState{
        intake, stop
    }
    Movement movement;
    public void runOpMode(){
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);

        Gamepad curGamepad = new Gamepad();
        Gamepad prevGamepad = new Gamepad();

        movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, true);
        Slide slide = new Slide("slideL", "slideR", "leftWheel", "rightWheel", "open", "turret", "touchSensor", this);
        movement.telOpRunMode();
        SlideMode slideMode = SlideMode.auto;
        boolean closed = false;
        boolean holdPosition = false;
        ElapsedTime t = new ElapsedTime();
        t.reset();
        IntakeState intakeState = IntakeState.stop;
        waitForStart();
        while (opModeIsActive()) {
            try {
                prevGamepad.copy(curGamepad);
                curGamepad.copy(gamepad1);
            }catch(RobotCoreException e){
                telemetry.addLine("robot core excption :(");
            }
            double powerFactor = slide.getSlidePosition() > 2700 ? highSpeed : (
                    slide.getSlidePosition() > 1900 ? medSpeed :
                            baseSpeed);
            movement.mecanumTeleOpUpdate(powerFactor,false);
            if (slideMode == SlideMode.auto) {
                if (curGamepad.dpad_down) {
                    slide.goToPos(Slide.height.ground);
                }
                if (curGamepad.dpad_left) {
                    slide.goToPos(Slide.height.low);
                }
                if (curGamepad.dpad_up) {
                    slide.goToPos(Slide.height.med);
                }
                if (curGamepad.dpad_right) {
                    slide.goToPos(Slide.height.high);
                }
            } else {
                if (curGamepad.dpad_up) {
                    slide.setSlidePower(slide.slidePower);
                    holdPosition = true;
                } else if (curGamepad.dpad_down) {
                    slide.setSlidePower(-slide.slidePower);
                    holdPosition = true;
                } else {
                    if(holdPosition){
                        slide.gotoOther(slide.getSlidePosition());
                        holdPosition = false;
                    }
                }
                if(curGamepad.dpad_left){
                    slide.resetSlideEncoders();
                }
            }
            if (curGamepad.x && /*t.seconds() >= btnDelay && */!prevGamepad.x) {
                if (closed) {
                    slide.open();
                    closed = false;
                } else {
                    slide.close();
                    closed = true;
                }
                t.reset();
            }
            if (curGamepad.left_bumper) {
                //if (slide.getSlidePosiion() > 400) {
                    slide.spinTurret(-0.7);
                //}
            } else if (curGamepad.right_bumper) {
                //if (slide.getSlidePosiion() > 400) {
                    slide.spinTurret(0.7);
                //}
            } else if(!slide.isTurretBusy()) {
                slide.spinTurret(0);
            }
            if(curGamepad.b && /*t.seconds() > btnDelay && */!prevGamepad.b){
                switch (intakeState){
                    case intake:
                        slide.stopIntake();
                        intakeState = IntakeState.stop;
                        break;
                    default:
                    case stop:
                        slide.startIntake();
                        intakeState = IntakeState.intake;
                        break;
                }
                t.reset();
            }
            if(curGamepad.a){
                slide.gotoOther(slide.getSlidePosition()-200);
                movement.delay(0.25);
                slide.open();
                movement.delay(0.25);
                slide.gotoOther(slide.getSlidePosition()+200);
                movement.delay(0.25);
                slide.spinTurretWait(0.6,0);
                slide.resetTurretEncoder();
                slide.goToPos(Slide.height.ground);
                //slide.resetTurretEncoder();
            }
            if(curGamepad.back && t.seconds() >= btnDelay && !prevGamepad.back){
                if(slideMode == SlideMode.auto) {
                    slideMode = SlideMode.manual;
                }else{
                    slideMode = SlideMode.auto;
                }
                t.reset();
            }
            if (slide.touchSensorPressed() && !sensorState && !(slide.getSlidePosition() > 250)) {
                slide.stopIntake();
                movement.delayUpdateMovement(0.1);
                slide.gotoOther(200);
                intakeState = IntakeState.stop;
            }
            if(slide.atPosition(Slide.height.ground) && !slide.isBusy()){
                slide.resetSlideEncoders();
            }
            if(curGamepad.y){
                slide.stopIntake();
                movement.delayUpdateMovement(0.1);
                slide.gotoOther(200);
                intakeState = IntakeState.stop;
            }
            if(curGamepad.left_trigger > 0.5 && t.seconds() >= btnDelay){
                slide.spinTurret(slide.turretPower, -400);
            }
            if(curGamepad.right_trigger > 0.5 && t.seconds() >= btnDelay){
                slide.spinTurret(slide.turretPower, 400);
            }
            sensorState = slide.touchSensorPressed();
            telemetry.addData("slideMode", slideMode);
            telemetry.addData("turretPosition", slide.getTurretPosition());
            telemetry.addData("slideHeight", slide.getSlidePosition());
            telemetry.addData("slideHeightLeft", slide.getSlidePosition(0));
            telemetry.addData("slideHeightRight", slide.getSlidePosition(1));
            telemetry.update();
        }
    }
}
