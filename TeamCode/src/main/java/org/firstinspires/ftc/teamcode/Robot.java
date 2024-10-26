// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    public DriveTrain driveTrain;
    public AscentMechanism ascentMechanism;
    public Telemetry telemetry;
    // This combines all the subsystems.
    // Nonsense change to test 'git push' through Android Studio
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrain(hwMap, telemetry);
        ascentMechanism = new AscentMechanism(hwMap);
        this.telemetry = telemetry;
    }
}