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
        CustomGamepad gamepad = new CustomGamepad();

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
            telemetry.addData("Splitting Responsibilities of the Gamepad: ", "%b", UseSplitResponsibilityGamepad);

            if (UseSplitResponsibilityGamepad) {
                gamepad.CombineSplitResponsibilities(gamepad1, gamepad2);
            } else {
                gamepad.CombineOverlappingResponsibilities(gamepad1, gamepad2);
            }

            //robot.driveByPower(gamepad);
            robot.update(gamepad);

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