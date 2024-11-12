package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "telemetry test", group = "Linear OpMode")
public class ControllerTestTelemetry extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("Status", "Waiting for Start");
            telemetry.update();

            // Provides telemetry for all motors, servos, and sensors.
            telemetry.addData("Left Stick X/Y", "%4.2f, %4.2f",
                    gamepad1.left_stick_x, gamepad1.left_stick_y);
            telemetry.addData("Right Stick X/Y", "%4.2f, %4.2f",
                    gamepad1.right_stick_x, gamepad1.right_stick_y);
            telemetry.addData("Right Trigger", "%4.2f",
                    gamepad1.right_trigger);
            telemetry.addData("Left Trigger", "%4.2f",
                    gamepad1.left_trigger);
        }
    }
}
