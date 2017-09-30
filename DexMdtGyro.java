package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DexMdtGyro {

    private GyroSensor gyro;
    final static double GYRO_THRESHOLD = 1.0;

    public DexMdtGyro(HardwareMap hwMap) {
        gyro = hwMap.gyroSensor.get("gyro");
    } // DexMHardware

    void calibrate() {
        if (gyro != null)
            gyro.calibrate();
    } // calibrate

    boolean isCalibrating() {
        boolean returnValue = false;

        if (gyro.isCalibrating())
            returnValue = true;
        return returnValue;
    } // isCalibrating

    boolean hasReached(double target) {
        boolean returnValue = false;

        double degreesFromTarget = Math.abs(target - heading());
        if (degreesFromTarget < GYRO_THRESHOLD)
            returnValue = true;
        return returnValue;
    } // hasReached

    double calcTarget(double calcIn, double degrees) {
        double outTarget = calcIn + degrees;
        if (outTarget < 0.0)
            outTarget += 360.0;
        else if (outTarget >= 360.0)
            outTarget -= 360.0;
        return outTarget;
    }

    double checkHeading(double target) {
        double returnValue = 0.0;
        double diff = target - heading();
        if (diff > 2.0 && diff < 25.0)
            returnValue = .1;
        else if (diff < -2.0 && diff > -25)
            returnValue = -.1;

        return returnValue;
    } // checkHeading

    double heading() {
        return gyro.getHeading();
    }

} // DexMHardware
