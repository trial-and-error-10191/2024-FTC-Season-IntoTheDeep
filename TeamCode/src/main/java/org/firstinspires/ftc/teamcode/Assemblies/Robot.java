// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Teleoperation.Bessie_TeleOp;

public class Robot {
    public Bessie_TeleOp bessieTeleOp;
    public DriveTrain driveTrain;
   // public BessieClaw bessieClaw;
    public BessieLimbArm bessieLimbArm;
    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        bessieTeleOp = new Bessie_TeleOp();
        driveTrain = new DriveTrain(hwMap, telemetry);
        //bessieClaw = new BessieClaw(hwMap);
        bessieLimbArm = new BessieLimbArm(hwMap, telemetry);
    }
}