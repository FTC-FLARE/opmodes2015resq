package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class DexMDriveTrain {

    DexMdtGyro gyro;
    public DcMotor motorLeftDrive;
    public DcMotor motorRightDrive;

    final static int TICKS_PER_ROTATION = 1440;
    final static double TIRE_DIAMETER = 2.8;
    final static double TIRE_CIRCUMFERENCE = TIRE_DIAMETER * Math.PI;
    final static double GEAR_RATIO = 1;
    final static double WHEEL_BASE = 12.0;
    final static double ROBOT_PIVOT_CIRCUMFERENCE = WHEEL_BASE * 2 * Math.PI;
    final static double ROBOT_360_SHAFT_ROTATION = ROBOT_PIVOT_CIRCUMFERENCE / TIRE_CIRCUMFERENCE;

    public DexMDriveTrain(HardwareMap hwMap) {
        gyro = new DexMdtGyro(hwMap);

        motorLeftDrive = hwMap.dcMotor.get("left_drive");
        motorLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        motorRightDrive = hwMap.dcMotor.get("right_drive");
    } // DexMHardware

    // Scale the joystick input using a nonlinear algorithm.
    double scalePower(double inPower) {
        double scale = 0;
        double power = Range.clip(inPower, -1.0, 1.0);
        double[] array =
                {0.00, 0.05, 0.09, 0.10, 0.12
                        , 0.15, 0.18, 0.24, 0.30, 0.36
                        , 0.43, 0.50, 0.60, 0.72, 0.85
                        , 1.00, 1.00};
        int index = (int) (power * 16.0);

        if (index < 0)
            index = -index;
        else if (index > 16)
            index = 16;

        if (power < 0)
            scale = -array[index];
        else
            scale = array[index];
        return scale;

    } // scalePower

    double getRightPower() {
        double returnValue = 0.0;

        if (motorRightDrive != null)
            returnValue = motorRightDrive.getPower();
        return returnValue;
    } // getRightPower

    double getLeftPower() {
        double returnValue = 0.0;

        if (motorLeftDrive != null)
            returnValue = motorLeftDrive.getPower();
        return returnValue;
    } // getLeftPower

    void setPower(double leftPower, double rightPower) {
        if (motorLeftDrive != null)
            motorLeftDrive.setPower(leftPower);
        if (motorRightDrive != null)
            motorRightDrive.setPower(rightPower);
    } // setPower

    public void runLeftUsingEncoder() {
        if (motorLeftDrive != null)
            motorLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    } // runLeftUsingEncoder

    public void runRightUsingEncoder() {
        if (motorRightDrive != null)
            motorRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    } // runRightUsingEncoder

    public void runUsingEncoders() {
        runLeftUsingEncoder();
        runRightUsingEncoder();
    } // runUsingEncoders

    public void runLeftWithoutEncoder() {
        if (motorLeftDrive != null) {
            if (motorLeftDrive.getMode() == DcMotor.RunMode.STOP_AND_RESET_ENCODER)
                motorLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    } // runLeftWithoutEncoder

    public void runRightWithoutEncoder() {
        if (motorRightDrive != null) {
            if (motorRightDrive.getMode() == DcMotor.RunMode.STOP_AND_RESET_ENCODER)
                motorRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    } // runRightWithoutEncoder

    public void runWithoutEncoders() {
        runLeftWithoutEncoder();
        runRightWithoutEncoder();
    } // runWithoutEncoders

    public void resetLeftEncoder() {
        if (motorLeftDrive != null)
            motorLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    } // resetLeftEncoder

    public void resetRightEncoder() {
        if (motorRightDrive != null)
            motorRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    } // resetRightEncoder

    public void resetEncoders() {
        resetLeftEncoder();
        resetRightEncoder();
    } // resetEncoders

    int getLeftEncoderCount() {
        int returnValue = 0;

        if (motorLeftDrive != null)
            returnValue = motorLeftDrive.getCurrentPosition();
        return returnValue;
    } // getLeftEncoderCount

    int getRightEncoderCount() {
        int returnValue = 0;

        if (motorRightDrive != null)
            returnValue = motorRightDrive.getCurrentPosition();
        return returnValue;
    } // getRightEncoderCount

    boolean leftHasReached(double p_count) {
        boolean returnValue = false;

        if (motorLeftDrive != null) {
            if (Math.abs(motorLeftDrive.getCurrentPosition()) > p_count)
                returnValue = true;
        }
        return returnValue;
    } // leftHasReached

    boolean rightHasReached(double p_count) {
        boolean returnValue = false;

        if (motorRightDrive != null) {
            if (Math.abs(motorRightDrive.getCurrentPosition()) > p_count)
                returnValue = true;
        }
        return returnValue;
    } // rightHasReached

    boolean encodersHaveReached(double leftTarget, double rightTarget) {
        boolean returnValue = false;

        if (leftHasReached(leftTarget) && rightHasReached(rightTarget))
            returnValue = true;
        return returnValue;
    } // encodersHaveReached

    boolean driveUsingEncoders(double leftPower, double rightPower, double leftCount, double rightCount) {
        boolean returnValue = false;

        runUsingEncoders();
        setPower(leftPower, rightPower);

        if (encodersHaveReached(leftCount, rightCount)) {
            setPower(0.0, 0.0);
            resetEncoders();
            returnValue = true;
        }
        return returnValue;
    } // driveUsingEncoders

    boolean leftHasReset() {
        boolean returnValue = false;

        if (getLeftEncoderCount() == 0)
            returnValue = true;
        return returnValue;
    } // leftHasReset

    boolean rightHasReset() {
        boolean returnValue = false;

        if (getRightEncoderCount() == 0)
            returnValue = true;
        return returnValue;
    } // rightHasReset

    boolean encodersHaveReset() {
        boolean returnValue = false;

        if (leftHasReset() && rightHasReset())
            returnValue = true;
        return returnValue;
    } // encodersHaveReset

    double TicksForInches(double inches_in) {
        double ticks_out = inches_in * TICKS_PER_ROTATION / TIRE_CIRCUMFERENCE * GEAR_RATIO;
        return ticks_out;
    }

    double ticksForDegree(double degrees_in) {
        double ticks_out = TicksForInches(ROBOT_PIVOT_CIRCUMFERENCE) / 360 * degrees_in;
        return ticks_out;
    }

    void turnUsingGyro(double gyroTarget) {
        double diff = gyroTarget - gyro.heading();
        double degreesFromStart = Math.abs(diff);
        double powerFromGyro = Range.clip(degreesFromStart * .01, .20, .75);

        if ((diff < 180.0 && diff > 0.0) || diff < -180.0)
            setPower(powerFromGyro, -powerFromGyro);
        else
            setPower(-powerFromGyro, powerFromGyro);
    } // turnUsingGyro

    void straightenToGyro(double target) {
        double diff = target - gyro.heading();
        if (diff > 2.0 && diff < 25.0)
            setPower(.09, -.09);
        else if (diff < -2.0 && diff > -25)
            setPower(-.09, .09);
    } // straightenToGyro

} // DexMHardware
