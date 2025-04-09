package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Assemblies.Robot;

@TeleOp (name = "Bessie_TeleOp", group = "LinearOpMode")
public class Bessie_TeleOp extends LinearOpMode {

    public void runOpMode() {
        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);
        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            robot.driveTrain.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            robot.lift.toggleLEDs(gamepad1.x);
            robot.lift.moveUp(gamepad1.right_trigger);
            robot.lift.moveDown(gamepad1.left_trigger);
            robot.claw.open(gamepad1.right_bumper);
            robot.claw.close(gamepad1.left_bumper);

            // Provides telemetry for all motors, servos, and sensors.
            robot.driveTrain.motorTelemetry();
            robot.lift.liftTelemetry();
            robot.claw.clawTelemetry();
            telemetry.update();
        }
    }

}
