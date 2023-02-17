package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.Slide;

// This is the current auto mode

@Autonomous
public class RedTerm3ConeCycle extends AutoBase{
    public void move() {
        //movement.strafeRight(15.2);
        //resetRobot = false;
        movement.setSpeed(0.6);
        movement.moveStraight(142.2); //moves the robot the first position and leaves cone there
        movement.setSpeed(0.7);
        movement.moveStraight(-33.5);// goes back to score
        movement.setSpeed(0.5);
        //score

        slide.goToPos(Slide.height.med); // slides goes to medium
        movement.strafeRight(7); // the robot strafes left
        movement.setSpeed(0.6);
        telemetry.addLine("turret");
        telemetry.update();
        movement.delay(0.45); // robot waits for cone to intake
        turret.spinTurretWait(turret.turretPower, 350);
        movement.delay(0.3);
        slide.gotoOther(slide.getValueForHeight(Slide.height.med) - 300); // slides moves lower for cone to score
        movement.delay(0.1);
        intake.open();
        movement.delay(0.1);// intake opens and drops cone
        movement.strafeLeft(11); // robot strafes right
        turret.spinTurretWait(turret.turretPower, 0);
        slide.goToPos(Slide.height.stack5); // slides goes to height to intake cone from stack

        movement.moveStraight(29.5);
        movement.setSpeed(0.7);
        movement.turnLeft(90);
        movement.moveStraight(68);
        //go to stack
        for(int i = 0; i < 3; i++){
            if(i != 0) {
                movement.moveStraight(35.5);
            }
            intake.close(); // the intake closes around the cone
            intake.intakeForTime(0.5); //
            slide.goToPos(Slide.height.low); // the slides goes to low for scoring
            movement.delay(0.1);
            movement.moveStraight(-33.5);

            //score cone 1

            turret.spinTurretWait(turret.turretPower, -400);
            movement.setSpeed(0.5);
            movement.strafeLeft((i == 3 ? 6 : (i == 1 ? 8 : 11)));
            slide.gotoOther(slide.getValueForHeight(Slide.height.low) - 200); // slides goes down to score
            intake.open(); // intake opens
            movement.delay(0.2); // robot stays in the same position
            movement.strafeRight(8 - 2 * i);
            movement.setSpeed(0.45);
            turret.spinTurretWait(turret.turretPower, 0);
            slide.goToPos( //positions for cones in stake
                    i == 0 ? Slide.height.stack5 : (
                            i == 1 ? Slide.height.stack4 :
                                    i == 2 ? Slide.height.stack3 :
                                            Slide.height.ground)
            );
        }
        movement.setSpeed(0.9);
        switch (cone){
            case 3:
                movement.moveStraight(-91.4);
                break;
            case 2:
                movement.moveStraight(-30.5);
                break;
            case 1:
                movement.moveStraight(30.5);
                break;
        }
        telemetry.addLine("done");
        telemetry.update();
    }
}
