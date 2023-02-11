package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous(name = "RedTermStack")
@Disabled
public class RedTermStack extends AutoBase {
    public void move(){
        slide.close();
        movement.strafeRight(61-15.2);
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
        if(cone == 3) return;
        movement.moveStraight(-31.5);
        movement.strafeLeft( (cone != 3 ? 61 : 0) + (cone == 1 ? 67 : 0));
    }
}
