   package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This file is the main TeleOp file.

@TeleOp(name = "IntoTheDeep TeleOp", group = "LinearOpMode")
public class IntoTheDeep_TeleOp extends LinearOpMode {

    private boolean UseSplitResponsibilityGamepad = true;
    private boolean USRGSignalPast = false;
    private boolean USRGSignalCurrent = false;
    @Override
    public void runOpMode() {


        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);
        SplitResponsibilityGamepad srGamepad = new SplitResponsibilityGamepad();
        CombinedResponsibilityGamepad crGamepad = new CombinedResponsibilityGamepad();

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Gamepad 1 exists: ", "%b", gamepad1 != null);
            telemetry.addData("Gamepad 2 exists: ", "%b", gamepad2 != null);

            // Only change on new button press.
            // If button is pressed, we only want to switch once, not oscillate rapidly back and forth
            USRGSignalCurrent = gamepad1.back || gamepad1.guide;
            if (!USRGSignalPast && USRGSignalCurrent) {
                UseSplitResponsibilityGamepad = !UseSplitResponsibilityGamepad;
            }
            USRGSignalPast = USRGSignalCurrent;

            telemetry.addData("Using Split Responsibility Controller: ", "%b", UseSplitResponsibilityGamepad);

            if (UseSplitResponsibilityGamepad) {
                srGamepad.MergeGamepads(gamepad1, gamepad2);
                robot.driveByPower(-srGamepad.left_stick_y, srGamepad.left_stick_x, srGamepad.right_stick_x);
            } else {
                crGamepad.MergeGamepads(gamepad1, gamepad2);
                robot.driveByPower(-crGamepad.left_stick_y, crGamepad.left_stick_x, crGamepad.right_stick_x);
            }

            // Provides telemetry for all motors, servos, and sensors.
            telemetry.addData("Front Driving Motors (Left, Right)", "%4.2f, %4.2f",
                    robot.getLeftFrontMotorPower(),
                    robot.getRightFrontMotorPower());
            telemetry.addData("Back Driving Motors (Left, Right)", "%4.2f, %4.2f",
                    robot.getLeftBackMotorPower(),
                    robot.getRightBackMotorPower());
            telemetry.update();
        }
    }
}