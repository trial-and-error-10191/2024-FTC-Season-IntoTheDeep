package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SampleClaw {
    boolean ClawOpen;

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
        //servo.setPosition(position);
    }

    public void clawClamp(boolean open) {
        if (open) { // Makes the claw open
            ClawOpen = true;
            servoClamp.setPosition(1);
        }
        else { // Makes the claw close
            ClawOpen = false;
            servoClamp.setPosition(2);
        }
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
        // Set the servo to the new position and pause;
        servoExtend.setPosition(position);
    }
    public void clawRotate(float left, float right) {
        if (left > 0) {

        }
        servoRotation.setPosition(position);
    }
}
