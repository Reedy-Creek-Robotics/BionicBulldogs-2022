package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slide {
    int lowPos = 1300;
    int[] stackPos = {0,0,150,300,400};
    int medPos = 2092;
    int highPos = 3000;
    double openPos = -0.25;
    public double slidePower = 1;
    double intakePower = 1;
    public double turretPower = 0.3;
    float offset = 20;
    public enum height{ground,low, med, high, stack3, stack4, stack5};
    ElapsedTime t;
    SpinMotor slideL;
    SpinMotor slideR;
    SpinCrServo wheelL;
    SpinCrServo wheelR;
    SpinCrServo open;
    SpinMotor turret;
    TouchSensorModule touchSensor;
    LinearOpMode opMode;
    public height currentHight = height.ground;
    public Slide(String slidel, String slider,String wheell, String wheelr,String opens, String Turret, String _touchSensor, LinearOpMode op){
        slideL = new SpinMotor(slidel,true,op);
        slideR = new SpinMotor(slider,true,op);
        slideL.reverse();
        wheelL = new SpinCrServo(wheell,op);
        wheelR = new SpinCrServo(wheelr,op);
        touchSensor = new TouchSensorModule(_touchSensor, op);
        turret = new SpinMotor(Turret, true, op);
        open = new SpinCrServo(opens,op);
        t = new ElapsedTime();
        t.reset();
        opMode = op;
    }
    public void goToPos(height pos) {
        currentHight = pos;
        switch (pos) {
            case ground:
                slideR.spinToPosition(slidePower,-10);
                slideL.spinToPosition(slidePower,-10);
                break;
            case low:
                slideR.spinToPosition(slidePower,lowPos);
                slideL.spinToPosition(slidePower,lowPos);
                break;
            case med:
                slideR.spinToPosition(slidePower,medPos);
                slideL.spinToPosition(slidePower,medPos);
                break;
            case high:
                slideR.spinToPosition(slidePower,highPos);
                slideL.spinToPosition(slidePower,highPos);
                break;
            case stack3:
                slideR.spinToPosition(slidePower, stackPos[2]);
                slideL.spinToPosition(slidePower, stackPos[2]);
            case stack4:
                slideR.spinToPosition(slidePower, stackPos[3]);
                slideL.spinToPosition(slidePower, stackPos[3]);
                break;
            case stack5:
                slideR.spinToPosition(slidePower, stackPos[4]);
                slideL.spinToPosition(slidePower, stackPos[4]);
                break;
        }
    }
    public void goToPosWait(height pos){
        goToPos(pos);
        while(!atPosition(pos)){

        }
    }
    public void startIntake(){
        wheelL.startSpin(-intakePower);
        wheelR.startSpin(intakePower);
    }
    public void startOutake(){
        wheelL.startSpin(intakePower);
        wheelR.startSpin(-intakePower);
    }
    public void stopIntake(){
        wheelL.stopSpin();
        wheelR.stopSpin();
    }
    public void open(){
        open.startSpin(openPos);
    }
    public void close(){
        open.stopSpin();
    }
    public void intakeForTime(double time){
        t.reset();
        startIntake();
        while (t.seconds() < time && opMode.opModeIsActive()){

        }
        stopIntake();
    }
    public void outakeForTime(double time){
        t.reset();
        startOutake();
        while (t.seconds() < time){

        }
        stopIntake();
    }
    public void openForTime(double time){
        t.reset();
        open();
        while (t.seconds() < time){

        }
        close();
    }
    public int getValueForHeight(height pos){
        switch (pos){
            case ground:
                return 0;
            case low:
                return lowPos;
            case med:
                return medPos;
            case high:
                return highPos;
        }
        return 0;
    }
    public boolean atPosition(height pos){
        return(slideL.getPosition() > getValueForHeight(pos) - offset &&
                slideL.getPosition() < getValueForHeight(pos) + offset &&
                slideR.getPosition() > getValueForHeight(pos) - offset &&
                slideR.getPosition() < getValueForHeight(pos) + offset);
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
    public int getSlidePosition(){
        return (Math.abs(slideL.getPosition()) + Math.abs(slideR.getPosition()))/2;
    }
    public int getSlidePosition(int slide){
        switch (slide){
            default:
                return (Math.abs(slideL.getPosition()) + Math.abs(slideR.getPosition()))/2;
            case 0:
                return slideL.getPosition();
            case 1:
                return slideR.getPosition();
        }
    }
    public boolean touchSensorPressed(){
        return touchSensor.getState();
    }
    public void setSlidePower(double power){
        slideL.startSpin(power);
        slideR.startSpin(power);
    }
    public void resetSlideEncoders(){
        slideL.resetEncoder();
        slideR.resetEncoder();
    }
    public boolean isBusy(){
        return slideL.isBusy() || slideR.isBusy();
    }
    public int getTurretPosition(){
        return turret.getPosition();
    }
    public boolean isTurretBusy(){
        return turret.isBusy();
    }
    public void gotoOther(int position){
        slideL.spinToPosition(slidePower, position);
        slideR.spinToPosition(slidePower, position);
    }
    public int getTargetPosition(){
        return Math.abs(slideL.getMotor().getTargetPosition()) + Math.abs(slideR.getMotor().getTargetPosition());
    }
    public void resetTurretEncoder(){
        turret.resetEncoder();
    }
}
