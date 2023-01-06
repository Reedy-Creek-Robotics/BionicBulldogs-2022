package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class SpinServo {
    Servo servo;
    LinearOpMode opMode;
    double startpos,endpos;
    boolean hasServo = false;
    public SpinServo(String s, double start, double end, LinearOpMode op){
        opMode = op;
        if(!op.hardwareMap.servo.contains(s)){
            return;
        }
        op.telemetry.addLine("inited servo");
        op.telemetry.update();
        hasServo = true;
        servo = op.hardwareMap.servo.get(s);
        startpos = start;
        endpos = end;
        goToStart();
    }
    public void rotateForTime(long time){
        if (opMode.opModeIsActive()&&hasServo){
            servo.setPosition(endpos);
            opMode.sleep(time);
            servo.setPosition(startpos);
        }
    }
    public void goToStart() {
        if (opMode.opModeIsActive()&&hasServo){
            servo.setPosition(startpos);
            opMode.telemetry.addData("c",servo.getPosition());
            opMode.telemetry.addData("a",startpos);
            opMode.telemetry.update();
        }
    }
    public void goToEnd() {
        if (opMode.opModeIsActive()&&hasServo){
            servo.setPosition(endpos);
            opMode.telemetry.addData("o",servo.getPosition());
            opMode.telemetry.addData("a",endpos);
            opMode.telemetry.update();
        }
    }
    public void goToPos(double pos) {
        if (opMode.opModeIsActive()&&hasServo) {
            servo.setPosition(pos);
        }
    }
    public double getPos(){
        if (opMode.opModeIsActive()&&hasServo){
            return(servo.getPosition());
        }else{
            return(0);
        }
    }
    public void setStartpos(double pos){
        startpos = pos;
    }
    public void setEndpos(double pos){
        endpos = pos;
    }
}
