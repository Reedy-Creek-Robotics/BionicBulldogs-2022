package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Recoginition;
import org.firstinspires.ftc.teamcode.modules.Slide;
@Disabled
@Autonomous(name = "leftSideLeftPole")
public class AutoLeftSideLeftPole extends AutoBase {
    public void move(){
        movement.strafeLeft(70);
        movement.moveStraight(127);
        movement.strafeRight(96.5);
        //movement.moveStraight(15.24);
        slide.goToPos(Slide.height.high);
        slide.open();
        movement.delay(1);
        switch (cone){
            case 1:
                movement.strafeRight(30.5);
                break;
            case 2:
                movement.strafeLeft(30.5);
                break;
            case 3:
                movement.strafeLeft(91.5);
                break;
        }

        slide.close();
        slide.goToPos(Slide.height.ground);
    }
}
