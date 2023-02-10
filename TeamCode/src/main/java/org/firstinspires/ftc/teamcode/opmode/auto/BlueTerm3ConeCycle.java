package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous
public class BlueTerm3ConeCycle extends AutoBase{
    public void move(){
        //movement.strafeRight(15.2);
        resetRobot = false;
        movement.moveStraight(137.2);
        movement.moveStraight(-30.5);
        //score

        slide.goToPos(Slide.height.med);
        movement.strafeLeft(11);
        telemetry.addLine("turret");
        telemetry.update();
        movement.delay(0.45);
        slide.spinTurretWait(0.7, -415);
        movement.delay(0.3);
        slide.gotoOther(slide.getValueForHeight(Slide.height.med) - 300);
        movement.delay(0.2);
        slide.open();
        movement.strafeRight(11);
        slide.spinTurretWait(0.7, 0);
        slide.goToPos(Slide.height.stack5);
        movement.setSpeed(0.6);

        //go to stack

        movement.moveStraight(30.5);
        movement.turnRight(90);
        movement.moveStraight(65);
        slide.close();
        slide.intakeForTime(0.25);
        slide.goToPos(Slide.height.low);
        movement.delay(0.1);
        movement.moveStraight(-33.5);

        //score cone 1

        slide.spinTurretWait(slide.turretPower, 415);
        movement.strafeRight(6);
        slide.gotoOther(slide.getValueForHeight(Slide.height.low) - 200);
        movement.delay(0.1);
        slide.open();
        movement.delay(0.1);
        movement.strafeLeft(6);
        slide.spinTurretWait(0.7,0);
        slide.goToPos(Slide.height.stack5);

        //grab another cone

        movement.moveStraight(33.5);
        slide.close();
        slide.intakeForTime(0.25);
        slide.gotoOther(slide.getValueForHeight(Slide.height.low)+100);
        movement.delay(0.1);
        movement.moveStraight(-33.5);

        //score another cone

        slide.spinTurretWait(slide.turretPower, 415);
        movement.strafeRight(3);
        slide.gotoOther(slide.getValueForHeight(Slide.height.low) - 200);
        movement.delay(0.1);
        slide.open();
        movement.delay(0.1);
        movement.strafeLeft(3);
        slide.spinTurretWait(0.7,0);
        slide.goToPos(Slide.height.stack4);

        //grab a third cone

        movement.moveStraight(33.5);
        slide.close();
        slide.intakeForTime(0.25);
        slide.gotoOther(slide.getValueForHeight(Slide.height.low)+100);
        movement.delay(0.1);
        movement.moveStraight(-37);

        //score a third cone

        slide.spinTurretWait(slide.turretPower, 415);
        movement.strafeRight(3);
        slide.gotoOther(slide.getValueForHeight(Slide.height.low) - 200);
        movement.delay(0.1);
        slide.open();
        movement.delay(0.1);
        movement.strafeLeft(3);
        slide.spinTurretWait(0.7,0);
        slide.goToPos(Slide.height.ground);
    }
}
