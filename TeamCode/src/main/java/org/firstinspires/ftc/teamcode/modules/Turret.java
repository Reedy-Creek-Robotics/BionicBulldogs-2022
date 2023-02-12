package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turret {
    public double turretPower = 0.3;
    SpinMotor turret;
    LinearOpMode opMode;
    public Turret(String turretName, LinearOpMode op){
        opMode = op;
        turret = new SpinMotor(turretName, true, op);
    }
    public void resetTurret(){
        turret.spinToPosition(turretPower, 0);
    }
    public void resetTurretWait(){
        resetTurret();
        while(turret.isBusy() && opMode.opModeIsActive()){
            //do nothing :)
        }
    }
    public void spinTurretWait(double power, int position){
        turret.spinToPosition(power, position);
        opMode.telemetry.addData("turret", turret.getPosition());
        opMode.telemetry.update();
        while(turret.isBusy() && opMode.opModeIsActive()){
            //do nothing :)
            opMode.telemetry.addData("turret", turret.getPosition());
            opMode.telemetry.update();
        }
    }
    public void spinTurret(double power, int position){
        turret.spinToPosition(turretPower, position);
    }
    public void spinTurret(double power){
        turret.startSpin(power);
    }
    public int getPosition(){
        return turret.getPosition();
    }
    public boolean isBusy(){
        return turret.isBusy();
    }
    public void resetEncoder(){
        turret.resetEncoder();
    }
}
