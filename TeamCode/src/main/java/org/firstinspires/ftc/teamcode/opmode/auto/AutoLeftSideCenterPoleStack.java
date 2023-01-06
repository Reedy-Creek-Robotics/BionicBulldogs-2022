package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous(name = "RedTermStack")
public class AutoLeftSideCenterPoleStack extends AutoBase {
    public void move(){
        slide.close();
        movement.strafeRight(61);
        movement.moveStraight(72);
        slide.goToPos(Slide.height.high);
        movement.strafeRight(35);
        movement.moveStraight(6);
        movement.delay(2);
        slide.open();
        movement.moveStraight(-7);
        movement.delay(1);
        //stack
        slide.goToPos(Slide.height.ground);
        movement.strafeLeft(160);
        movement.moveStraight(61);
        slide.goToPos(Slide.height.stack5);
        movement.turnLeft(78);
        movement.delay(5);
        movement.moveStraight(10);
        slide.close();
        /*
        slide.goToPos(Slide.height.ground);
        movement.strafeLeft(31.5 + (cone != 3 ? 64 : 0) + (cone == 1 ? 67 : 0));
        slide.close();*/
    }
}
