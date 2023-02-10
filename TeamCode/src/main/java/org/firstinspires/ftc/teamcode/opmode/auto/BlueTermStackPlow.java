package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous(name = "BlueTermStackPlow")
public class BlueTermStackPlow extends AutoBase {
    public void move(){
        //movement.setSpeed(1);
        resetRobot = false;
        slide.close();
        //movement.strafeRight(10);
        movement.moveStraight(160);
        //score cone
        slide.goToPos(Slide.height.high);
        movement.strafeLeft(10.5);
        slide.spinTurret(slide.turretPower, -550);
        movement.delay(0.5);
        movement.moveStraight(3);
        telemetry.addLine("turret");
        telemetry.update();
        //movement.delay(0.5);
        slide.spinTurretWait(slide.turretPower, -550);
        movement.delay(0.25);
        slide.gotoOther(slide.getValueForHeight(Slide.height.high) - 500);
        movement.delay(0.25);
        slide.open();
        slide.goToPos(Slide.height.high);
        movement.delay(0.25);
        slide.spinTurretWait(slide.turretPower, 0);
        slide.goToPos(Slide.height.low);

        //park
        movement.moveStraight(-30.5);
        movement.strafeRight(cone == 1 ? 91.5 : (cone == 2 ? 30.5 : -30.5));
    }
}
