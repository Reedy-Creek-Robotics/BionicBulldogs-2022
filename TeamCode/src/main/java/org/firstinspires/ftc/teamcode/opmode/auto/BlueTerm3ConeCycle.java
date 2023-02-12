package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous
public class BlueTerm3ConeCycle extends AutoBase{
    public void move() {
        //movement.strafeRight(15.2);
        //resetRobot = false;
        movement.moveStraight(142.2);
        movement.moveStraight(-33.5);
        //score

        slide.goToPos(Slide.height.med);
        movement.strafeLeft(12);
        telemetry.addLine("turret");
        telemetry.update();
        movement.delay(0.45);
        turret.spinTurretWait(turret.turretPower, -400);
        movement.delay(0.3);
        slide.gotoOther(slide.getValueForHeight(Slide.height.med) - 300);
        movement.delay(0.2);
        intake.open();
        movement.strafeRight(11);
        turret.spinTurretWait(turret.turretPower, 0);
        slide.goToPos(Slide.height.stack5);
        movement.setSpeed(0.6);

        movement.moveStraight(31.5);
        movement.turnRight(90);
        movement.moveStraight(68);
        //go to stack
        for(int i = 0; i < 1; i++){
            if(i != 0) {
                movement.moveStraight(33.5);
            }
            intake.close();
            intake.intakeForTime(0.5);
            slide.goToPos(Slide.height.low);
            movement.delay(0.1);
            movement.moveStraight(-33.5);

            //score cone 1

            turret.spinTurretWait(turret.turretPower, 400);
            movement.strafeRight(8);
            slide.gotoOther(slide.getValueForHeight(Slide.height.low) - 200);
            movement.delay(0.1);
            intake.open();
            movement.delay(0.1);
            movement.strafeLeft(8);
            turret.spinTurretWait(turret.turretPower, 0);
            slide.goToPos(
                    i == 0 ? Slide.height.stack5 : (
                    i == 1 ? Slide.height.stack4 :
                    Slide.height.stack3)
            );
        }
        switch (cone){
            case 1:
                movement.strafeLeft(5);
                movement.moveStraight(-91.4);
                break;
            case 2:
                movement.moveStraight(-30.5);
                break;
            case 3:
                movement.moveStraight(30.5);
                break;
        }
        telemetry.addLine("done");
        telemetry.update();
    }
}
