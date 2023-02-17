package org.firstinspires.ftc.teamcode.opmode.auto;

import static androidx.core.math.MathUtils.clamp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Recoginition;
import org.firstinspires.ftc.teamcode.modules.Slide;
import org.firstinspires.ftc.teamcode.modules.TouchSensorModule;
import org.firstinspires.ftc.teamcode.modules.Turret;

public abstract class AutoBase extends LinearOpMode {
    Movement movement;
    Slide slide;
    Turret turret;
    Intake intake;
    TouchSensorModule touchSensor;
    int cone = 0;
    final double deafultSpeed = 0.7;
    boolean resetRobot = true;
    public abstract void move();
    public void runOpMode(){
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        slide = new Slide("slideL", "slideR", "slideSensor", this);
        turret = new Turret("turret", this);
        intake = new Intake("leftWheel", "rightWheel", "open", this);
        touchSensor = new TouchSensorModule("touchSensor", this);
        movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, false);
        movement.setSpeed(deafultSpeed);
        movement.addSlide(touchSensor);
        Recoginition recoginition = new Recoginition(this);
        while(opModeInInit()){
            recoginition.update();
            sleep(20);
        }
        waitForStart();
        if(opModeIsActive()) {
            cone = recoginition.cone;
            telemetry.addData("cone", cone);
            telemetry.update();
            cone = clamp(Math.abs(cone),1,3);
            turret.resetTurret();
            intake.close();
            move();
            if (resetRobot) {
                telemetry.addLine("resetSlides");
                telemetry.update();
                slide.goToPosWait(Slide.height.ground);
            }
        }
    }
}
