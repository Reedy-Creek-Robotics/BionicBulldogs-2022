package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensorModule {
    LinearOpMode opMode;
    DistanceSensor sensor;
    public DistanceSensorModule(String distanceSensor, LinearOpMode op){
        sensor = op.hardwareMap.get(DistanceSensor.class, distanceSensor);
        opMode = op;
    }
    public double getDistance(){
        return sensor.getDistance(DistanceUnit.CM);
    }
}