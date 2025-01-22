// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    private DriveTrain driveTrain;
    private SampleAndSpecimenManipulator manipulator;
    private Telemetry telemetry;
    private final double MAX_DRIVE_SPEED = 0.5;
    private final double MAX_TURN_SPEED = 0.3;

    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrain(hwMap, telemetry);
        manipulator = new SampleAndSpecimenManipulator(hwMap, telemetry);
        this.telemetry = telemetry;
    }

    public void update(Gamepad gamepad) {
        driveTrain.update(gamepad);
        manipulator.update(gamepad);
    }

    public void driveByPower(Gamepad gamepad) {
        driveTrain.driveByPower(gamepad);
    }

    public double getLeftFrontMotorPower() {
        return driveTrain.getLeftFrontMotorPower();
    }
    public double getRightFrontMotorPower() {
        return driveTrain.getRightFrontMotorPower();
    }
    public double getLeftBackMotorPower() {
        return driveTrain.getLeftBackMotorPower();
    }
    public double getRightBackMotorPower() {
        return driveTrain.getRightBackMotorPower();
    }

    public void resetEncoders() {
        driveTrain.resetEncoders();
    }

    public void driveUsingEncoders() {
        driveTrain.runUsingEncoders();
    }

    public void resetImu() {
        driveTrain.resetImu();
    }

    public void moveForward(double distanceInInches) {
        double currentHeading = driveTrain.getHeading();
        driveTrain.driveStraight(MAX_DRIVE_SPEED, distanceInInches, currentHeading);
    }

    public void turnToHeading(double angle) {
        driveTrain.turnToHeading(MAX_TURN_SPEED, angle);
    }
}