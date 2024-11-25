package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ScoopArm {

    CRServo CRservo;

    public ScoopArm(HardwareMap hwMap) {
        // initiates servo name
        CRservo = hwMap.get(CRServo.class, "specimen_scoop");
    }

    public void scoopArmPosition(float robot_position_rise, float robot_position_lower) {
        if (robot_position_rise > 0) { // Makes the robot's grabbing arm rise
            CRservo.setPower(robot_position_rise);
        }
        else if (robot_position_lower > 0) { // Makes the robot's grabbing arm lower
            CRservo.setPower(-robot_position_lower);
        }
    }
}
