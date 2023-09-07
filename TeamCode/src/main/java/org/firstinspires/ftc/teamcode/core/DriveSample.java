package org.firstinspires.ftc.teamcode.core;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.core.RobotConfig;

/**
 * Simple sample using FTC lib to implement field centric and robot centric driving
 * A real implementation needs to use Commands and Subsystems
 */
public class DriveSample extends LinearOpMode {
    MecanumDrive drive;
    RevIMU revIMU; // how do we use the one on control hub?
    GamepadEx gamepad;
    boolean fieldCentricOn = true;

    @Override
    public void runOpMode() throws InterruptedException {
        // init
        initHardware();

        // WAIT - ALL INIT GOES BEFORE THIS LINE
        // ROBOT SHOULD NOT HAVE MOVE ACTIONS BEFORE THIS
        waitForStart();

        while(!isStopRequested()) {
            if( gamepad.wasJustPressed(GamepadKeys.Button.A) ) {
                fieldCentricOn = !fieldCentricOn; // toggle the value
            }

            if( fieldCentricOn ) {
                drive.driveFieldCentric(
                    gamepad.getLeftX(),
                    gamepad.getLeftY(),
                    gamepad.getRightX(),
                    revIMU.getRotation2d().getDegrees(),
                    true
                );
            }
            else {
                drive.driveRobotCentric(
                    gamepad.getLeftX(),
                    gamepad.getLeftY(),
                    gamepad.getRightX(),
                    true
                );
            }
        }
    }

    protected void initHardware() {
        drive = new MecanumDrive(
            new Motor(hardwareMap, RobotConfig.DRIVE_FRONT_LEFT, Motor.GoBILDA.RPM_312),
            new Motor(hardwareMap, RobotConfig.DRIVE_FRONT_RIGHT, Motor.GoBILDA.RPM_312),
            new Motor(hardwareMap, RobotConfig.DRIVE_BACK_LEFT, Motor.GoBILDA.RPM_312),
            new Motor(hardwareMap, RobotConfig.DRIVE_BACK_RIGHT, Motor.GoBILDA.RPM_312)
        );

        // TODO - how do we use new imu or make sure we use expansion hub?
        revIMU = new RevIMU(hardwareMap);
        revIMU.init();

        gamepad = new GamepadEx(gamepad1);
    }
}