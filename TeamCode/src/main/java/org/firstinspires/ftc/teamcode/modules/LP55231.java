package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

/*
2022-11-06: First implementation by Coach Wright of 19410 Entropic Fluctuations

Dependencies: I2C Drivers from OpenFTC, https://github.com/OpenFTC/I2C-Drivers

NOTE: Some soldering required to use this board.

This code implements the I2C interface for the LP55231 Breakout Board from SparkFun (PN BOB-13884).
This board was selected since it provided 3 LEDs to control and does not have a built in
microcontroller. The internal driver 'engine' has to be programmed from scratch on boot. This code
does not implement the engine. The board has far more capabilities than what is implemented in this
library. This library simply controls the 3 RGB color channels for each of the 3 LEDs.

Only 4 of the 7 input wires are need for this implementation:
VCC,GND,SDA,SCL
See REV Control Hub documentation for I2C wiring connections for the 4 wires. The REV Control Hub
needs a JST PH 4-Pin Sensor Cable. You can solder these wires directly to the board or use an
adapter cable.
NOTE: The EN, INT, and TRIG will be unused, but are necessary should the user try to enact the built
in engine protocols.

Full documentation can be found here:
https://learn.sparkfun.com/tutorials/lp55231-breakout-board-hookup-guide

Shamelessly lifted I2C implementation from the following sites.
https://github.com/FIRST-Tech-Challenge/ftcrobotcontroller/wiki/Writing-an-I2C-Driver
https://github.com/OpenFTC/I2C-Drivers/blob/master/i2cdrivers/src/main/java/org/openftc/i2cdrivers/QwiicLEDStick.java

Example implementation:
   LP55231 myLP55231;                                           // create object
   myLP55231 = hardwareMap.get(LP55231.class,"LP55231");        // map hardware
   myLP55231.reset();                                           // required device reset
   myLP55231.setSingleColorValue(LP55231.Register.LED1_B,150);  // example LED color set
*/

@SuppressWarnings({"WeakerAccess", "unused"}) // Ignore access and unused warnings
@I2cDeviceType
@DeviceProperties(name = "LP55231 LED Driver Board", description = "3 LED Board from Sparkfun", xmlTag = "LP55231")

public class LP55231 extends I2cDeviceSynchDevice<I2cDeviceSynch>
{
    // Write a value to a register. Used in other methods. Can be used to write to various registers
    // (including the PWM color channels) directly.
    public void writeToRegister(short valueToSend, Register registerToWrite)
    {
        byte[] data = new byte[1];
        data[0] = (byte) valueToSend;
        writeI2C(registerToWrite,data);
    }

    // Single control of an LED's RGB channel. Use register enums with designation:
    // LEDn_c where 'n' is {1,2,3} and 'c' is {R,G,B}
    // Example: myLP55231.setSingleColorValue(LP55231.Register.LED1_B,150);
    // NOTE: This can be accomplished by using the REG_Dn_PWM registers as well.
    public void setSingleColorValue(Register regToSet, int brightness) {
        byte[] onVal = new byte[1];
        onVal[0] = (byte) brightness;
        writeI2C(regToSet,onVal);
    }

    public void turnAllOff() {
        byte[] offVal = new byte[1];
        offVal[0] = (byte) 0;

        writeI2C(Register.REG_D1_PWM,offVal);
        writeI2C(Register.REG_D2_PWM,offVal);
        writeI2C(Register.REG_D3_PWM,offVal);
        writeI2C(Register.REG_D4_PWM,offVal);
        writeI2C(Register.REG_D5_PWM,offVal);
        writeI2C(Register.REG_D6_PWM,offVal);
        writeI2C(Register.REG_D7_PWM,offVal);
        writeI2C(Register.REG_D8_PWM,offVal);
        writeI2C(Register.REG_D9_PWM,offVal);
    }

    // The following methods provide short cuts for:
    // Turning on all LEDs to the same color (or turning all off)
    // Example: myLP55231.setAllColor(LP55231.colorList.OFF,0);
    // Turning on single LED to red, green, or blue and turn off the other 2
    // Example: myLP55231.setLED1Color(LP55231.colorList.RED,255);
    public enum colorList {BLUE,GREEN,RED,OFF;}

    public void setAllColor(colorList color,int brightness){
        // REG_D1_PWM --> LED1, Green
        // REG_D2_PWM --> LED1, Blue
        // REG_D7_PWM --> LED1, Red
        // REG_D3_PWM --> LED2, Green
        // REG_D4_PWM --> LED2, Blue
        // REG_D8_PWM --> LED2, Red
        // REG_D5_PWM --> LED3, Green
        // REG_D6_PWM --> LED3, Blue
        // REG_D9_PWM --> LED3, Red

        byte[] onVal = new byte[1];
        onVal[0] = (byte) brightness;

        byte[] offVal = new byte[1];
        offVal[0] = (byte) 0;

        turnAllOff();

        switch(color){
            case BLUE:
                writeI2C(Register.REG_D2_PWM,onVal);
                writeI2C(Register.REG_D4_PWM,onVal);
                writeI2C(Register.REG_D6_PWM,onVal);
                break;
            case GREEN:
                writeI2C(Register.REG_D1_PWM,onVal);
                writeI2C(Register.REG_D3_PWM,onVal);
                writeI2C(Register.REG_D5_PWM,onVal);
                break;
            case RED:
                writeI2C(Register.REG_D7_PWM,onVal);
                writeI2C(Register.REG_D8_PWM,onVal);
                writeI2C(Register.REG_D9_PWM,onVal);
                break;
            case OFF:
                break;
        }
    }

    public void setLED1Color(colorList color,int brightness){
        // REG_D1_PWM --> LED1, Green
        // REG_D2_PWM --> LED1, Blue
        // REG_D7_PWM --> LED1, Red

        byte[] onVal = new byte[1];
        onVal[0] = (byte) brightness;

        byte[] offVal = new byte[1];
        offVal[0] = (byte) 0;

        turnAllOff();

        switch(color){
            case BLUE:
                writeI2C(Register.REG_D2_PWM,onVal);
                break;
            case GREEN:
                writeI2C(Register.REG_D1_PWM,onVal);
                break;
            case RED:
                writeI2C(Register.REG_D7_PWM,onVal);
                break;
            case OFF:
                break;
        }
    }
    public void setLED2Color(colorList color,int brightness){
        // REG_D3_PWM --> LED2, Green
        // REG_D4_PWM --> LED2, Blue
        // REG_D8_PWM --> LED2, Red

        byte[] onVal = new byte[1];
        onVal[0] = (byte) brightness;

        byte[] offVal = new byte[1];
        offVal[0] = (byte) 0;

        turnAllOff();

        switch(color){
            case BLUE:
                writeI2C(Register.REG_D4_PWM,onVal);
                break;
            case GREEN:
                writeI2C(Register.REG_D3_PWM,onVal);
                break;
            case RED:
                writeI2C(Register.REG_D8_PWM,onVal);
                break;
            case OFF:
                break;
        }
    }

    public void setLED3Color(colorList color,int brightness){
        // REG_D5_PWM --> LED3, Green
        // REG_D6_PWM --> LED3, Blue
        // REG_D9_PWM --> LED3, Red

        byte[] onVal = new byte[1];
        onVal[0] = (byte) brightness;

        byte[] offVal = new byte[1];
        offVal[0] = (byte) 0;

        turnAllOff();

        switch(color){
            case BLUE:
                writeI2C(Register.REG_D6_PWM,onVal);
                break;
            case GREEN:
                writeI2C(Register.REG_D5_PWM,onVal);
                break;
            case RED:
                writeI2C(Register.REG_D9_PWM,onVal);
                break;
            case OFF:
                break;
        }
    }

    // Specific write to register call.
    private void writeI2C(LP55231.Register cmd, byte[] data) {
        deviceClient.write(cmd.bVal, data, I2cWaitControl.WRITTEN);
    }

    // Documentation on SparkFun site indicates that to initialize the device, a reset followed by
    // the two listed commands is needed. See implementation in arduino libraries for further details.
    public void reset()
    {
        int valueToRight;
        byte[] data = new byte[1];

        // Reset command
        valueToRight = 0xff;
        data[0] = (byte) valueToRight;
        writeI2C(Register.REG_RESET, data);

        valueToRight = 0x40;
        data[0] = (byte) valueToRight;
        writeI2C(Register.REG_CNTRL1, data);

        valueToRight = 0x53;
        data[0] = (byte) valueToRight;
        writeI2C(Register.REG_MISC, data);

    }

    // All the registers copied from the SparkFun Arduino library. Most of these are not implemented
    // by this code. See TI LP55321 data sheet for details:
    // https://cdn.sparkfun.com/datasheets/BreakoutBoards/lp55231.pdf
    public enum Register
    {
        // register stuff
        REG_CNTRL1(0x00),
        REG_CNTRL2(0x01),
        REG_RATIO_MSB(0x02),
        REG_RATIO_LSB(0x03),
        REG_OUTPUT_ONOFF_MSB(0x04),
        REG_OUTPUT_ONOFF_LSB(0x05),

        // Per LED control channels - fader channel assig, log dimming enable, temperature compensation
        REG_D1_CTRL(0x06),
        REG_D2_CTRL(0x07),
        REG_D3_CTRL(0x08),
        REG_D4_CTRL(0x09),
        REG_D5_CTRL(0x0a),
        REG_D6_CTRL(0x0b),
        REG_D7_CTRL(0x0c),
        REG_D8_CTRL(0x0d),
        REG_D9_CTRL(0x0e),

        // Direct PWM control registers
        REG_D1_PWM(0x16),
        REG_D2_PWM(0x17),
        REG_D3_PWM(0x18),
        REG_D4_PWM(0x19),
        REG_D5_PWM(0x1a),
        REG_D6_PWM(0x1b),
        REG_D7_PWM(0x1c),
        REG_D8_PWM(0x1d),
        REG_D9_PWM(0x1e),

        // Repeat of above block, except with more user friendly names.
        LED1_G(0x16),
        LED1_B(0x17),
        LED2_G(0x18),
        LED2_B(0x19),
        LED3_G(0x1a),
        LED3_B(0x1b),
        LED1_R(0x1c),
        LED2_R(0x1d),
        LED3_R(0x1e),

        // Drive cuttent registers
        REG_D1_I_CTL(0x26),
        REG_D2_I_CTL(0x27),
        REG_D3_I_CTL(0x28),
        REG_D4_I_CTL(0x29),
        REG_D5_I_CTL(0x2a),
        REG_D6_I_CTL(0x2b),
        REG_D7_I_CTL(0x2c),
        REG_D8_I_CTL(0x2d),
        REG_D9_I_CTL(0x2e),

        REG_MISC(0x36),
        REG_PC1(0x37),
        REG_PC2(0x38),
        REG_PC3(0x39),
        REG_STATUS_IRQ(0x3A),
        REG_RESET(0x3D),

        REG_PROG1_START(0x4C),
        REG_PROG2_START(0x4D),
        REG_PROG3_START(0x4E),
        REG_PROG_PAGE_SEL(0x4f),

        REG_ENG1_MAP_MSB(0x70),
        REG_ENG1_MAP_LSB(0x71),
        REG_ENG2_MAP_MSB(0x72),
        REG_ENG2_MAP_LSB(0x73),
        REG_ENG3_MAP_MSB(0x74),
        REG_ENG3_MAP_LSB(0x75);

        public int bVal;

        Register(int bVal)
        {
            this.bVal = bVal;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Construction and Initialization
    // The following methods were copied and modified from the two links in the header for I2C
    // drivers. Changes include removing extraneous commands not relevant to this chip, modifying
    // the chip address to x32, changing Manufacturer and Device Name, and calling reset() during
    // the initialize method.
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x32);

    public LP55231(I2cDeviceSynch deviceClient)
    {
        super(deviceClient, true);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false); // Deals with USB cables getting unplugged
        // Sensor starts off disengaged so we can change things like I2C address. Need to engage
        this.deviceClient.engage();
    }

    @Override
    protected boolean doInitialize() {
        reset();
        return true;
    }

    @Override
    public Manufacturer getManufacturer()
    {
        return Manufacturer.Other;
    }

    @Override
    public String getDeviceName()
    {
        return "SparkFun LP55231";
    }
}


