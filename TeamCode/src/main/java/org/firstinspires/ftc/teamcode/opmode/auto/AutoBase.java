package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Recoginition;
import org.firstinspires.ftc.teamcode.modules.Slide;

public abstract class AutoBase extends LinearOpMode {
    Movement movement;
    Slide slide;
    int cone = 0;
    public abstract void move();
    public void runOpMode(){
        slide = new Slide("slideL", "slideR", "leftWheel", "rightWheel", "open", "turret", "touchSensor", this);
        movement = new Movement("frontLeft", "frontRight", "backLeft", "backRight", this, false);
        movement.setSpeed(0.5);
        Recoginition recoginition = new Recoginition(this);
        while(opModeInInit()){
            recoginition.update();
            sleep(20);
        }
        waitForStart();
        cone = recoginition.cone;
        telemetry.addData("cone", cone);
        telemetry.update();
        cone = Math.abs(cone);
        slide.resetTurret();
        move();
        slide.goToPosWait(Slide.height.ground);
    }
}
