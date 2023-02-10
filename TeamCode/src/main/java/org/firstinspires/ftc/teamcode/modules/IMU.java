package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class IMU {

    public BHI260IMU imuBH;
    Orientation lastAngles = new Orientation();
    double globalAngle;
    LinearOpMode op;
    /**************************************
     * Initialization and Basic Heading Functions
     **************************************/
    public IMU(LinearOpMode opmode) {
        op = opmode;

        imuBH = op.hardwareMap.tryGet(BHI260IMU.class, "imu");
        BHI260IMU.Prameters parameters = new BHI260IMU.Parameters();
        parameters.mode = BHI260IMU.SensorMode.IMU;
        parameters.angleUnit = BHI260IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BHI260IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imuBH.initialize(parameters);


        // make sure the imu gyro is calibrated before continuing.
        while (!op.isStopRequested() && !imuBN.isGyroCalibrated()) {
            op.sleep(50);
            op.idle();
            op.telemetry.clear();
            op.telemetry.addLine("calibrating...");
            op.telemetry.update();
        }

        op.telemetry.clear();
        op.telemetry.addData("imu calib status", imuBN.getCalibrationStatus().toString());
        op.telemetry.update();
    }

    public double getHeading(AngleUnit angleUnit) {
        Orientation angles = imuBN.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleUnit);
        return angles.firstAngle;
    }

    /**************************************
     * Advanced Angle and Rotate Functions
     **************************************/
    public void resetAngle() {
        lastAngles = imuBN.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }
    public double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imuBN.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }
}