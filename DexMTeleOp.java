package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DexMTeleOp extends LinearOpMode {

    private DexMRobot robot;
    private DexMTelemetry telem;

    public DexMTeleOp() {
    } // DexMTeleOp

    @Override
    public void runOpMode() {

        robot = new DexMRobot(hardwareMap);
        telem = new DexMTelemetry(telemetry, robot, gamepad1, gamepad2);

        robot.init();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Note: x and y equal -1 when the joystick is pushed all the way forward.
            double leftDrivePower = robot.driveTrain.scalePower(-gamepad1.left_stick_y);
            double rightDrivePower = robot.driveTrain.scalePower(-gamepad1.right_stick_y);

            robot.driveTrain.setPower(leftDrivePower, rightDrivePower);

            // Control arm - both the in/out movement & lifting
            robot.arm.checkForMove(gamepad1);

            // Control lower hook
            robot.hook.checkForMove(gamepad1);

            // Control hitmen
            robot.hitmen.checkForMove(gamepad2);

            //Controls the arm that dumps the hikers into the thing
            robot.dumper.checkForMove(gamepad2);

            telem.updateDrive(); // Update common telemetry
            telem.updateGamepad();
            telemetry.addData("Hitman_left ", robot.hitmen.getLeft());
            telemetry.addData("Hitman_right ", robot.hitmen.getRight());
            telemetry.addData("Arm ", robot.arm.getPower());
            telemetry.addData("servo_dumper", robot.dumper.getPosition());
            telemetry.update();
        }

    } // loop

} // DexMTeleOp
