// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.UtilityOctoQuadConfigMenu;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    public DriveTrain driveTrain;
    public AscentMechanism ascentMechanism;
    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {

        driveTrain = new DriveTrain(hwMap, telemetry);
        ascentMechanism = new AscentMechanism(hwMap);
    }
}