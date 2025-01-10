package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SampleClaw {
    boolean ClawOpen = false;

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    Servo servoClamp;
    Servo servoExtend;
    Servo servoRotation;

    double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position

    public SampleClaw(HardwareMap hwMap) {

        // initiates servo name
        servoClamp = hwMap.get(Servo.class, "claw_Clamp");
        servoExtend = hwMap.get(Servo.class, "claw_Extend");
        servoRotation = hwMap.get(Servo.class, "claw_Rotation");
    }

    public void clawClamp(boolean open) {
        if (open) { // Makes the claw open
            ClawOpen = true;
            servoClamp.setPosition(1);
        }
        else { // Makes the claw close
            ClawOpen = true;
            servoClamp.setPosition(2);
        }
        servoClamp.setPosition(position);
    }
    public void clawClampAuto(boolean clawArmClamp) { // claw clamping for autonomous
        if (clawArmClamp) {
            ClawOpen = true;
            servoClamp.setPosition(1);
        }
        else { // Makes the claw close
            ClawOpen = true;
            servoClamp.setPosition(2);
        }
        // Set the servo to the new position and pause;
        servoClamp.setPosition(position);
    }
    public void clawExtend(boolean extend, boolean contracting) {
        if (extend) { // Makes the claw extend
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (contracting) { // Makes the robot's arm lower
            position -= INCREMENT;
            if (position <= MIN_POS ) { // Makes the claw go back from extending
                position = MIN_POS;
            }
        }
        else {
            position = 0;
        }
        servoExtend.setPosition(position);
    }
    public void clawExtendAuto(double clawArmPosition) { // claw extending for autonomous
        if (position < clawArmPosition) { // Tells arm to rise
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (position > clawArmPosition) { // Makes the robot's arm lower
            position -= INCREMENT;
            if (position <= MIN_POS ) {
                position = MIN_POS;
            }
        }
        servoExtend.setPosition(position);
    }
    public void clawRotate(float left, float right) {
        if (left > 0) {
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (right > 0) {
            position -= INCREMENT;
            if (position <= MIN_POS ) { // Makes the claw go back from extending
                position = MIN_POS;
            }
        }
        else {
            position = 0;
        }
        servoRotation.setPosition(position);
    }
    public void clawRotateAuto(double clawArmRotate) { // claw rotation for autonomous
        if (position < clawArmRotate) { // Tells arm to rise
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (position > clawArmRotate) { // Makes the robot's arm lower
            position -= INCREMENT;
            if (position <= MIN_POS ) {
                position = MIN_POS;
            }
        }
        servoRotation.setPosition(position);
    }
}
