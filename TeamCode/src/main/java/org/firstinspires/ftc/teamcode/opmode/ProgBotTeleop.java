package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Movement;
@TeleOp(name = "progBotTelop")
public class ProgBotTeleop extends LinearOpMode {
    public void runOpMode(){
        Movement movement = new Movement("frontLeft", "frontRight","backLeft","backRight",this, false);
        movement.telOpRunMode();
        boolean fieldRelative = true;
        waitForStart();
        while (opModeIsActive()){
            movement.mecanumTeleOpUpdate(0.95,fieldRelative);
            if(gamepad1.back){
                fieldRelative = !fieldRelative;
            }
            if(fieldRelative) {
                telemetry.addData("mode", "driverRealtive");
            }else{
                telemetry.addData("mode","normal");
            }
            telemetry.update();
        }
    }
}
