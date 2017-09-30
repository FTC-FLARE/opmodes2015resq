package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DexMDumper {

    private Servo servoDumper;

    public DexMDumper(HardwareMap hwMap) {
        servoDumper = hwMap.servo.get("servoDumper");
    } // DexMHardware

    void checkForMove(Gamepad gamepad) {
        if (gamepad.dpad_up) {
            trigger();
        } else {
            goBack();
        }
    }

    void trigger() {
        if (servoDumper != null) {
            servoDumper.setPosition(1.0);
        }
    } // trigger

    void goBack() {
        if (servoDumper != null) {
            servoDumper.setPosition(0.0);
        }
    } // trigger


    double getPosition() {
        double returnValue = 0.0;

        if (servoDumper != null) {
            returnValue = servoDumper.getPosition();
        }

        return returnValue;
    } // getPosition
} // DexMHardware
