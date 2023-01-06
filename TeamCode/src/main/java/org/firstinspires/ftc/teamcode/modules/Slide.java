package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slide {
    int lowPos = 2000;
    int[] stackPos = {0,0,0,0,800};
    int medPos = 3140;
    int highPos = 4500;
    double openPos = -0.25;
    public double slidePower = 0.8;
    double intakePower = 0.5;
    double turretPower = 0.4;
    float offset = 20;
    public enum height{ground,low, med, high, stack5};
    ElapsedTime t;
    SpinMotor slideL;
    SpinMotor slideR;
    SpinCrServo wheelL;
    SpinCrServo wheelR;
    SpinCrServo open;
    SpinMotor turret;
    TouchSensorModule touchSensor;
    public height currentHight = height.ground;
    public Slide(String slidel, String slider,String wheell, String wheelr,String opens, String Turret, String _touchSensor, LinearOpMode op){
        slideL = new SpinMotor(slidel,true,op);
        slideR = new SpinMotor(slider,true,op);
        slideR.reverse();
        wheelL = new SpinCrServo(wheell,op);
        wheelR = new SpinCrServo(wheelr,op);
        touchSensor = new TouchSensorModule(_touchSensor, op);
        turret = new SpinMotor(Turret, true, op);
        open = new SpinCrServo(opens,op);
        t = new ElapsedTime();
        t.reset();
    }
    public void goToPos(height pos) {
        currentHight = pos;
        switch (pos) {
            case ground:
                slideR.spinToPosition(slidePower,0);
                slideL.spinToPosition(slidePower,0);
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
        while (t.seconds() < time){

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
    int getValueForHeight(height pos){
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
    public void spinTurretWait(double power, int position){
        turret.spinToPosition(power, position);
        while(turret.isBusy()){
            //do nothing :)
        }
    }
    public void spinTurret(double power, int position){
        turret.spinToPosition(turretPower, position);
    }
    public void spinTurret(double power){
        turret.startSpin(power);
    }
    public int getSlidePosiion(){
        return (Math.abs(slideL.getPosition()) + Math.abs(slideR.getPosition()))/2;
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
}
