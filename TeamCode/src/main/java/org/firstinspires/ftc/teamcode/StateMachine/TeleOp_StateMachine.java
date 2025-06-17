package org.firstinspires.ftc.teamcode.StateMachine;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Assemblies.Robot;
@TeleOp (name = "State Machine TeleOp", group  = "LinearOpMode")

public class TeleOp_StateMachine extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotSM robot = new RobotSM(hardwareMap, telemetry);
        telemetry.addData("Status:", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            robot.updateState(gamepad1, gamepad2);
            robot.run(gamepad1, gamepad2);

            telemetry.addData("Status:", "OpMode is Active");
            telemetry.update();
        }
    }
}
