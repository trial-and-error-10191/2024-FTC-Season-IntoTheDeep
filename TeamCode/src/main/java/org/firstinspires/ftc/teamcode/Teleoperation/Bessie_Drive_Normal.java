package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Assemblies.DriveTrain;

@TeleOp (name = "Bessie Drive Normal", group = "LinearOpModes")
public class Bessie_Drive_Normal extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        DriveTrain driveTrain = new DriveTrain(hardwareMap, telemetry);
        while (opModeIsActive()) {
            driveTrain.driveNormal(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
