package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

// Want a class to safely practice driving a motor by encoder in teleop
@TeleOp(name = "Drive By Encoder: Teleop", group = "LinearOpMode")
public class Teleop_DriveByEncoder extends LinearOpMode {
    DcMotorEx motor;
    int targetPosition = 0;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "test_motor");
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);
        //motor.setVelocity(10); This isn't working in this setup. not sure why. targetPosition's value is changing much slower
        targetPosition = motor.getCurrentPosition();

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Motor Encoder count: %d", motor.getCurrentPosition());
            telemetry.addData("Motor Target: %d", motor.getTargetPosition());
            telemetry.addData("Motor Power: %4.2f", motor.getPower());
            telemetry.update();
            RunMotor(gamepad1);
        }
    }

    private void RunMotor(Gamepad gamepad) {
        // dpad up/down to extend/retract lift
        // Determine where driver is telling motor to go
        boolean raisingLift = gamepad.dpad_up;
        boolean loweringLift = gamepad.dpad_down;

        if (raisingLift) { // If going up, move lift up while guard against overextending
            targetPosition = motor.getCurrentPosition() + 10;
        } else if (loweringLift) { // If going down, guard against retracting too far
            targetPosition = motor.getCurrentPosition() - 10;
        }

        motor.setTargetPosition(targetPosition);
    }
}
