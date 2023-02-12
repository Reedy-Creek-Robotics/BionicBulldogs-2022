package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    SpinCrServo wheelL;
    SpinCrServo wheelR;
    SpinCrServo open;
    LinearOpMode opMode;
    public double intakePower = 1;
    double openPos = -0.25;
    ElapsedTime t;
    public Intake(String wheell, String wheelr, String opens, LinearOpMode op){
        wheelL = new SpinCrServo(wheell,op);
        wheelR = new SpinCrServo(wheelr,op);
        open = new SpinCrServo(opens,op);
        opMode = op;
        t = new ElapsedTime();
        t.reset();
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
}
