   package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This file is the main TeleOp file.

@TeleOp(name = "IntoTheDeep TeleOp", group = "LinearOpMode")
public class IntoTheDeep_TeleOp extends LinearOpMode {

    @Override

    public void runOpMode() {


        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);
        SplitResponsibilityGamepad gamepad = new SplitResponsibilityGamepad();

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Gamepad 1 exists: ", "%b", gamepad1 != null);
            telemetry.addData("Gamepad 2 exists: ", "%b", gamepad2 != null);
            gamepad.MergeGamepads(gamepad1, gamepad2);
            // This controls the drive train using three double input methods.
            // The fourth input is a boolean for the direction toggle.
            // The last input is the time the function uses to space out inputs for the direction switch.
            //robot.driveByPower(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            robot.driveByPower(-gamepad.left_stick_y, gamepad.left_stick_x, gamepad.right_stick_x);

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