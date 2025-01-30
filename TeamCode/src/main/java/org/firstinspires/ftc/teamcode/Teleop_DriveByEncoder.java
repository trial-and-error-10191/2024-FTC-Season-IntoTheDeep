package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

// Want a class to safely practice driving a motor by encoder in teleop
@TeleOp(name = "Drive By Encoder: Teleop", group = "LinearOpMode")
public class Teleop_DriveByEncoder extends LinearOpMode {
    DcMotorEx motor;
    int targetPosition = 0;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "test_motor");
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);
        targetPosition = motor.getCurrentPosition();

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            int roundedResult = (int)(gamepad1.right_trigger * 20);
            telemetry.addData("Motor Encoder count: %d", motor.getCurrentPosition());
            telemetry.addData("Motor Target: %d", motor.getTargetPosition());
            telemetry.addData("Motor Power: %4.2f", motor.getPower());
            telemetry.addData("", "");
            telemetry.addData("gamepad input: ", "%4.2f", gamepad1.right_trigger);
            telemetry.addData("rounded result: ", "%d", roundedResult);
            telemetry.addData("", "");
            telemetry.addData("stick drift: ", "%4.2f", -gamepad1.left_stick_y);
            telemetry.update();
            RunMotor(gamepad1);
        }
    }

    private void RunMotor(Gamepad gamepad) {
        float liftSpeed = -gamepad1.left_stick_y; // from gamepad, up -> negative, down -> positive.
        if (-0.05f <= liftSpeed && liftSpeed <= 0.05f) {
            liftSpeed = 0.0f;
        }

        if (Math.abs(liftSpeed) > 0.05f) {
            targetPosition = motor.getCurrentPosition() + (int) (liftSpeed * 40);
        }
        motor.setTargetPosition(targetPosition);
    }
}
