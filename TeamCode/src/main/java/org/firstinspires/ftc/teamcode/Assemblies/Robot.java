// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Teleoperation.Bobbette_TeleOp;

public class Robot {
    public Bobbette_TeleOp bobbetteTeleOp;
    public DriveTrain driveTrain;
    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        bobbetteTeleOp = new Bobbette_TeleOp();
        driveTrain = new DriveTrain(hwMap, telemetry);
    }
}