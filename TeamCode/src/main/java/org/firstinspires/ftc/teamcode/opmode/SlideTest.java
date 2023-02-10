package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Slide;
import org.firstinspires.ftc.teamcode.modules.SpinMotor;
@TeleOp(name = "slideTest")
public class SlideTest extends LinearOpMode {
    public void runOpMode(){
        SpinMotor slideL = new SpinMotor("slideL", true, this);
        SpinMotor slideR = new SpinMotor("slideR", true, this);
        ElapsedTime t = new ElapsedTime();
        t.reset();
        Slide.height height = Slide.height.ground;
        Slide slide = new Slide("slideL", "slideR", "leftWheel", "rightWheel", "open", "turret", "touchSensor", this);
        waitForStart();
        while (opModeIsActive()){
            if(gamepad1.dpad_up && t.seconds() >= 0.5){
                switch (height){
                    case ground:
                        height = Slide.height.low;
                        break;
                    case low:
                        height = Slide.height.med;
                        break;
                    case med:
                        height = Slide.height.high;
                        break;
                    case high:
                        height = Slide.height.ground;
                        break;
                }
                t.reset();
            }
            if(gamepad1.dpad_down){
                slide.goToPos(height);
            }
            if (gamepad1.a) {
                slide.startIntake();
            }
            if (gamepad1.b) {
                slide.stopIntake();
            }
            if(gamepad1.y){
                slide.startOutake();
            }
            if (gamepad1.x) {
                slide.open();
            }else if (gamepad1.y) {
                slide.close();
            }
            telemetry.addData("left",slideL.getPosition());
            telemetry.addData("right",slideR.getPosition());
            telemetry.addData("avg", (Math.abs(slideR.getPosition()) + Math.abs(slideL.getPosition())/2));
            telemetry.addData("leftSlide", slide.getSlidePosition(0));
            telemetry.addData("rightSlide", slide.getSlidePosition(1));
            telemetry.addData("turret", slide.getTurretPosition());
            telemetry.addData("pos", height);
            telemetry.update();
        }
    }
}
