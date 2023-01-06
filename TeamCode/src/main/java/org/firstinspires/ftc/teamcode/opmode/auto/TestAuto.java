package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "strafe test")
public class TestAuto extends AutoBase{
    public void move() {
        movement.strafeLeft(61*1.15);
    }
}
