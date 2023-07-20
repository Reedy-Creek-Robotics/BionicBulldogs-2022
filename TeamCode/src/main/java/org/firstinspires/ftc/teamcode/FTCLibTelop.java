package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.GyroEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gyroscope;

@TeleOp(name="FTCLibTelop")
public class FTCLibTelop extends LinearOpMode {
    double MoveSpeed = 0.8;
    public void runOpMode(){
        MecanumDrive drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeft"),
                new Motor(hardwareMap, "frontRight"),
                new Motor(hardwareMap, "backLeft"),
                new Motor(hardwareMap, "backRight")
        );
        RevIMU imu = new RevIMU(hardwareMap, "imu");
        imu.init();
        imu.reset();
        waitForStart();
        while (opModeIsActive()){
            drive.driveFieldCentric(gamepad1.left_stick_x * MoveSpeed, gamepad1.left_stick_y * MoveSpeed, gamepad1.right_stick_x * MoveSpeed, imu.getHeading());
        }
    }
}
