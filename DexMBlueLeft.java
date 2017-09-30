package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DexMBlueLeft extends LinearOpMode {
    DexMRobot robot = new DexMRobot(hardwareMap);
    DexMTelemetry telem = new DexMTelemetry(telemetry, robot, gamepad1, gamepad2);

    double ticks_target = 0;

    double gyro_Target = 0.0;
    boolean got_gyro_target = false;
    double fix_heading = 0.0;
    final static double DRIVE_POWER = .75;
    private ElapsedTime runtime = new ElapsedTime();

    private enum State {
        WAIT_FOR_RESETS,
        FORWARD_1,
        TURN_LEFT_1,
        WAIT_FOR_ENCODERS_1,
        BACKWARD_1,
        WAITTIME,
        STOP
    }

    State CurrState;

    public DexMBlueLeft() {
    } // DexMAuto

    @Override
    public void runOpMode() {
        runtime.reset();
        robot.init();
        CurrState = State.WAIT_FOR_RESETS;

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            switch (CurrState) {
                case WAIT_FOR_RESETS: // Synchronize the state machine and hardware.
                    if (!robot.driveTrain.gyro.isCalibrating() && robot.driveTrain.encodersHaveReset())
                        CurrState = State.WAITTIME;
                    break;

                case WAITTIME:
                    if (runtime.time() >= 8)
                        CurrState = State.FORWARD_1;
                    break;

                case FORWARD_1: // Drive forward until the encoders exceed the specified values.
                    robot.driveTrain.runUsingEncoders();
                    fix_heading = robot.driveTrain.gyro.checkHeading(gyro_Target);
                    robot.driveTrain.setPower(DRIVE_POWER + fix_heading, DRIVE_POWER - fix_heading);
                    ticks_target = robot.driveTrain.TicksForInches(90);

                    if (robot.driveTrain.encodersHaveReached(ticks_target, ticks_target)) {
                        robot.driveTrain.setPower(0.0, 0.0);
                        CurrState = State.TURN_LEFT_1;
                    }
                    break;

                case TURN_LEFT_1: // Turn left until Gyro hits specified value.
                    robot.driveTrain.runWithoutEncoders();
                    if (!got_gyro_target) {
                        gyro_Target = robot.driveTrain.gyro.calcTarget(gyro_Target, -90);
                        got_gyro_target = true;
                    }
                    if (robot.driveTrain.gyro.hasReached(gyro_Target)) {
                        robot.driveTrain.setPower(0.0, 0.0);
                        robot.driveTrain.resetEncoders();
                        got_gyro_target = false;
                        CurrState = State.WAIT_FOR_ENCODERS_1;
                    } else
                        robot.driveTrain.turnUsingGyro(gyro_Target);
                    break;

                case WAIT_FOR_ENCODERS_1: // Synchronize the state machine and hardware.
                    if (robot.driveTrain.encodersHaveReset())
                        CurrState = State.BACKWARD_1;
                    break;

                case BACKWARD_1: // Drive backward until the encoders exceed the specified values.
                    robot.driveTrain.runUsingEncoders();
                    fix_heading = robot.driveTrain.gyro.checkHeading(gyro_Target);
                    robot.driveTrain.setPower(-DRIVE_POWER + fix_heading, -DRIVE_POWER - fix_heading);
                    ticks_target = robot.driveTrain.TicksForInches(100);

                    if (robot.driveTrain.encodersHaveReached(ticks_target, ticks_target)) {
                        robot.driveTrain.setPower(0.0, 0.0);
                        CurrState = State.STOP;
                    }
                    break;

                case STOP:
                    break;

                default: // Autonomous done - wait until Stop command
                    robot.driveTrain.setPower(0.0, 0.0);
                    break;
            }

            telemetry.addData("Gyro Head", String.valueOf(robot.driveTrain.gyro.heading()) + "Target: " + gyro_Target);
            telem.updateDrive(); // Update common telemetry
            telemetry.addData("18", "State: " + CurrState);
            telemetry.update();
        }
    } // loop
} // DexMAuto
