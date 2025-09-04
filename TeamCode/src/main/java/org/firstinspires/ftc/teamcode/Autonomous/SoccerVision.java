package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import android.graphics.Color;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

@Autonomous(name="SoccerVision", group="Robot")
public class SoccerVision extends LinearOpMode {
    static final double COUNTS_PER_MOTOR_REV    = 753.2 ;   // GoBILDA 223 RPM 52003 Yellow Jacket Series
    static final double DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES   = 3.5 ;     // For figuring circumference
    static final double COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    DcMotor leftFrontDrive   = null;
    DcMotor rightFrontDrive  = null;
    DcMotor rightBackDrive   = null;
    DcMotor leftBackDrive    = null;
    IMU imu              = null;

    private double headingError   = 0;
    private double targetHeading  = 0;
    double driveSpeed     = 0;
    double turnSpeed      = 0;
    double leftSpeed      = 0;
    double rightSpeed     = 0;
    int leftFrontTarget   = 0;
    int leftBackTarget    = 0;
    int rightBackTarget   = 0;
    int rightFrontTarget  = 0;

    private void sendTelemetry(boolean straight) {
        if (straight) {
            telemetry.addData("Heading", getHeading());
            telemetry.addData("Motion", "Drive Straight");
            telemetry.addData("Target Pos L:R",  "%7d:%7d",      leftFrontTarget, rightFrontTarget);
            telemetry.addData("Actual Pos L:R",  "%7d:%7d:%7d:%7d",      leftFrontDrive.getCurrentPosition(),
                    rightFrontDrive.getCurrentPosition(),leftBackDrive.getCurrentPosition(),rightBackDrive.getCurrentPosition());
        } //end of if statement

        else {
            telemetry.addData("Motion", "Turning");
        } // end of else statement

        telemetry.addData("Heading- Target : Current", "%5.2f : %5.0f", targetHeading, getHeading());
        telemetry.addData("Error  : Steer Pwr",  "%5.1f : %5.1f", headingError, turnSpeed);
        telemetry.addData("Front Wheel Speeds L : R", "%5.2f : %5.2f", leftFrontDrive.getPower(), rightFrontDrive.getPower());
        telemetry.addData("Back Wheel Speeds L : R", "%5.2f : %5.2f", rightBackDrive.getPower(), leftBackDrive.getPower());
        telemetry.update();

    } // end of private void sendTelemetry
    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    } // end of public double getHeading
    public void driveStraight(double maxDriveSpeed, double distance, double heading) {

        Orientations();

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            int moveCounts = (int)(distance * COUNTS_PER_INCH);
            leftFrontTarget = leftFrontDrive.getCurrentPosition() + moveCounts;
            leftBackTarget = leftBackDrive.getCurrentPosition() + moveCounts;
            rightFrontTarget = rightFrontDrive.getCurrentPosition() + moveCounts;
            rightBackTarget = rightBackDrive.getCurrentPosition() + moveCounts;

            // Set Target FIRST, then turn on RUN_TO_POSITION
            // If Strafing then reverse motor directions

            final double P_DRIVE_GAIN           = 0.03;     // Larger is more responsive, but also less stable.

            // Set the required driving speed  (must be positive for RUN_TO_POSITION)
            // Start driving straight, and then enter the control loop
            maxDriveSpeed = Math.abs(maxDriveSpeed);
            moveRobot(maxDriveSpeed, 0);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy() && leftBackDrive.isBusy())) {

                // Determine required steering to keep on heading
                turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    turnSpeed *= -1.0;

                // Apply the turning correction to the current driving speed.
                moveRobot(driveSpeed, turnSpeed);

                // Display drive status for the driver.
                sendTelemetry(true);
            } // end of while loop

            // Stop all motion & Turn off RUN_TO_POSITION
            moveRobot(0, 0);
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } // end of if statement
    } // end of public void driveStraight
    public void driveStraightPower(double power, double time) { // Makes the robot move using power
        leftFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightFrontDrive.setPower(power);
        rightBackDrive.setPower(power);
        final ElapsedTime Time   = new ElapsedTime();
        Time.reset();
        while (Time.milliseconds()  < time * 1000) {
            // doesn't need anything
        } // end of while loop
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
    public void moveRobot(double drive, double turn) {
        driveSpeed = drive;     // save this value as a class member so it can be used by telemetry.
        turnSpeed  = turn;      // save this value as a class member so it can be used by telemetry.

        leftSpeed  = drive - turn;
        rightSpeed = drive + turn;

        // Scale speeds down if either one exceeds +/- 1.0;
        double max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
        if (max > 1.0) {
            leftSpeed /= max;
            rightSpeed /= max;
        } // end of if statement

        leftFrontDrive.setPower(leftSpeed * 0.7);
        rightFrontDrive.setPower(rightSpeed);
        leftBackDrive.setPower(leftSpeed);
        rightBackDrive.setPower(rightSpeed);
    } // end of public void moveRobot
    public void Orientations() {
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        Wait(0.1);
    }
    public double getSteeringCorrection(double desiredHeading, double proportionalGain) {
        targetHeading = desiredHeading;  // Save for telemetry

        // Determine the heading current error
        headingError = targetHeading - getHeading();

        // Normalize the error to be within +/- 180 degrees
        while (headingError > 180)  headingError -= 360;
        while (headingError <= -180) headingError += 360;

        // Multiply the error by the gain to determine the required steering correction/  Limit the result to +/- 1.0
        return Range.clip(headingError * proportionalGain, -1, 1);
    } // end of public double getSteeringCorrection
    public void Wait(double seconds) {
        final ElapsedTime Time   = new ElapsedTime();
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesn't need anything
        } // end of while loop
    } // end of public void Wait
    @Override
    public void runOpMode()
    {
        /* Build a "Color Sensor" vision processor based on the PredominantColorProcessor class.
         *
         * - Focus the color sensor by defining a RegionOfInterest (ROI) which you want to inspect.
         *     This can be the entire frame, or a sub-region defined using:
         *     1) standard image coordinates or 2) a normalized +/- 1.0 coordinate system.
         *     Use one form of the ImageRegion class to define the ROI.
         *         ImageRegion.entireFrame()
         *         ImageRegion.asImageCoordinates(50, 50,  150, 150)  100x100 pixel square near the upper left corner
         *         ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1)  10% width/height square centered on screen
         *
         * - Set the list of "acceptable" color swatches (matches).
         *     Only colors that you assign here will be returned.
         *     If you know the sensor will be pointing to one of a few specific colors, enter them here.
         *     Or, if the sensor may be pointed randomly, provide some additional colors that may match the surrounding.
         *     Possible choices are:
         *         RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE, MAGENTA, BLACK, WHITE;
         *
         *     Note that in the example shown below, only some of the available colors are included.
         *     This will force any other colored region into one of these colors.
         *     eg: Green may be reported as YELLOW, as this may be the "closest" match.
         */

        // Initialize the drive system variables.
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        final double TURN_SPEED              = 0.7;     // Max turn speed to limit turn rate.

        PredominantColorProcessor colorSensor = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1))
                .setSwatches(
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE,
                        PredominantColorProcessor.Swatch.YELLOW,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE)
                .build();

        /*
         * Build a vision portal to run the Color Sensor process.
         *
         *  - Add the colorSensor process created above.
         *  - Set the desired video resolution.
         *      Since a high resolution will not improve this process, choose a lower resolution that is
         *      supported by your camera.  This will improve overall performance and reduce latency.
         *  - Choose your video source.  This may be
         *      .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))  .....   for a webcam
         *  or
         *      .setCamera(BuiltinCameraDirection.BACK)    ... for a Phone Camera
         */
        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(colorSensor)
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        telemetry.setMsTransmissionInterval(50);  // Speed up telemetry updates, Just use for debugging.

        // WARNING:  To be able to view the stream preview on the Driver Station, this code runs in INIT mode.
        while (opModeInInit())
        {
            telemetry.addData("Op Mode Is Initiated", opModeInInit());
            telemetry.addData("DS preview on/off", "3 dots, Camera Stream\n");

            // Request the most recent color analysis.
            // This will return the closest matching colorSwatch and the predominant RGB color.
            // Note: to take actions based on the detected color, simply use the colorSwatch in a comparison or switch.
            //  eg:
            //      if (result.closestSwatch == PredominantColorProcessor.Swatch.RED) {... some code  ...}
            PredominantColorProcessor.Result result = colorSensor.getAnalysis();

            // Display the Color Sensor result.
            telemetry.addData("Best Match:", result.closestSwatch);
            telemetry.addLine(String.format("R %3d, G %3d, B %3d", Color.red(result.rgb), Color.green(result.rgb), Color.blue(result.rgb)));
            telemetry.update();

            sleep(20);
        }
        while (opModeIsActive()) {
            PredominantColorProcessor.Result result = colorSensor.getAnalysis();
            telemetry.addData("Op Mode Is Active", opModeIsActive());
            telemetry.update();
            if (result.closestSwatch == PredominantColorProcessor.Swatch.RED) {
                driveStraightPower(0.5, 1);
                Wait(1);
                driveStraightPower(-0.5, 1);
                Wait(30); // To make sure this while doesn't loop again
            }
        }
    }
}