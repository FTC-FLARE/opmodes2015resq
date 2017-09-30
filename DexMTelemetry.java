package org.firstinspires.ftc.teamcode.opmodes8767;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Provide telemetry provided by the DexMHardware class.
 * Based on PushBotTelemetry
 *
 * Telemetry Keys
 *     00 - Important message; sometimes used for error messages.
 *     01 - The power being sent to the left drive's motor controller and the
 *          encoder count returned from the motor controller.
 *     02 - The power being sent to the right drive's motor controller and the
 *          encoder count returned from the motor controller.
 *     03 - The power being sent to the left arm's motor controller.
 *     04 - The position being sent to the left and right hand's servo
 *          controller.
 *     05 - The negative value of gamepad 1's left stick's y (vertical; up/down)
 *          value.
 *     06 - The negative value of gamepad 1's right stick's y (vertical;
 *          up/down) value.
 *     07 - The negative value of gamepad 2's left stick's y (vertical; up/down)
 *          value.
 *     08 - The value of gamepad 2's X button (true/false).
 *     09 - The value of gamepad 2's Y button (true/false).
 *     10 - The value of gamepad 1's left trigger value.
 *     11 - The value of gamepad 1's right trigger value.
 */
public class DexMTelemetry {

    Telemetry telemetry = null;
    DexMRobot robot = null;
    Gamepad gamepad1 = null;
    Gamepad gamepad2 = null;


    public DexMTelemetry(Telemetry telemetry, DexMRobot robot, Gamepad gamepad1, Gamepad gamepad2)
    {
        this.telemetry = telemetry;
        this.robot = robot;
        this.gamepad1 = gamepad1;
        this.gamepad1 = gamepad1;
    } // DexMTelemetry

    public void updateDrive()
    {
        telemetry.addData ( "01", "Left Drive: " + robot.driveTrain.getLeftPower() + ", " + robot.driveTrain.getLeftEncoderCount());
        telemetry.addData ( "02", "Right Drive: " + robot.driveTrain.getRightPower() + ", " + robot.driveTrain.getRightEncoderCount());
    } // updateDrive

    public void updateGamepad()
    {
        telemetry.addData ("05", "GP1 Left: " + -gamepad1.left_stick_y);
        telemetry.addData ("06", "GP1 Right: " + -gamepad1.right_stick_y);
        telemetry.addData ("07", "GP2 Left: " + -gamepad2.left_stick_y);
        telemetry.addData ("08", "GP2 X: " + gamepad2.x);
        telemetry.addData ("09", "GP2 Y: " + gamepad2.y);
        telemetry.addData ("10", "GP1 LT: " + gamepad1.left_trigger);
        telemetry.addData ("11", "GP1 RT: " + gamepad1.right_trigger);

    } // updateGamepad

    public void setFirstMessage(String p_message)
    {
        telemetry.addData ( "00", p_message);
    } // setFirstMessage

    public void set_error_message (String p_message)
    {
        setFirstMessage("ERROR: " + p_message);
    } // set_error_message
} // DexMTelemetry
