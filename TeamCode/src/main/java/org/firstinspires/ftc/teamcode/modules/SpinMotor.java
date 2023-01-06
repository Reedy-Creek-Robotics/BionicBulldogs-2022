package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SpinMotor {
    DcMotor motor;
    LinearOpMode opMode;
    Telemetry telemetry;
    boolean hasMotor = false;
    boolean reversed = false;
    public SpinMotor (String dm, boolean brake, LinearOpMode op) {
        opMode = op;
        if(!op.hardwareMap.dcMotor.contains(dm)){
            return;
        }
        hasMotor = true;
        motor = op.hardwareMap.dcMotor.get(dm);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(0);
        telemetry = op.telemetry;
        if(brake){
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else{
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
    public void spinForTime(double speed, long time){
        if (opMode.opModeIsActive()&&hasMotor){
            motor.setPower(speed);
            opMode.sleep(time);
            motor.setPower(0);
        }
    }
    public void startSpin(double speed) {
        if (opMode.opModeIsActive()&&hasMotor){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setPower(speed);
        }
    }
    public void stopSpin() {
        if (opMode.opModeIsActive()&&hasMotor){
            motor.setPower(0);
        }
    }
    public DcMotor getMotor(){
        return motor;
    }
    public void spinToPosition(double speed, int position){
        spinToPosition(speed,position,true);
    }

    public void spinToPosition(double speed, int position, boolean stopPowering){
        if(hasMotor) {
            if(reversed) {
                motor.setTargetPosition(position);
            }else{
                motor.setTargetPosition(-position);
            }
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
        }
    }
    public int getPosition(){
        if(hasMotor) {
            return motor.getCurrentPosition();
        }else{
            return -1;
        }
    }
    public boolean isBusy(){
        if(hasMotor) {
            return motor.isBusy();
        }else{
            return false;
        }
    }
    public double getPower(){
        if(hasMotor) {
            return motor.getPower();
        }else{
            return 0;
        }
    }
    public void reverse(){
        if(hasMotor) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
            reversed = true;
        }
    }
    public void resetEncoder(){
        if(hasMotor) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}