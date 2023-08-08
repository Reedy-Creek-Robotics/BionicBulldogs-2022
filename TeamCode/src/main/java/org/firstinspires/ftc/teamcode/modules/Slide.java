package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slide {
    int lowPos = 1300;
    int[] stackPos = {0,100,200,300,400};
    int medPos = 2092;
    int highPos = 2850;
    public double slidePower = 1;
    float offset = 20;
    public enum height{ground,low, med, high, stack3, stack4, stack5};
    SpinMotor slideL;
    SpinMotor slideR;
    TouchSensorModule touchSensor;
    LinearOpMode opMode;
    public height currentHight = height.ground;
    public Slide(String slidel, String slider, String Sensor, LinearOpMode op){
        slideL = new SpinMotor(slidel,true,op);
        slideR = new SpinMotor(slider,true,op);
        touchSensor = new TouchSensorModule(Sensor, op);
        slideL.reverse();
        opMode = op;
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
    public void isMoving(){
        if((slideL.isBusy() || slideR.isBusy()) && touchSensor.getState()) {
            slideL.stopSpin();
            slideR.stopSpin();
            slideL.resetEncoder();
            slideR.resetEncoder();
        }
    }
    public void goToPosWait(height pos){
        goToPos(pos);
        while(!atPosition(pos)){
            isMoving();
        }
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
                slideR.getPosition() < getValueForHeight(pos) + offset && !opMode.isStopRequested());
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

    public void gotoOther(int position){
        slideL.spinToPosition(slidePower, position);
        slideR.spinToPosition(slidePower, position);
    }
    public int getTargetPosition(){
        return Math.abs(slideL.getMotor().getTargetPosition()) + Math.abs(slideR.getMotor().getTargetPosition());
    }
}
