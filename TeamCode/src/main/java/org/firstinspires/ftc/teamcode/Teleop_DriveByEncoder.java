package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Want a class to safely practice driving a motor by encoder in teleop
public class Teleop_DriveByEncoder extends LinearOpMode {
    DcMotor motor;
    // Do we need to have a DcMotorEx object to really take advantage of driving by encoder in teleop?
    DcMotorEx otherMotor;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "test_motor");

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Motor is DcMotorEx: ", "%b", motor instanceof DcMotorEx);
            telemetry.update();
        }
    }
}
