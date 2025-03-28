package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Assemblies.AscentMechanism;
import org.firstinspires.ftc.teamcode.Assemblies.DriveTrain;
import org.firstinspires.ftc.teamcode.Assemblies.Robot;

// This file is the main TeleOp file.

@TeleOp(name = "IntoTheDeep TeleOp", group = "LinearOpMode")
public class stupidfreakingrobot extends LinearOpMode {

    @Override

    public void runOpMode() {


        // Initiates the robots system and subsystems!
        DriveTrain drive_train = new DriveTrain(hardwareMap, telemetry);
        AscentMechanism Ascent = new AscentMechanism(hardwareMap);
        drive_train.Small_Wheels();
        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            // This controls the drive train using three double input methods.
            // The fourth input is a boolean for the direction toggle.
            // The last input is the time the function uses to space out inputs for the direction switch.
            Ascent.rise(gamepad1.dpad_up, gamepad1.dpad_down);
            drive_train.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            // Provides telemetry for all motors, servos, and sensors.
        }
    }
}