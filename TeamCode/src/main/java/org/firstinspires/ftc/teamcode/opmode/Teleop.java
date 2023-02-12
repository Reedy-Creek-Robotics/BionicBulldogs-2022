package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Slide;
import org.firstinspires.ftc.teamcode.modules.TouchSensorModule;
import org.firstinspires.ftc.teamcode.modules.Turret;

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
        Slide slide = new Slide("slideL", "slideR", this);
        Turret turret = new Turret("turret", this);
        Intake intake = new Intake("leftWheel", "rightWheel", "open", this);
        TouchSensorModule touchSensor = new TouchSensorModule("touchSensor", this);
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
                    intake.open();
                    closed = false;
                } else {
                    intake.close();
                    closed = true;
                }
                t.reset();
            }
            if (curGamepad.left_bumper) {
                //if (slide.getSlidePosiion() > 400) {
                turret.spinTurret(-0.7);
                //}
            } else if (curGamepad.right_bumper) {
                //if (slide.getSlidePosiion() > 400) {
                turret.spinTurret(0.7);
                //}
            } else if(!turret.isBusy()) {
                turret.spinTurret(0);
            }
            if(curGamepad.b && /*t.seconds() > btnDelay && */!prevGamepad.b){
                switch (intakeState){
                    case intake:
                        intake.stopIntake();
                        intakeState = IntakeState.stop;
                        break;
                    default:
                    case stop:
                        intake.startIntake();
                        intakeState = IntakeState.intake;
                        break;
                }
                t.reset();
            }
            if(curGamepad.a){
                slide.gotoOther(slide.getSlidePosition()-200);
                movement.delay(0.25);
                intake.open();
                movement.delay(0.25);
                slide.gotoOther(slide.getSlidePosition()+200);
                movement.delay(0.25);
                turret.spinTurretWait(0.6,0);
                turret.resetEncoder();
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
            if (touchSensor.getState() && !sensorState && !(slide.getSlidePosition() > 250)) {
                intake.stopIntake();
                movement.delayUpdateMovement(0.1);
                slide.gotoOther(200);
                intakeState = IntakeState.stop;
            }
            if(slide.atPosition(Slide.height.ground) && !slide.isBusy()){
                slide.resetSlideEncoders();
            }
            if(curGamepad.y){
                intake.stopIntake();
                movement.delayUpdateMovement(0.1);
                slide.gotoOther(200);
                intakeState = IntakeState.stop;
            }
            if(curGamepad.left_trigger > 0.5 && t.seconds() >= btnDelay){
                turret.spinTurret(turret.turretPower, -400);
            }
            if(curGamepad.right_trigger > 0.5 && t.seconds() >= btnDelay){
                turret.spinTurret(turret.turretPower, 400);
            }
            sensorState = touchSensor.getState();
            telemetry.addData("slideMode", slideMode);
            telemetry.addData("turretPosition", turret.getPosition());
            telemetry.addData("slideHeight", slide.getSlidePosition());
            telemetry.addData("slideHeightLeft", slide.getSlidePosition(0));
            telemetry.addData("slideHeightRight", slide.getSlidePosition(1));
            telemetry.update();
        }
    }
}
