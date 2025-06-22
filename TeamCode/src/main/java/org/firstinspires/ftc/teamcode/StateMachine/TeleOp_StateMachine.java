package org.firstinspires.ftc.teamcode.StateMachine;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
            telemetry.addData("Claw flip up", gamepad2.left_bumper);
            telemetry.addData("Claw flip down", gamepad2.right_bumper);
            telemetry.addData("Claw slow", gamepad2.y);
            telemetry.addData("Claw rotate left", gamepad2.left_trigger);
            telemetry.addData("Claw rotate right", gamepad2.right_trigger);
            telemetry.update();
        }
    }
}