package org.firstinspires.ftc.teamcode.modules;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;

public class Movement {
    //Ticks per revolution = 537.6(same for both)
    //wheel size is 100mm and circumference ~31.415 cm(regular)
    //wheel size is 96mm and circumference~30.15 cm(Strafer chassis)
    public static double TICKS_PER_CM = 17.83; // 17.83 tics/cm traveled(Strafer)
    public static double MOVE_CORRECTION = 0.955;
    public static double ROTATION_CORRECTION = 0.88; //(62/90);
    public static double STRAFE_CORRECTION = 1.1;
    public static double TURN_CONSTANT = 50.5d/90d; // distance per deg
    double slowdownOffset = 0.2;
    DcMotor frontLeft,backLeft,frontRight,backRight;
    LinearOpMode opMode;
    Telemetry telemetry;
    DistanceSensorModule distanceSensor;
    IMU imu;
    boolean useImu = false;
    double speed = .5;// default speed is always 50%
    Slide slide;

    public Movement(String fl, String fr, String bl, String br, LinearOpMode op, boolean _useImu){
        opMode = op;
        telemetry = op.telemetry;
        useImu = _useImu;
        if(useImu){
            imu = new IMU(op);
        }
        frontLeft = opMode.hardwareMap.dcMotor.get(fl);
        frontRight = opMode.hardwareMap.dcMotor.get(fr);
        backLeft = opMode.hardwareMap.dcMotor.get(bl);
        backRight = opMode.hardwareMap.dcMotor.get(br);

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        //distanceSensor = new DistanceSensorModule("distanceSensor",opMode);


        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void setSettings(double movea, double strafe, double rotate){
        MOVE_CORRECTION = movea;
        STRAFE_CORRECTION = strafe;
        ROTATION_CORRECTION = rotate;
    }
    public void reverse(){
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void resetAngle(){
        if(useImu) {
            imu = new IMU(opMode);
        }
    }
    public IMU getImu(){
        if(useImu) {
            return imu;
        }
        return null;
    }

    public double getAngle(AngleUnit angleUnit){
        if(useImu) {
            return imu.getHeading(angleUnit);
        }
        return 0;
    }
    public double getAngle(){
        return getAngle(AngleUnit.RADIANS);
    }
    public void addSlide(Slide _slide){
        slide = _slide;
    }

    public void telOpRunMode(){
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void mecanumTeleOpUpdate(double wheelsPowerFactor,boolean fieldRelative){
        //THESE ARE THE MOVEMENT FUNCTIONS
        double angle = 0;
        if(useImu) {
            angle = imu.getHeading(AngleUnit.RADIANS);
        }
        double output_x = Math.pow(opMode.gamepad1.left_stick_x, 3);
        double output_y = Math.pow(opMode.gamepad1.left_stick_y, 3);
        double output_xRight = Math.pow(-opMode.gamepad1.right_stick_x, 3);
        double drive = -output_y * wheelsPowerFactor;//vertical movement = the left stick on controller one(moving on the y-axis)
        double strafe = output_x * wheelsPowerFactor;//Strafing = the left stick on controller 1(moving on the x-axis)
        double rotate = -output_xRight * (wheelsPowerFactor/0.5);
        double field_drive = -(drive * Math.cos(angle) - strafe * Math.sin(angle));
        double field_strafe = -(drive * Math.sin(angle) + strafe * Math.cos(angle));
        if(fieldRelative) {
            frontLeft.setPower(field_drive + field_strafe + rotate);
            backLeft.setPower(field_drive - field_strafe + rotate);
            frontRight.setPower(field_drive - field_strafe - rotate);
            backRight.setPower(field_drive + field_strafe - rotate);
        }else{
            frontLeft.setPower(drive + strafe + rotate);
            backLeft.setPower(drive - strafe + rotate);
            frontRight.setPower(drive - strafe - rotate);
            backRight.setPower(drive + strafe - rotate);
        }
    }

    public void tankTeleOpUpdate(double wheelsPowerFactor){
        double output_x = Math.pow(opMode.gamepad1.left_stick_x, 3);
        double output_y = Math.pow(opMode.gamepad1.left_stick_y, 3);
        double drive = -output_y * wheelsPowerFactor;
        double turn = output_x * wheelsPowerFactor;
        double leftPower = Range.clip(drive + turn, -1.0, 1.0) ;
        double rightPower = Range.clip(drive - turn, -1.0, 1.0) ;
        //THESE ARE THE MOVEMENT FUNCTIONS

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);
        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);

        telemetry.addData("left power",leftPower);
        telemetry.addData("right power", rightPower);
    }
    public void setSpeed(double s) {
        speed = s;
    }

    public void moveStraight(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * MOVE_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * MOVE_CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * MOVE_CORRECTION));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * MOVE_CORRECTION));
        move();
    }

    public void strafeLeft(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * STRAFE_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * STRAFE_CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * STRAFE_CORRECTION));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM * STRAFE_CORRECTION));
        move();
    }
    public void strafeRight(double distance) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * STRAFE_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * STRAFE_CORRECTION));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM * STRAFE_CORRECTION));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * STRAFE_CORRECTION));
        move();
    }
    public void turnLeft(double degrees) {
        backLeft.setTargetPosition((int) (-(degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (-(degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) ((degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) ((degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        move();
    }
    public void turnRight(double degrees) {
        backLeft.setTargetPosition((int) ((degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) ((degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (-(degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (-(degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        move();
    }
    public void moveUntilDistance(double distance){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        time.reset();
        while (opMode.opModeIsActive()){//distanceSensor.getDistance() >= distance &&
            telemetry.addData("distance",distanceSensor.getDistance());
            if(time.seconds() > 5){
                telemetry.addLine("timedOut");
                break;
            }
            telemetry.update();
        }
        telemetry.addData("distance",distanceSensor.getDistance());
        telemetry.addData("time left", time.seconds());
        telemetry.addLine("reached Distance");
        telemetry.update();

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }
    public void moveForTime(double timeLeft){
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        time.reset();
        while (time.seconds() < timeLeft){
            telemetry.addData("time", time.seconds());
            telemetry.update();
        }
        telemetry.addData("time left", time.seconds());
        telemetry.addLine("reached time");
        telemetry.update();

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void moveUntilTouch(Slide slide){
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        time.reset();
        while (!slide.touchSensorPressed()){
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    void move(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);

        while (!opMode.isStopRequested() && (backRight.isBusy() && backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy())){
            double fl = frontLeft.getCurrentPosition() / frontLeft.getTargetPosition() == 0 ? frontLeft.getCurrentPosition() : frontLeft.getTargetPosition();
            double fr = frontRight.getCurrentPosition() / frontRight.getTargetPosition() == 0 ? frontRight.getCurrentPosition() : frontRight.getTargetPosition();
            double bl = backLeft.getCurrentPosition() / backLeft.getTargetPosition() == 0 ? backLeft.getCurrentPosition() : backLeft.getTargetPosition();
            double br = backRight.getCurrentPosition() / backRight.getTargetPosition() == 0 ? backRight.getCurrentPosition() : backRight.getTargetPosition();
            double a = (fl+fr+bl+br)/4;
            double newSpeed = ((a > 0.5 + slowdownOffset / 2) ? (-2 * a + 2 + slowdownOffset / 2) : 1) * speed;
            frontLeft.setPower(speed);
            backLeft.setPower(speed);
            frontRight.setPower(speed);
            backRight.setPower(speed);
            telemetry.addData("power", newSpeed);
            telemetry.addData("a", a);
            telemetry.addData("fl", fl);
            telemetry.addData("fr", fr);
            telemetry.addData("bl", bl);
            telemetry.addData("br", br);
            telemetry.update();
        }
        delay(0.25);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);

    }
    public void telemetryUpdate(){
        telemetry.addData("encoder-bck-left", backLeft.getCurrentPosition() + " power= " + backLeft.getPower() +  "  busy=" + backLeft.isBusy());
        telemetry.addData("encoder-bck-right", backRight.getCurrentPosition() + " power= " + backRight.getPower() +  "  busy=" + backRight.isBusy());
        telemetry.addData("encoder-fwd-left", frontLeft.getCurrentPosition() + " power= " + frontLeft.getPower() +  "  busy=" +frontLeft.isBusy());
        telemetry.addData("encoder-fwd-right", frontRight.getCurrentPosition() + " power= " + frontRight.getPower() +  "  busy=" + frontRight.isBusy());

        telemetry.update();
    }

    public double inToCm(double in){
        return in*2.54;
    }
    public void delay(double time){
        ElapsedTime t = new ElapsedTime();
        t.reset();
        while(t.seconds() < time && opMode.opModeIsActive());
    }
    public void delayUpdateMovement(double time){
        ElapsedTime t = new ElapsedTime();
        t.reset();
        while(t.seconds() < time && opMode.opModeIsActive()){
            mecanumTeleOpUpdate(0.8, false);
        }
    }
    public void move(double forward, double _strafe, double _rotate){
        //THESE ARE THE MOVEMENT FUNCTIONS
        double angle = 0;
        if(useImu) {
            angle = imu.getHeading(AngleUnit.RADIANS);
        }
        double output_x = Math.pow(forward, 3);
        double output_y = Math.pow(_strafe, 3);
        double output_xRight = Math.pow(_rotate, 3);
        double drive = -output_y * speed;//vertical movement = the left stick on controller one(moving on the y-axis)
        double strafe = output_x * speed;//Strafing = the left stick on controller 1(moving on the x-axis)
        double rotate = output_xRight * (speed - 0.4);
        double field_drive = -(drive * Math.cos(angle) - strafe * Math.sin(angle));
        double field_strafe = -(drive * Math.sin(angle) + strafe * Math.cos(angle));
        frontLeft.setPower(field_drive + field_strafe + rotate);
        backLeft.setPower(field_drive - field_strafe + rotate);
        frontRight.setPower(field_drive - field_strafe - rotate);
        backRight.setPower(field_drive + field_strafe - rotate);
    }
    public void moveAndTurn(double forward, double strafe, double rotate){
        //rotate
        backLeft.setTargetPosition((int) ((rotate * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        frontLeft.setTargetPosition((int) ((rotate * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (-(rotate * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (-(rotate * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));
        //forward
        backLeft.setTargetPosition((int) (forward * TICKS_PER_CM * MOVE_CORRECTION + backLeft.getTargetPosition()));
        frontLeft.setTargetPosition((int) (forward * TICKS_PER_CM * MOVE_CORRECTION + frontLeft.getTargetPosition()));
        frontRight.setTargetPosition((int) (forward * TICKS_PER_CM * MOVE_CORRECTION + frontRight.getTargetPosition()));
        backRight.setTargetPosition((int) (forward * TICKS_PER_CM * MOVE_CORRECTION + backRight.getTargetPosition()));
        //strafe
        backLeft.setTargetPosition((int) (-strafe * TICKS_PER_CM * STRAFE_CORRECTION + backLeft.getTargetPosition()));
        frontLeft.setTargetPosition((int) (strafe * TICKS_PER_CM * STRAFE_CORRECTION + frontLeft.getTargetPosition()));
        frontRight.setTargetPosition((int) (-strafe * TICKS_PER_CM * STRAFE_CORRECTION + frontRight.getTargetPosition()));
        backRight.setTargetPosition((int) (strafe * TICKS_PER_CM * STRAFE_CORRECTION + backRight.getTargetPosition()));
        //move
        move();
    }
}