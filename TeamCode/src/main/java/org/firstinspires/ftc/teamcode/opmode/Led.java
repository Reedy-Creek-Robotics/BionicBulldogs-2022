package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.LP55231;

@TeleOp(name = "led board")
public class Led extends LinearOpMode {
    public void runOpMode() {
        LP55231 myLP55231 = hardwareMap.get(LP55231.class,"LP55231"); // map hardware
        myLP55231.reset(); // required device reset
        myLP55231.setSingleColorValue(LP55231.Register.LED1_B,150);
        myLP55231.setSingleColorValue(LP55231.Register.LED2_R,13);
        myLP55231.setSingleColorValue(LP55231.Register.LED2_G,163);
        myLP55231.setSingleColorValue(LP55231.Register.LED2_B,58);
        myLP55231.setSingleColorValue(LP55231.Register.LED3_G,150);
    }
}
