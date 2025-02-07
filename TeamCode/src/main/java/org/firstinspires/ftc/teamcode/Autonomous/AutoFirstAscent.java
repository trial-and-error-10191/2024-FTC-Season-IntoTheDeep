package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Assemblies.LimbArm;
import org.firstinspires.ftc.teamcode.Assemblies.SampleClaw;

@Autonomous(name="AutoFirstAscent", group="Robot")
//@Disabled
public class AutoFirstAscent extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor leftFrontDrive   = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor leftBackDrive = null;
    private IMU imu         = null;
    private final ElapsedTime Time = new ElapsedTime();
    LimbArm arm;
    SampleClaw claw;
    // Control/Expansion Hub IMU

    private double          headingError  = 0;

    // These variable are declared here (as class members) so they can be updated in various methods,
    // but still be displayed by sendTelemetry()
    private double  targetHeading = 0;
    private double  driveSpeed    = 0;
    private double  turnSpeed     = 0;
    private double  leftSpeed     = 0;
    private double  rightSpeed    = 0;
    private int     leftFrontTarget    = 0;
    private int leftBackTarget = 0;
    private int rightBackTarget = 0;
    private int rightFrontTarget = 0;
    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     COUNTS_PER_MOTOR_REV    = 537.6 ;   // eg: GoBILDA 312 RPM Yellow Jacket
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    // These constants define the desired driving/control characteristics
    // They can/should be tweaked to suit the specific robot drive train.
    static final double     DRIVE_SPEED             = 0.7;     // Max driving speed for better distance accuracy.
    static final double     TURN_SPEED              = 0.5;     // Max turn speed to limit turn rate.
    static final double     HEADING_THRESHOLD       = 2.0 ;    // How close must the heading get to the target before moving to next step.
    // Requiring more accuracy (a smaller number) will often make the turn take longer to get into the final position.
    // Define the Proportional control coefficient (or GAIN) for "heading control".
    // We define one value when Turning (larger errors), and the other is used when Driving straight (smaller errors).
    // Increase these numbers if the heading does not correct strongly enough (eg: a heavy robot or using tracks)
    // Decrease these numbers if the heading does not settle on the correct value (eg: very agile robot with omni wheels)
    static final double     P_TURN_GAIN            = 0.02;     // Larger is more responsive, but also less stable.
    static final double     P_DRIVE_GAIN           = 0.03;     // Larger is more responsive, but also less stable.

    public void Orientations() {
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        Wait(0.1);
    }
    public void flip(String FirstLocation, String SecondLocation) {
        // The input is expected to be L or R for the first value and F or B for the second value
        if (FirstLocation.equals("L")) {
            if (SecondLocation.equals("F")) {
                if (leftFrontDrive.getDirection() == DcMotor.Direction.REVERSE) {
                    leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
                } else {
                    leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
                }
            } else {
                if (leftBackDrive.getDirection() == DcMotor.Direction.REVERSE) {
                    leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
                } else {
                    leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
                }
            }
        }
        else {
            if (SecondLocation.equals("F")) {
                if (rightFrontDrive.getDirection() == DcMotor.Direction.REVERSE) {
                    rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
                } // end of if statement
                else {
                    rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
                } // end of else statement
            } // end of if statement
            else {
                if (rightBackDrive.getDirection() == DcMotor.Direction.REVERSE) {
                    rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
                } // end of if statement
                else {
                    rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
                } // end of else Statement
            } // end of else Statement
        } // end of else statement
        Wait(0.1);
    }

    @Override
    public void runOpMode() {

        arm = new LimbArm(hardwareMap, telemetry);
        claw = new SampleClaw(hardwareMap);

        // Initialize the drive system variables.
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flip
        Orientations();
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

        // Ensure the robot is stationary.  Reset the encoders and set the motors to BRAKE mode
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        imu.resetYaw();

        // Wait for the game to start (Display Gyro value while waiting)

        while (opModeInInit()) {
            telemetry.addData(">", "Robot Heading = %4.0f", getHeading());
            telemetry.update();
        } // end of while loop

        // Set the encoders for closed loop speed control, and reset the heading.
        // BEGIN AUTO CODE

        StrafeRobot(TURN_SPEED, -6, 0);
        // Wait(1);
        driveStraight(TURN_SPEED, 62, 0);
        // Wait(1);
        turnToHeading(TURN_SPEED, -90);
        // Wait(1);
        driveStraight(TURN_SPEED, 10, -90);
         Wait(1);
       // arm.armRotateAuto(-50);
       // Wait(1);
       // arm.AutoExtendMotor(500);
        // Wait(1);
       // arm.armRotateAuto(100);
       // Wait(1);




        // 0 for heading is whatever the robot's original position is. 90 goes to the left of 0, and negative 90 goes to the right of the 0.
        // Lastly, 180 goes the exact opposite of where 0 is.
        // Step through each leg of the path,
        // Notes:   Reverse movement is obtained by setting a negative distance (not speed)
        //          holdHeading() is used after turns to let the heading stabilize
        //          Add a sleep(2000) after any step to keep the telemetry data visible for review
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // Pause to display last telemetry message.

    } // end of public void runOpMode

    /*
     * ====================================================================================================
     * Driving "Helper" functions are below this line.
     * These provide the high and low level methods that handle driving straight and turning.
     * ====================================================================================================
     */

    // **********  HIGH Level driving functions.  ********************
    /**
     *  Drive in a straight line, on a fixed compass heading (angle), based on encoder counts.
     *  Move will stop if either of these conditions occur:
     *  1) Move gets to the desired position
     *  2) Driver stops the OpMode running.
     *
     * @param maxDriveSpeed MAX Speed for forward/rev motion (range 0 to +1.0) .
     * @param distance   Distance (in inches) to move from current position.  Negative distance means move backward.
     * @param heading      Absolute Heading Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from the current robotHeading.
     */
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

            leftFrontDrive.setTargetPosition(leftFrontTarget);
            rightFrontDrive.setTargetPosition(rightFrontTarget);
            leftBackDrive.setTargetPosition(leftBackTarget);
            rightBackDrive.setTargetPosition(rightBackTarget);

            leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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
    public void StrafeRobot(double maxDriveSpeed, double distance, double heading) {
        Orientations();
        if (distance > 0) {
            flip("L", "B");
            flip("R", "F");
        } else {
            flip("L", "F");
            flip("R", "B");
        }

        telemetry.addData("Encoder Count aim", (distance * COUNTS_PER_INCH));
        if (opModeIsActive()) {
            leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // Determine new target position, and pass to motor controller
            // Determine new target position, and pass to motor controller
            int moveCounts = (int)(Math.abs(distance) * COUNTS_PER_INCH);
            leftFrontTarget = leftFrontDrive.getCurrentPosition() + moveCounts;
            leftBackTarget = leftBackDrive.getCurrentPosition() + moveCounts;
            rightFrontTarget = rightFrontDrive.getCurrentPosition() + moveCounts;
            rightBackTarget = rightBackDrive.getCurrentPosition() + moveCounts;

            // Set Target FIRST, then turn on RUN_TO_POSITION
            // If Strafing then reverse motor directions
            leftFrontDrive.setTargetPosition(leftFrontTarget);
            rightFrontDrive.setTargetPosition(rightFrontTarget);
            leftBackDrive.setTargetPosition(leftBackTarget);
            rightBackDrive.setTargetPosition(rightBackTarget);

            leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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
//            if (distance < 0) {
//                turnSpeed *= -1.0;
//            }
                // Apply the turning correction to the current driving speed.
                RotateRoboto(driveSpeed, turnSpeed, distance < 0);

                // Display drive status for the driver.
                // sendTelemetry(true);
            }
            telemetry.addData(" LF end encoder counter", leftFrontDrive.getCurrentPosition() );
            telemetry.addData(" LB end encoder counter", leftBackDrive.getCurrentPosition() );
            telemetry.addData(" RF end encoder counter", rightFrontDrive.getCurrentPosition() );
            telemetry.addData(" RB end encoder counter", rightBackDrive.getCurrentPosition() );
            telemetry.update();

            // Stop all motion & Turn off RUN_TO_POSITION
            moveRobot(0, 0);
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Orientations();
            turnToHeading(maxDriveSpeed,heading);
        }

    } // end of public void StrafeRobot

    /**
     *  Spin on the central axis to point in a new direction.
     *  <p>
     *  Move will stop if either of these conditions occur:
     *  <p>
     *  1) Move gets to the heading (angle)
     *  <p>
     *  2) Driver stops the OpMode running.
     *
     * @param maxTurnSpeed Desired MAX speed of turn. (range 0 to +1.0)
     * @param heading Absolute Heading Angle (in Degrees) relative to last gyro reset.
     *              0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *              If a relative angle is required, add/subtract from current heading.
     */
    public void turnToHeading(double maxTurnSpeed, double heading) {
        //  Orientations();
        double TempThreshold = 1;
        // Run getSteeringCorrection() once to pre-calculate the current error
        getSteeringCorrection(heading, P_DRIVE_GAIN);

        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && (Math.abs(headingError) > TempThreshold)) {

            // Determine required steering to keep on heading
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);

            // Clip the speed to the maximum permitted value.
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);

            // Pivot in place by applying the turning correction
            moveRobot(0, turnSpeed);

//            telemetry.addData("HeadingErr", headingError);
//            telemetry.addData("HeadinggetThresh",TempThreshold);
//            telemetry.update();
        } // end of while loop

        // Stop all motion;
        moveRobot(0, 0);
    } // end of public void turnToHeading

    /**
     *  Obtain & hold a heading for a finite amount of time
     *  <p>
     *  Move will stop once the requested time has elapsed
     *  <p>
     *  This function is useful for giving the robot a moment to stabilize its heading between movements.
     *
     * @param maxTurnSpeed      Maximum differential turn speed (range 0 to +1.0)
     * @param heading    Absolute Heading Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     * @param holdTime   Length of time (in seconds) to hold the specified heading.
     */
    public void holdHeading(double maxTurnSpeed, double heading, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        // keep looping while we have time remaining.
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Determine required steering to keep on heading
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);

            // Clip the speed to the maximum permitted value.
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);

            // Pivot in place by applying the turning correction
            moveRobot(0, turnSpeed);

            // Display drive status for the driver.
            sendTelemetry(false);
        } // end of while loop

        // Stop all motion;
        moveRobot(0, 0);
    } // end of public void holdHeading

    // **********  LOW Level driving functions.  ********************

    /**
     * Use a Proportional Controller to determine how much steering correction is required.
     *
     * @param desiredHeading        The desired absolute heading (relative to last heading reset)
     * @param proportionalGain      Gain factor applied to heading error to obtain turning power.
     * @return                      Turning power needed to get to required heading.
     */
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

    /**
     * Take separate drive (fwd/rev) and turn (right/left) requests,
     * combines them, and applies the appropriate speed commands to the left and right wheel motors.
     * @param drive forward motor speed
     * @param turn  clockwise turning motor speed.
     */
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

        leftFrontDrive.setPower(leftSpeed * 0.85);
        rightFrontDrive.setPower(rightSpeed);
        leftBackDrive.setPower(leftSpeed);
        rightBackDrive.setPower(rightSpeed);
    } // end of public void moveRobot

    public void RotateRoboto(double drive, double turn, Boolean isLeft ) {
//        driveSpeed = drive;     // save this value as a class member so it can be used by telemetry.
//        turnSpeed  = turn;      // save this value as a class member so it can be used by telemetry.
//        leftSpeed  = drive - turn;
//        rightSpeed = drive + turn;
        leftFrontDrive.setPower(drive + ((isLeft ? 1 : -1) * turn));
        rightFrontDrive.setPower(drive + ((isLeft ? 1 : -1) * turn));
        leftBackDrive.setPower(drive + ((isLeft ? -1 : 1) * turn));
        rightBackDrive.setPower(drive + ((isLeft ? -1 : 1) * turn));

        // Scale speeds down if either one exceeds +/- 1.0;
//        double max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
//        if (max > 1.0)
//        {
//            leftSpeed /= max;
//            rightSpeed /= max;
//        }



//        leftFrontDrive.setPower((isLeft ? -1 : 1) * leftSpeed);
//        rightFrontDrive.setPower((isLeft ? 1 : -1) * rightSpeed);
//        leftBackDrive.setPower((isLeft ? 1 : -1) * leftSpeed);
//        rightBackDrive.setPower((isLeft ? -1 : 1) * rightSpeed);
    }

    /**
     *  Display the various control parameters while driving
     *
     * @param straight  Set to true if we are driving straight, and the encoder positions should be included in the telemetry.
     */
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
        telemetry.addData("Wheel Speeds L : R", "%5.2f : %5.2f", leftSpeed, rightSpeed);
        telemetry.update();

    } // end of private void sendTelemetry
    /**
     * read the Robot heading directly from the IMU (in degrees)
     */
    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    } // end of public double getHeading

    public void Wait(double seconds) {
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesn't need anything
        } // end of while loop
    } // end of public void Wait
} // end of public class

