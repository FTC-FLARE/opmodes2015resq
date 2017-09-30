package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class DexMHitmen {

    public Servo servoHitmanLeft;
    public Servo servoHitmanRight;


    public DexMHitmen(HardwareMap hwMap) {
        double hitman_left_position = 0.375;
        double hitman_right_position = 0.4;

        servoHitmanLeft = hwMap.servo.get("servoHitmanLeft");
        servoHitmanLeft.setPosition(hitman_left_position);

        servoHitmanRight = hwMap.servo.get("servoHitmanRight");
        servoHitmanRight.setPosition(hitman_right_position);
    } // DexMHardware

    double getLeft() {
        double returnValue = 0.0;

        if (servoHitmanLeft != null) {
            returnValue = servoHitmanLeft.getPosition();
        }

        return returnValue;

    } // a_hitman_left_position

    double getRight() {
        double returnValue = 0.0;

        if (servoHitmanRight != null) {
            returnValue = servoHitmanRight.getPosition();
        }

        return returnValue;

    } // a_hitman_right_position

    void triggerLeft() {
        servoHitmanLeft.setPosition(1.0);
    }

    void triggerRight() {
        servoHitmanRight.setPosition(1.0);
    }

    void returnLeft() {
        servoHitmanLeft.setPosition(.375);
    }

    void returnRight() {
        servoHitmanRight.setPosition(.4);
    }

    void checkForMove(Gamepad gamepad) {
        // Check right hitman
        if (gamepad.right_bumper)
            triggerRight();
        else if (gamepad.right_trigger > 0.0)
            returnRight();

        // Check left hitman
        if (gamepad.left_bumper)
            triggerLeft();
        else if (gamepad.left_trigger > 0.0)
            returnLeft();

    }

} // DexMHardware
