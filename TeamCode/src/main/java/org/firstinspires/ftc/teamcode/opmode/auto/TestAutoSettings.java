package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.config.Config;

@Config
public class TestAutoSettings {
    public static double strafeCorrection = 1;
    public static double moveCorrection = 1;
    public static double rotateCorrection = 1;

    public enum mode {
        move, strafe, rotate
    }

    public static mode moveMode = mode.move;
    public static double distance = 10;
}
