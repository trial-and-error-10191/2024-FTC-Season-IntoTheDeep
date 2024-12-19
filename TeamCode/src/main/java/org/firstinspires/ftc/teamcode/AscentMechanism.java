package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AscentMechanism {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    Servo   servo;

    double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position

    public AscentMechanism(HardwareMap hwMap) {

        // initiates servo name
        servo = hwMap.get(Servo.class, "left_hand");
        //servo.setPosition(position);
    }

    public void rise(boolean rise, boolean lower) {
        if (rise) { // Makes the robot's arm rise
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (lower) { // Makes the robot's arm lower
            position -= INCREMENT;
            if (position <= MIN_POS ) {
                position = MIN_POS;
            }
        }
        // Set the servo to the new position and pause;
        servo.setPosition(position);
    }

    public void armPosition(double armPosition) {
        if (position < armPosition) { // Tells arm to rise
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (position > armPosition) { // Makes the robot's arm lower
            position -= INCREMENT;
            if (position <= MIN_POS ) {
                position = MIN_POS;
            }
        }
        // Set the servo to the new arm position and pause;
        servo.setPosition(position);
    }

    public void SetPosistion(double Posistion) {
        servo.setPosition(Posistion);
    }
}
