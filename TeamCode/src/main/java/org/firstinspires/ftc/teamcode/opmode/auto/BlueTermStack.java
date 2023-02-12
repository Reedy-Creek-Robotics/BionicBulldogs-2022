package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.modules.Slide;
@Autonomous(name = "BlueTermStack")
@Disabled
public class BlueTermStack extends AutoBase {
    public void move(){
        //resetRobot = false;
        intake.close();
        movement.strafeLeft(53);
        movement.moveStraight(102);
        telemetry.addLine("slide");
        slide.goToPos(Slide.height.high);
        movement.moveAndTurn(35,0,72);
        telemetry.update();

        //score preload
        movement.strafeLeft(17.5);
        turret.spinTurret(turret.turretPower, -550);
        movement.moveStraight(2);
        telemetry.addLine("turret");
        telemetry.update();
        //movement.delay(0.5);
        turret.spinTurretWait(turret.turretPower, -550);
        movement.delay(0.125);
        slide.gotoOther(slide.getValueForHeight(Slide.height.high) - 500);
        movement.delay(0.125);
        intake.open();
        movement.strafeRight(5);
        turret.spinTurretWait(0.7, -18);
        slide.goToPos(Slide.height.stack5);

        //stack
        movement.setSpeed((deafultSpeed*2)/3);
        movement.delay(0.125);
        movement.moveStraight(96);
        intake.close();
        movement.delay(0.125);
        slide.goToPos(Slide.height.low);
        intake.intakeForTime(0.5);
        movement.moveStraight(-92);

        //score second cone
        slide.goToPos(Slide.height.high);
        movement.strafeLeft(11);
        telemetry.addLine("turret");
        telemetry.update();
        movement.delay(0.45);
        turret.spinTurretWait(0.7, -450);
        movement.delay(0.3);
        slide.gotoOther(slide.getValueForHeight(Slide.height.high) - 500);
        movement.delay(0.5);
        intake.open();
        movement.strafeRight(5);
        turret.spinTurretWait(0.7, 0);
        slide.goToPos(Slide.height.ground);

        //park
        movement.moveStraight(cone == 1 ? -32.5 : (cone == 2 ? 30.5 : 89.4));
    }
}
