   package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

// This file is the main TeleOp file.

@TeleOp(name = "IntoTheDeep TeleOp", group = "LinearOpMode")
public class IntoTheDeep_TeleOp extends LinearOpMode {

    @Override

    public void runOpMode() {
        
        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            // This controls the drive train using three double input methods.
            // The fourth input is a boolean for the direction toggle.
            // The last input is the time the function uses to space out inputs for the direction switch.
            robot.driveTrain.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            robot.ascentMechanism.rise(gamepad2.y, gamepad2.x);
            // Moves the arm that moves the specimen up and down
            robot.scoopyArm.scoopArmPosition(gamepad2.left_trigger, gamepad2.right_trigger);

            // Provides telemetry for all motors, servos, and sensors.
            telemetry.addData("Front Driving Motors (Left, Right)", "%4.2f, %4.2f",
                    robot.driveTrain.leftFrontDrive.getPower(),
                    robot.driveTrain.rightFrontDrive.getPower());
            telemetry.addData("Back Driving Motors (Left, Right)", "%4.2f, %4.2f",
                    robot.driveTrain.leftBackDrive.getPower(),
                    robot.driveTrain.rightBackDrive.getPower());
            telemetry.addData("Scooper Arm (Up, Down))", "%4.2f",
                    robot.scoopyArm.CRservo.getPower());
            telemetry.update();
        }
    }
}