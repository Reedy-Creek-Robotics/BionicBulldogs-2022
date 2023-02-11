package org.firstinspires.ftc.teamcode.opmode.auto;

import static androidx.core.math.MathUtils.clamp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Recoginition;
import org.firstinspires.ftc.teamcode.modules.Slide;

public abstract class AutoBase extends LinearOpMode {
    Movement movement;
    Slide slide;
    int cone = 0;
    final double deafultSpeed = 0.7;
    boolean resetRobot = true;
    public abstract void move();
    public void runOpMode(){
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        slide = new Slide("slideL", "slideR", "leftWheel", "rightWheel", "open", "turret", "touchSensor", this);
        movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, false);
        movement.setSpeed(deafultSpeed);
        movement.addSlide(slide);
        Recoginition recoginition = new Recoginition(this);
        slide.turretPower = 0.7;
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
            slide.resetTurret();
            slide.close();
            move();
            if (resetRobot) {
                telemetry.addLine("resetSlides");
                telemetry.update();
                slide.goToPosWait(Slide.height.ground);
            }
        }
    }
}
