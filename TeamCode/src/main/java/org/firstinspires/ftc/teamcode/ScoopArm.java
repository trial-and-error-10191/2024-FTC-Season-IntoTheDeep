package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ScoopArm {
    CRServo CRservo;
    DigitalChannel limitMax;
    DigitalChannel limitLower;
    ElapsedTime runtimeTimer = new ElapsedTime();

    public ScoopArm(HardwareMap hwMap) {
        // initiates servo name
        CRservo = hwMap.get(CRServo.class, "specimen_scoop");
        limitMax = hwMap.get(DigitalChannel.class, "limitSwitchMax");
        limitLower = hwMap.get(DigitalChannel.class, "limitSwitchLower");
    }

    public void AutoScoopArm(double power, double time) {
        runtimeTimer.reset();
        while (runtimeTimer.time() < time && limitMax.getState() && limitLower.getState()) {
            CRservo.setPower(power);
        }
        CRservo.setPower(0);
    }

    public void scoopArmPosition(float robot_position_rise, float robot_position_lower) {
        if (robot_position_rise > 0) { // Makes the robot's grabbing arm rise
            if (!limitMax.getState()) { // Max limit
                CRservo.setPower(0);
            }
            else if (limitMax.getState()) {
                CRservo.setPower(robot_position_rise);
            }
        }
        else if (robot_position_lower > 0) { // Makes the robot's grabbing arm lower
            if (!limitLower.getState()) { // Lower limit
                CRservo.setPower(0);
            }
            else if (limitLower.getState()){
                CRservo.setPower(-robot_position_lower);
            }
        }
        else { // Makes the robot stop if the powers are 0
            CRservo.setPower(0);
        }
    }
    public double getPower() {
        return CRservo.getPower();
    }
    public boolean getLimitMax() {
        return limitMax.getState();
    }
    public boolean getLimitLower() {
        return limitLower.getState();
    }
}
