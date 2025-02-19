package org.firstinspires.ftc.teamcode.Teleoperation;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Assemblies.Robot;

@TeleOp (name = "Field oriented controls", group = "LinearOpMode")
public class Field_TeleOp extends LinearOpMode {
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public IMU imu = null;

    double angles = 0;

    double initYaw;
    double adjustedYaw;

    public void runOpMode() {
        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);

        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");

        // Initializes motor directions:
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        /* The next two lines define Hub orientation.
         * The Default Orientation (shown) is when a hub is mounted horizontally with the printed logo pointing UP and the USB port pointing FORWARD.
         *
         * To Do:  EDIT these two lines to match YOUR mounting configuration.
         */
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        // Now initialize the IMU with this mounting orientation
        // This sample expects the IMU to be in a REV Hub and named "imu".
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));

//        imu.resetYaw();

        waitForStart();
        robot.limbArm.initRotateByPower();

        double deadzone = 0.05;
        while (opModeIsActive()) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            angles = -orientation.getYaw(AngleUnit.RADIANS);

            double fieldStrafe = gamepad1.left_stick_x;
            double fieldForward = -gamepad1.left_stick_y;
            double fieldTurn = gamepad1.right_stick_x;

            double robotForward = fieldForward * Math.cos(angles) + fieldStrafe * Math.sin(angles);
            double robotStrafe = fieldStrafe * Math.cos(angles) - fieldForward * Math.sin(angles);
            double robotTurn = fieldTurn;

            double leftFrontPower = 0;
            double rightFrontPower = 0;
            double leftBackPower = 0;
            double rightBackPower = 0;

            if (Math.abs(robotForward) > deadzone || Math.abs(robotStrafe) > deadzone || Math.abs(robotTurn) > deadzone) {
                leftFrontPower = robotForward + robotStrafe + robotTurn;
                rightFrontPower = robotForward - robotStrafe - robotTurn;
                leftBackPower = robotForward - robotStrafe + robotTurn;
                rightBackPower = robotForward + robotStrafe - robotTurn;
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

            double sensitivity = 0.65;
            // The next four lines gives the calculated power to each motor.
            leftFrontDrive.setPower(leftFrontPower * sensitivity);
            rightFrontDrive.setPower(rightFrontPower * sensitivity);
            leftBackDrive.setPower(leftBackPower * sensitivity);
            rightBackDrive.setPower(rightBackPower * sensitivity);

            robot.updateState(gamepad2);
            robot.moveClaw(gamepad2, robot.limbArm.LimbExtendCount());


            // Makes the limb arm extend/contract, and gives the option to have precise movement
            if (gamepad2.left_stick_y < 0.05 && gamepad2.left_stick_y > -0.05) {   // Makes sure there's no drifting
                gamepad2.left_stick_y = 0;
            }
            robot.limbArm.RunMotor(-gamepad2.left_stick_y);

            // Makes the limb arm rotate
            if (gamepad2.right_stick_x < 0.05 && gamepad2.right_stick_x > -0.05) { // Makes sure there's no drifting
                gamepad2.right_stick_x = 0;
            }
            //robot.driveTrain.LiftHandle(robot.limbArm.limbExtend.getCurrentPosition());
            //robot.limbArm.armRotate(gamepad2.right_stick_y);
            robot.limbArm.rotateByPower(-gamepad2.right_stick_y);
            // Spool correction stuff
            robot.limbArm.spoolCorrection(gamepad1.dpad_up, gamepad1.dpad_down);

            telemetry.addData("Extend Encoder Count: d%", robot.limbArm.limbExtend.getCurrentPosition());
            telemetry.addData("angles", "%4.2f", angles);
            telemetry.addData("Claw Rotation", "%1.2f", robot.sampleClaw.servoRotation.getPosition());
            telemetry.update();
        }
    }
}
