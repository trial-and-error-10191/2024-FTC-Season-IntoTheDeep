// This is the drive train subsystem file.
// All drive train stuff should be found here.

package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class DriveTrain {

    DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;
    double angles  = 0;         // ini angles for field oriented controls
    private IMU imu;            // ini imu for field oriented controls
    Telemetry telemetry;

    // All subsystems should have a hardware function that labels all of the hardware required of it.
    public DriveTrain(HardwareMap hwMap, Telemetry telemetry) {

        // Initializes motor names:
        leftFrontDrive = hwMap.get(DcMotor.class, "leftFront");     // control hub port 0
        leftBackDrive = hwMap.get(DcMotor.class, "leftBack");       // control hub port 2
        rightFrontDrive = hwMap.get(DcMotor.class, "rightFront");   // control hub port 1
        rightBackDrive = hwMap.get(DcMotor.class, "rightBack");     // control hub port 3

        // Initializes motor directions:
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set up IMU specific to robot. See web link for examples:
        // https://ftc-docs.firstinspires.org/en/latest/programming_resources/imu/imu.html
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu = hwMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();          // resets robots heading
        this.telemetry = telemetry;
    }

    // Field oriented controls for driving
    public void drive(double axial, double lateral, double yaw) {
        double deadzone = 0.05;           // deadzone in joystick drift
        double sensitivity = 0.65;        // initializes sensitivity
        double max;                       // use for max power to wheels

        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        angles = -orientation.getYaw(AngleUnit.RADIANS);

        double robotForward = -axial * Math.cos(angles) + lateral * Math.sin(angles);
        double robotStrafe = lateral * Math.cos(angles) - -axial * Math.sin(angles);

        double leftFrontPower = 0;
        double rightFrontPower = 0;
        double leftBackPower = 0;
        double rightBackPower = 0;

        if (Math.abs(robotForward) > deadzone || Math.abs(robotStrafe) > deadzone || Math.abs(yaw) > deadzone) {
            leftFrontPower = robotForward + robotStrafe + yaw;
            rightFrontPower = robotForward - robotStrafe - yaw;
            leftBackPower = robotForward - robotStrafe + yaw;
            rightBackPower = robotForward + robotStrafe - yaw;
        }

        // All code below this comment normalizes the values so no wheel power exceeds 100%.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // The next four lines gives the calculated power to each motor.
        leftFrontDrive.setPower(leftFrontPower * sensitivity);
        rightFrontDrive.setPower(rightFrontPower * sensitivity);
        leftBackDrive.setPower(leftBackPower * sensitivity);
        rightBackDrive.setPower(rightBackPower * sensitivity);
    }

    public void driveNormal(double axial, double lateral, double yaw) {

        // initializes deadzone
        double deadzone = 0.05;
        // initializes sensitivity
        double sensitivity = 0.75;

        double leftFrontPower = 0;
        double rightFrontPower = 0;
        double leftBackPower = 0;
        double rightBackPower = 0;

        if (Math.abs(axial) > deadzone || Math.abs(lateral) > deadzone || Math.abs(yaw) > deadzone) {
            leftFrontPower = axial + lateral + yaw;
            rightFrontPower = axial - lateral - yaw;
            leftBackPower = axial - lateral + yaw;
            rightBackPower = axial + lateral - yaw;
        }
        double max;

        // All code below this comment normalizes the values so no wheel power exceeds 100%.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));


        if (max > 1.0) {
            leftFrontPower /= max; // leftFrontPower = leftFrontPower / max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Calculates power using sensitivity variable.
        leftFrontPower *= sensitivity;
        leftBackPower *= sensitivity;
        rightFrontPower *= sensitivity;
        rightBackPower *= sensitivity;

        leftFrontPower *= 0.7; // this motor is 312 rpm, others are 223. 223/312 ~ 0.7

        // The next four lines gives the calculated power to each motor.
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }

    // Provides telemetry info for the driving motors
    public void motorTelemetry() {
        telemetry.addData("LeftFront Motor: ", "%4.2f", leftFrontDrive.getPower());
        telemetry.addData("RightFront Motor: ", "%4.2f", rightFrontDrive.getPower());
        telemetry.addData("LeftBack Motor: ", "%4.2f", leftBackDrive.getPower());
        telemetry.addData("RightBack Motor: ", "%4.2f", rightBackDrive.getPower());
        telemetry.addData("angles", "%4.2f", angles);
    }

}