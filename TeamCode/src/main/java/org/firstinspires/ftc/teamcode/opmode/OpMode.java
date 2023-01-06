package org.firstinspires.ftc.teamcode.opmode;

import android.text.method.Touch;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamClient;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.SpinCrServo;
import org.firstinspires.ftc.teamcode.modules.SpinMotor;
import org.firstinspires.ftc.teamcode.modules.SpinServo;
import org.firstinspires.ftc.teamcode.modules.TouchSensorModule;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp(name = "OpModeOld")
public class OpMode extends LinearOpMode {
    public void runOpMode() {

        double slidePower = 0.7;

        Movement movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, false);
        movement.telOpRunMode();

        SpinCrServo left = new SpinCrServo("leftWheel", this);
        SpinCrServo right = new SpinCrServo("rightWheel", this);

        SpinMotor slideL = new SpinMotor("slideL", true, this);
        SpinMotor slideR = new SpinMotor("slideR", true, this);

        SpinMotor turret = new SpinMotor("turret", true, this);

        SpinCrServo open = new SpinCrServo("open", this);
        TouchSensorModule touchSensor = new TouchSensorModule("touchSensor", this);

        ElapsedTime t = new ElapsedTime();
        boolean tSpin = false;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
        FtcDashboard.getInstance().startCameraStream(webcam, 0);
        t.reset();
        waitForStart();
        while (opModeIsActive()) {
            movement.mecanumTeleOpUpdate((Math.abs(slideL.getPosition()) > 2000 ? 0.4 : 0.8),false);
            if(gamepad1.back){
                turret.spinToPosition(0.6, 0);
                tSpin = true;
            }
            if(tSpin && !turret.isBusy()){
                turret.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                tSpin = false;
            }
            if (gamepad1.x && t.seconds() >= 0.25) {
                left.startSpin(0.5);
                right.startSpin(-0.5);
            }
            if (gamepad1.b && t.seconds() >= 0.25) {
                left.startSpin(-0.5);
                right.startSpin(0.5);
            }
            if (gamepad1.a && t.seconds() >= 0.25) {
                left.stopSpin();
                right.stopSpin();
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
            if(gamepad1.dpad_left){
                open.startSpin(  -0.25);
            }
            if(gamepad1.dpad_right){
                open.stopSpin();
            }
            if (gamepad1.left_bumper){
                if(Math.abs(slideL.getPosition())  >400) {
                    turret.startSpin(0.5);
                }else{
                    slideL.startSpin(slidePower);
                    slideR.startSpin(-slidePower);
                }
            }else if(gamepad1.right_bumper){
                if(Math.abs(slideL.getPosition())>400) {
                    turret.startSpin(-0.5);
                }else{
                    slideL.startSpin(slidePower);
                    slideR.startSpin(-slidePower);
                }
            }else{
                turret.stopSpin();
            }

            if (touchSensor.getState()) {
                left.stopSpin();
                right.stopSpin();
            }
            telemetry.addData("a", slideL.getPosition());
            telemetry.update();
        }
    }

}
