package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Recoginition;
import org.firstinspires.ftc.teamcode.modules.Slide;
@Autonomous(name = "RedTerm")
public class AutoLeftSideCenterPole extends AutoBase {
    public void move(){
        slide.close();
        movement.strafeRight(61);
        movement.moveStraight(102);
        telemetry.addLine("slide");
        telemetry.update();
        slide.goToPos(Slide.height.high);
        telemetry.addLine("turret");
        telemetry.update();
        movement.delay(1.25);
        slide.spinTurretWait(0.4, -550);
        movement.strafeRight(3);
        movement.delay(2);
        slide.open();
        movement.delay(0.1);
        movement.strafeLeft(3);
        slide.spinTurretWait(0.4, 0);
        slide.goToPosWait(Slide.height.ground);
        //slide.close();
        //slide.goToPos(Slide.height.ground);//move turret to -560
        if(cone == 3) return;
        movement.moveStraight(-31.5);
        movement.strafeLeft( (cone != 3 ? 61 : 0) + (cone == 1 ? 67 : 0));



        //no turret
//        movement.strafeLeft(28.5);
//        movement.moveStraight(5);
//        movement.delay(2);
//        slide.open();
//        movement.moveStraight(-5);
//        movement.delay(1);
//        slide.goToPos(Slide.height.ground);
//        movement.strafeRight(33.5 + (cone != 1 ? 64 : 0) + (cone == 3 ? 67 : 0));
//        if(cone == 1){
//            movement.moveStraight(51);
//        }
        slide.close();
    }
}
