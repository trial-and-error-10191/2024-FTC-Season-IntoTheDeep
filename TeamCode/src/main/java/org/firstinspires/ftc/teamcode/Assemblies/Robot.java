// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    public DriveTrain driveTrain;
    public Lift lift;
    public Claw claw;

    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrain(hwMap, telemetry);
        lift = new Lift(hwMap, telemetry);
        claw = new Claw(hwMap, telemetry);
    }
}