package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DexMHook {

    private DcMotor motorHook;

    public DexMHook(HardwareMap hwMap) {
        motorHook = hwMap.dcMotor.get("motorHook");

    } // DexMHardware

    public void checkForMove(Gamepad gamepad) {

        if (gamepad.b)
            down();
        else if (gamepad.y)
            up();
        else
            stop();
    }

    void setPower(double power) {
        if (motorHook != null)
            motorHook.setPower(power);
    } // setPower

    void down() {
        setPower(.3);
    }

    void up() {
        setPower(-.3);
    }

    void stop() {
        setPower(0.0);
    }
} // DexMHardware
