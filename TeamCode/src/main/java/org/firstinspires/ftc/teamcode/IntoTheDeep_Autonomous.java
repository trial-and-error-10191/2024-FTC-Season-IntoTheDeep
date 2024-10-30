package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Into The Deep Autonomous Mode", group = "Auto OpMode", preselectTeleOp = "IntoTheDeep TeleOp")
public class IntoTheDeep_Autonomous extends LinearOpMode {

    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap);
        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();
        waitForStart();

        telemetry.addData("Status", "Running Autonomous OpMode");
        telemetry.update();
        robot.resetEncoders();
        robot.driveUsingEncoders();
        robot.resetImu();
        robot.moveForward(24); // should move robot one tile.
    }
}
