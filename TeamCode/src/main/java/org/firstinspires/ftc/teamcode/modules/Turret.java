package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turret {
    public double turretPower = 0.3;
    SpinMotor turret;
    LinearOpMode op;
    public Turret(String turretName, LinearOpMode opmode){
        op = opmode;
        turret = new SpinMotor(turretName, true, op);
    }
}
