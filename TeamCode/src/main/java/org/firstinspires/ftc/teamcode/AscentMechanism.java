// This is the ascent mechanism subsystem file.
// All ascent mechanism stuff should be found here.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AscentMechanism {

    static final double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int CYCLE_MS = 50;     // period of each cycle
    static final double MAX_POS = 1.0;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position

    // Define class members
    Servo servo;
    double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;

    public AscentMechanism(HardwareMap hwMap) {

        //initiates servo name
        servo = hwMap.get(Servo.class, "left_hand");

        if (rampUp) {
            // Keep stepping up until we hit the max value.
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            } else {
                // Keep stepping down until we hit the min value.
                position -= INCREMENT;
                if (position <= MIN_POS) {
                    position = MIN_POS;
                }
            }
        }
    }
}