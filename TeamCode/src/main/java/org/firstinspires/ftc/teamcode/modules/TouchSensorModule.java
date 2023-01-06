package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class TouchSensorModule {
    LinearOpMode opmode;
    boolean hasSensor = false;
    TouchSensor sensor;
    public TouchSensorModule(String name, LinearOpMode op){
        opmode = op;
        if(op.hardwareMap.touchSensor.contains(name)){
            hasSensor = true;
            sensor = op.hardwareMap.touchSensor.get(name);
        }
    }
    public boolean getState(){
        if(hasSensor){
            return sensor.isPressed();
        }
        return false;
    }
}
