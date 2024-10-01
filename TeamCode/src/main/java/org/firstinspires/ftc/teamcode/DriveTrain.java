// This is the drive train subsystem file.
// All drive train stuff should be found here.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/** @noinspection ALL*/
public class DriveTrain {

    DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;
    private ElapsedTime runtime = new ElapsedTime();
    // for move vertically only
    double motor;

    // All subsystems should have a hardware function that labels all of the hardware required of it.
    public DriveTrain(HardwareMap hwMap) {

        // Initializes motor names:
        leftFrontDrive = hwMap.get(DcMotor.class, "leftFront");
        leftBackDrive = hwMap.get(DcMotor.class, "leftBack");
        rightFrontDrive = hwMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hwMap.get(DcMotor.class, "rightBack");

        // Initializes motor directions:
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
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

    public void moveVertically(double power, double seconds) { // Makes the robot go forward if the number is a positive, but moves it backward when it's a negative number
        runtime.reset();
        while (runtime.time() < seconds) {
            leftFrontDrive.setPower(power);
            rightFrontDrive.setPower(power);
            leftBackDrive.setPower(power);
            rightBackDrive.setPower(power);
        }
    }

    public void strafeLeft(double power, double seconds) { // Makes the robot strafe to the left
        runtime.reset();
        while (runtime.time() < seconds) {
            leftFrontDrive.setPower(-power);
            rightFrontDrive.setPower(power);
            leftBackDrive.setPower(power);
            rightBackDrive.setPower(-power);
        }
    }

    public void strafeRight(double power, double seconds) { // Makes the robot strafe to the right
        runtime.reset();
        while (runtime.time() < seconds) {
            leftFrontDrive.setPower(power);
            rightFrontDrive.setPower(-power);
            leftBackDrive.setPower(-power);
            rightBackDrive.setPower(power);
        }
    }

    public void turnClockwise(double power, double seconds) { // Makes the robot turn clockwise
        runtime.reset();
        while (runtime.time() < seconds) {
            leftFrontDrive.setPower(power);
            rightFrontDrive.setPower(-power);
            leftBackDrive.setPower(power);
            rightBackDrive.setPower(-power);
        }
    }

    public void turnCounterClockwise(double power, double seconds) { // Makes the robot turn clockwise
        runtime.reset();
        while (runtime.time() < seconds) {
            leftFrontDrive.setPower(-power);
            rightFrontDrive.setPower(power);
            leftBackDrive.setPower(-power);
            rightBackDrive.setPower(power);
        }
    }

    public void stop() { // Makes the robot stop whenever this function is called
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }

    public void Wait(double seconds) {
        runtime.reset();
        while (runtime.time() < seconds) {
           // this statement is supposed to be empty.
        }
    }
    public void autonomous1() { // Autonomous for IntoTheDeep
        strafeRight(0.4, 0.3);
        stop();
        Wait(1);
        moveVertically(0.9, 1.35);
        stop();
        Wait(0.1);
        moveVertically(-0.1, 0.1);
        stop();
        Wait(0.3);
        moveVertically(-0.9, 1.5);
        stop();
        Wait(0.1);
        moveVertically(0.1, 0.1);
        stop();
        Wait(0.3);
//        strafeLeft(0.8, 0.5);
//        moveVertically(0.5, 0.5);
//        stop();
//        Wait(1);
//        moveVertically(1, 0.5);
//        stop();
//        Wait(20);
//        turnCounterClockwise(2, 0.5);
//        stop();
//        moveVertically(7, 0.5);
//        stop();
//        strafeLeft(4, 0.5);
//        stop();
//        moveVertically(-4, 0.5);
//        stop();
    }
}