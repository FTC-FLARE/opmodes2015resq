package org.firstinspires.ftc.teamcode.opmodes8767;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
public class DexMRobot {

    DexMDriveTrain driveTrain;
    DexMHitmen hitmen;
    DexMArm arm;
    DexMHook hook;
    DexMDumper dumper;

    private UltrasonicSensor ultraTop;
    private Sensor ultraBottom;

    public DexMRobot(HardwareMap hwMap) {
        driveTrain = new DexMDriveTrain(hwMap);
        hitmen = new DexMHitmen(hwMap);
        arm = new DexMArm(hwMap);
        hook = new DexMHook(hwMap);
        dumper = new DexMDumper(hwMap);

        ultraTop = hwMap.ultrasonicSensor.get("ultraTop");
        ultraTop = hwMap.ultrasonicSensor.get("ultraBottom");
    } // DexMHardware

    public void init() {
        driveTrain.gyro.calibrate();
        driveTrain.resetEncoders();

    }
    // init

} // DexMHardware
