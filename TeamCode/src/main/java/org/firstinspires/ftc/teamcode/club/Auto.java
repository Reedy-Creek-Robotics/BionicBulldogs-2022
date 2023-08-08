package org.firstinspires.ftc.teamcode.club;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class Auto extends LinearOpMode {
    public void runOpMode(){
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        ElapsedTime t = new ElapsedTime();
        t.reset();

        frontLeft.setPower(0.8);
        frontRight.setPower(0.8);
        backLeft.setPower(0.8);
        backRight.setPower(0.8);
        while (t.seconds() < 1 && opModeIsActive()){
            //wait :/
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        t.reset();


        while (t.seconds() < 1 && opModeIsActive()){
            //wait :/
        }
        frontLeft.setPower(-0.8);
        frontRight.setPower(-0.8);
        backLeft.setPower(-0.8);
        backRight.setPower(-0.8);
        t.reset();
        while (t.seconds() < 1 && opModeIsActive()){
            //wait :/
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
