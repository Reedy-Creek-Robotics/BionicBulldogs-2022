package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous(name = "BlueTermStackPlow")
@Disabled
public class BlueTermStackPlow extends AutoBase {
    public void move(){
        //movement.setSpeed(1);
        resetRobot = false;
        intake.close();
        //movement.strafeRight(10);
        movement.moveStraight(160);
        //score cone
        slide.goToPos(Slide.height.high);
        movement.strafeLeft(10.5);
        turret.spinTurret(turret.turretPower, -550);
        movement.delay(0.5);
        movement.moveStraight(3);
        telemetry.addLine("turret");
        telemetry.update();
        //movement.delay(0.5);
        turret.spinTurretWait(turret.turretPower, -550);
        movement.delay(0.25);
        slide.gotoOther(slide.getValueForHeight(Slide.height.high) - 500);
        movement.delay(0.25);
        intake.open();
        slide.goToPos(Slide.height.high);
        movement.delay(0.25);
        turret.spinTurretWait(turret.turretPower, 0);
        slide.goToPos(Slide.height.low);

        //park
        movement.moveStraight(-30.5);
        movement.strafeRight(cone == 1 ? 91.5 : (cone == 2 ? 30.5 : -30.5));
    }
}
