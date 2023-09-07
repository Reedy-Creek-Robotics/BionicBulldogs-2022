package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Autonomous OpMode demonstrating how to
 *   - retrieve components to control from the hardware map
 *   - control a motor
 */
public class AutonomousOpMode1 extends LinearOpMode {

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

        // turn the motor on, giving it a power to run at
        // power values are from 0 to 1 and represent a percentage of total power
        motor.setPower(.5);

        // sleep/wait for 5s (otherwise the program would exit immediately)
        sleep(5000);

        // turn the motor off
        motor.setPower(0);
    }
}
