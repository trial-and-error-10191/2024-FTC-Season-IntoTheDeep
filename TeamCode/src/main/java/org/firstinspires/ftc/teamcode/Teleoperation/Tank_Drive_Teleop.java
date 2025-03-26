package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp (name = "Tank-Drive", group  = "LinearOpMode")
public class Tank_Drive_Teleop extends LinearOpMode {

 public DcMotor LeftSideFirst = null;
 public DcMotor LeftSideSecond = null;
 public DcMotor RightSideFirst = null;
 public DcMotor RightSideSecond = null;

 public double Deadzone = 0.05;

 public void runOpMode() {
     LeftSideFirst = hardwareMap.get(DcMotor.class, "L1");
     LeftSideSecond = hardwareMap.get(DcMotor.class, "L2");
     RightSideFirst = hardwareMap.get(DcMotor.class, "R1");
     RightSideSecond = hardwareMap.get(DcMotor.class, "R2");
        waitForStart();
        while (opModeIsActive()) {

            LeftSideFirst.setPower(gamepad1.left_stick_y > Deadzone || -Deadzone > gamepad1.left_stick_y ? gamepad1.left_stick_y : 0);
            LeftSideSecond.setPower(gamepad1.left_stick_y > Deadzone || -Deadzone > gamepad1.left_stick_y ? gamepad1.left_stick_y : 0);

            RightSideFirst.setPower(gamepad1.right_stick_y > Deadzone || -Deadzone > gamepad1.right_stick_y ? gamepad1.right_stick_y : 0);
            RightSideSecond.setPower(gamepad1.right_stick_y > Deadzone || -Deadzone > gamepad1.right_stick_y ? gamepad1.right_stick_y : 0);

        }
    }
}



