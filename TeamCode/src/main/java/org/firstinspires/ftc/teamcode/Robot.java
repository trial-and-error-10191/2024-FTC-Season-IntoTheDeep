// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    private DriveTrain driveTrain;

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
}