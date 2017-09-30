package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class DexMArm {

    private DcMotor motorArm;
    private DcMotor motorArmLift;

    public DexMArm(HardwareMap hwMap) {
        motorArm = hwMap.dcMotor.get("left_drive");
        motorArm.setDirection(DcMotor.Direction.REVERSE);

        motorArm = hwMap.dcMotor.get("motorArm");
        motorArmLift = hwMap.dcMotor.get("motorArmLift");

    } // DexMHardware

    void checkForMove(Gamepad gamepad){
        checkInOut(gamepad);
        checkLift(gamepad);
    }

    void checkInOut(Gamepad gamepad){
        if (gamepad.right_bumper)
            moveOut();
        else if (gamepad.right_trigger > 0.0)
            moveIn();
        else
            stopInOut();
    }

    void checkLift(Gamepad gamepad){
        if (gamepad.left_bumper)
            liftUp();
        else if (gamepad.left_trigger > 0.0)
            liftDown();
        else
            liftStop();
    }

    double getPower() {
        double returnValue = 0.0;

        if (motorArm != null)
            returnValue = motorArm.getPower();
        return returnValue;
    } // getPower

    void setPower(double arm_power) {
        if (motorArm != null)
            motorArm.setPower(arm_power);
    } // setPower

    void setLiftPower(double arm_power) {
        if (motorArmLift != null)
            motorArmLift.setPower(arm_power);
    } // setLiftPower

    void moveOut() {
        setPower(1.0);
    }
    void moveIn() {
        setPower(-1.0);
    }
    void stopInOut() {
        setPower(0.0);
    }
    void liftUp(){
        setLiftPower(-1.0);
    }
    void liftDown(){
        setLiftPower(1.0);
    }
    void liftStop(){
        setLiftPower(0.0);
    }

} // DexMHardware
