// This is the drive train subsystem file.
// All drive train stuff should be found here.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/** @noinspection ALL*/
public class DriveTrain {

    DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;


    // All subsystems should have a hardware function that labels all of the hardware required of it.
    public DriveTrain(HardwareMap hwMap) {

        // Initializes motor names:
        leftFrontDrive = hwMap.get(DcMotor.class, "leftFront");
        leftBackDrive = hwMap.get(DcMotor.class, "leftBack");
        rightFrontDrive = hwMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hwMap.get(DcMotor.class, "rightBack");

        // Initializes motor directions:
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    // This function needs an axial, lateral, and yaw input. It uses this input to drive the drive train motors.
    // The last two variables are for direction switching.
    public void drive(double axial, double lateral, double yaw) {
        double sensitivity = 0.6;
        double leftFrontPower = 0;
        double rightFrontPower = 0;
        double leftBackPower = 0;
        double rightBackPower = 0;

        leftFrontPower = axial + lateral + yaw;
        rightFrontPower = axial - lateral - yaw;
        leftBackPower = axial - lateral + yaw;
        rightBackPower = axial + lateral - yaw;

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

        // The next four lines gives the calculated power to each motor.
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);
    }
    public void moveForward(int distance, double seconds) { // Makes the robot move forward
        leftFrontDrive.setPower(1);
        rightFrontDrive.setPower(1);
        leftBackDrive.setPower(1);
        rightBackDrive.setPower(1);
    }
    public void strafeLeft(int distance, double seconds) { // Makes the robot strafe to the left
        leftFrontDrive.setPower(1);
        rightFrontDrive.setPower(-1);
        leftBackDrive.setPower(1);
        rightBackDrive.setPower(-1);
    }
    public void strafeRight(int distance, double seconds) { // Makes the robot strafe to the right
        leftFrontDrive.setPower(-1);
        rightFrontDrive.setPower(1);
        leftBackDrive.setPower(-1);
        rightBackDrive.setPower(1);
    }
    public void turnClockwise(int distance, double seconds) { // Makes the robot turn clockwise
        leftFrontDrive.setPower(1);
        rightFrontDrive.setPower(1);
        leftBackDrive.setPower(-1);
        rightBackDrive.setPower(-1);
    }
    public void turnCounterClockwise(int distance, double seconds) { // Makes the robot turn clockwise
        leftFrontDrive.setPower(-1);
        rightFrontDrive.setPower(-1);
        leftBackDrive.setPower(1);
        rightBackDrive.setPower(1);
    }
    public void stop(double seconds) { // Makes the robot stop whenever this function is called
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
    public void autonomous1 () {
        moveForward(3,0.5);
        stop(0.1);
        strafeRight(4,0.5);
        stop(0.1);
        moveForward(4, 0.5);
        stop(0.1);
        turnCounterClockwise(2, 0.5);
        stop(0.1);
        moveForward(7, 0.5);
        stop(0.1);
        strafeLeft(4,0.5);
        stop(0.1);
        moveForward(-4,0.5);
        stop(0.1);
    }
}