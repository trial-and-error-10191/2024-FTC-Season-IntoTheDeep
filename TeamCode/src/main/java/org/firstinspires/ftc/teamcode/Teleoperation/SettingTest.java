package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Assemblies.Robot;

@TeleOp (name = "SettingTest", group = "LinearOpMode")
public class SettingTest extends LinearOpMode {

    public void runOpMode() {
        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);

        waitForStart();
        robot.limbArm.initRotateByPower();

        while (opModeIsActive()) {
            robot.updateState(gamepad1);
            robot.setMove(gamepad1, gamepad2);

            telemetry.addData("Manual", "%b", robot.doesManual());
            telemetry.update();
        }
    }
}
