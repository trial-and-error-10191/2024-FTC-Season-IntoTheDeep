package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Tankbot", group = "LinearOpMode")
public class tankbot extends LinearOpMode {
    DcMotor LeftFront = null;
    DcMotor LeftBack = null;
    DcMotor RightFront = null;
    DcMotor RightBack = null;

    double Deadzone = 0.05;

    public void runOpMode() {
        LeftFront = hardwareMap.get(DcMotor.class, "Left_Front");
        LeftBack = hardwareMap.get(DcMotor.class, "Left_Back");
        RightFront = hardwareMap.get(DcMotor.class, "Right_Front");
        RightBack = hardwareMap.get(DcMotor.class, "Right_Back");
        LeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("waiting for start", "");
        telemetry.update();
       waitForStart();
        while(opModeIsActive()) {
          double Left_Input = gamepad1.left_stick_y;
          double Right_Input = gamepad1.right_stick_y;

          LeftFront.setPower(Math.abs(Left_Input) > Deadzone ? Left_Input : 0);
          LeftBack.setPower(Math.abs(Left_Input) > Deadzone ? Left_Input : 0);
            RightFront.setPower(Math.abs(Right_Input) > Deadzone ? Right_Input : 0);
            RightBack.setPower(Math.abs(Right_Input) > Deadzone ? Right_Input : 0);

        }
    }
}
