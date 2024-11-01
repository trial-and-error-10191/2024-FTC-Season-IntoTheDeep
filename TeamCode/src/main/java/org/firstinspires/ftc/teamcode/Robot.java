// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    private DriveTrain driveTrain;
    private final double MAX_DRIVE_SPEED = 0.5;
    private final double MAX_TURN_SPEED = 0.3;

    // This combines all the subsystems.
    public Robot(HardwareMap hwMap) {
        driveTrain = new DriveTrain(hwMap);
    }

    public void driveByPower(double axial, double lateral, double yaw) {
        driveTrain.driveByPower(axial, lateral, yaw);
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

    public void moveForward(double distance) {
        double currentHeading = driveTrain.getHeading();
        driveTrain.driveStraight(MAX_DRIVE_SPEED, distance, currentHeading);
    }

    public void turnToHeading(double angle) {
        driveTrain.turnToHeading(MAX_TURN_SPEED, angle);
    }

}