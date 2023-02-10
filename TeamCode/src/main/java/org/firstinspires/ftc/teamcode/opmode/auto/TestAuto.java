package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.modules.Slide;

@Autonomous(name = "test auto")
public class TestAuto extends AutoBase {
    public void move() {
        resetRobot = false;
        TestAutoSettings settings = new TestAutoSettings();
        movement.setSettings(settings.moveCorrection, settings.strafeCorrection, settings.rotateCorrection);
        switch (settings.moveMode){
            case move:
                movement.moveStraight(settings.distance);
                break;
            case rotate:
                movement.turnRight(settings.distance);
                break;
            case strafe:
                movement.strafeRight(settings.distance);
        }
    }
}
