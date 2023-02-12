package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.Slide;
@Autonomous(name = "RedTerm")
public class RedTerm extends AutoBase {
    public void move(){
        intake.close();
        movement.setSpeed(0.5);
        movement.strafeRight(70);
        movement.moveStraight(102);
        telemetry.addLine("slide");
        telemetry.update();
        movement.strafeRight(8);
        slide.goToPos(Slide.height.high);
        telemetry.addLine("turret");
        telemetry.update();
        movement.delay(1.25);
        turret.spinTurretWait(0.4, 550);
        movement.delay(2);
        slide.gotoOther(slide.getValueForHeight(Slide.height.high)-500);
        movement.delay(1);
        intake.open();
        movement.delay(0.25);
        slide.goToPos(Slide.height.high);
        //movement.delay(1);
        movement.strafeLeft(10);
        turret.spinTurretWait(0.4, 0);
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
        intake.close();
    }
}
