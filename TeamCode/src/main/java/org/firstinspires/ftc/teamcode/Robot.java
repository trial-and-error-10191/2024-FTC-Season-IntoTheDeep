// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public DriveTrain driveTrain;
    // This combines all the subsystems.
    // Nonsense change to test 'git push' through Android Studio
    public Robot(HardwareMap hwMap) {
        driveTrain = new DriveTrain(hwMap);
    }
}