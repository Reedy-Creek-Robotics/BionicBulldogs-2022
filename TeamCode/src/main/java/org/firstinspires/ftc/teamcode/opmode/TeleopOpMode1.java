package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Teleop OpMode demonstrating how to
 *   - retrieve a motor from the hardware map
 *   - use the gamepad to stop and start the motor
 */
public class TeleopOpMode1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // INIT
        // all code between here and WAIT runs when the INIT button is pressed on the driver station
        // this is where you initialize all the hardware and code for your program
        // the robot should NOT move in this part of the program (its a penalty)

        // hardwareMap is an inherited object that contains all the config loaded from
        // your control or expansion hub, it uses the names EXACTLY as you entered
        // for this to work you need a config with a Motor named 'motor1'
        // you are retrieving an 'instance' of the motor to send commands
        DcMotor motor = hardwareMap.dcMotor.get("motor1");

        // WAIT
        // the program pauses here until the START button is pressed on the driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();

        // START / RUN
        // all code after here runs when the START button is pressed on the driver station
        // this is where all your code to move the robot goes
        // once all instructions are executed the program will exit
        telemetry.addLine("PROGRAM STARTED");
        while( !isStopRequested() ) {
            // this is your EVENT loop (like a video game loop)
            // these instructions will be executed over and over until STOP is pressed on the driver station
            // you will listen for gamepad and other input commands in this loop
            telemetry.addLine("EVENT LOOP");

            // start the motor if the 'a' button is pressed on the gamepad
            if(gamepad1.a) {
                motor.setPower(0.5);
            }

            // stop the motor if the 'b' button is pressed on the gamepad
            if(gamepad1.b) {
                motor.setPower(0);
            }
        }
    }
}
