package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class ClubBotTelop extends LinearOpMode {
    public void runOpMode(){
        DcMotor leftMotor = hardwareMap.dcMotor.get("left");
        DcMotor rightMotor = hardwareMap.dcMotor.get("right");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()){
            double forward = gamepad1.left_stick_y * 1.0;
            double right = -gamepad1.right_stick_x * 1.0;
            leftMotor.setPower(forward + right);
            rightMotor.setPower(forward - right);
        }
    }
}
