package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SpinCrServo {
    CRServo servo;
    LinearOpMode opMode;
    Telemetry telemetry;
    boolean hasEncoder = false;
    boolean movingToPos = false;
    boolean holdPos = false;
    int position;
    DcMotor encoder;
    boolean hasServo = false;
    public SpinCrServo (String s, LinearOpMode op) {
        opMode = op;
        if(!op.hardwareMap.crservo.contains(s)){
            return;
        }
        hasServo = true;
        servo = op.hardwareMap.crservo.get(s);
        telemetry = op.telemetry;
        stopSpin();
    }

    public void setEncoder(DcMotor e){
        encoder = e;
        hasEncoder = true;
    }

    public void spinForTime(double speed, long time){
        if (opMode.opModeIsActive()&&hasServo){
            servo.setPower(speed);
            opMode.sleep(time);
            servo.setPower(0);
        }
    }
    public void startSpin(double speed) {
        if (opMode.opModeIsActive()&&hasServo){
            servo.setPower(speed);
        }
    }
    public void stopSpin() {
        if (opMode.opModeIsActive()&&hasServo){
            servo.setPower(0);
        }
    }
    public void setTargetPos(int pos, boolean holdPosition){
        if (hasEncoder&&hasServo) {
            position = pos;
            movingToPos = true;
            holdPos = holdPosition;
        }
    }
    public void updatePosition(double speed){
        if(hasEncoder && movingToPos&&hasServo){
            if(encoder.getCurrentPosition()>position+5){
                servo.setPower(speed);
            }else if(encoder.getCurrentPosition()<position-5){
                servo.setPower(-speed);
            }else{
                servo.setPower(0);
                if(holdPos) {
                    movingToPos = false;
                }
            }
        }
        telemetry.addData("currentPosition", encoder.getCurrentPosition());
        telemetry.addData("is Moving", movingToPos);
        telemetry.update();
    }
    public void stopMoving(){
        if(hasServo){
            movingToPos = false;
        }
    }
    public double getPower(){
        if(hasServo) {
            return servo.getPower();
        }
        return 0;
    }
}