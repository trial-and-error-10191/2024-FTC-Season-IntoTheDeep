package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BessieClaw {
    boolean ClawOpen = false;
    boolean lastInput = false;

    static double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS = 1.0;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position
    static final double openPos = 0.8;    // servo position for open claw
    static final double closePos = 0.47;     // servo position for closed claw
    double rotatePosition = 0.5;                  // Start at halfway position
    double extendPosition = 0.8;                  // Start at halfway position


    // Define class members
    public Servo servoClamp;

    public BessieClaw(HardwareMap hwMap) {
        // initiates servo name
        servoClamp = hwMap.get(Servo.class, "claw_Clamp");
    }

    public void clawClamp(boolean open) {
        if (!lastInput && open) {
            ClawOpen = !ClawOpen;
            if (ClawOpen) {
                servoClamp.setPosition(openPos);
            } else {
                servoClamp.setPosition(closePos);
            }
        }
        lastInput = open;
    }

    public void clawExtend(boolean extend, boolean contracting, boolean slow) {
        if (slow) {
            INCREMENT = 0.0005;
        }
        if (!slow) {
            INCREMENT = 0.01;
        }
        if (extend) {                        // Makes the claw extend up?
            extendPosition += INCREMENT;
            if (extendPosition >= MAX_POS) {
                extendPosition = MAX_POS;
            }
        } else if (contracting) {              // Makes the claw extend down?
            extendPosition -= INCREMENT;
            if (extendPosition <= MIN_POS) {
                extendPosition = MIN_POS;
            }
        }
    }

    public void OpenClaw() {
// opens the claw
        servoClamp.setPosition(openPos);
    }

    public void CloseClaw() {
// closes the claw
        servoClamp.setPosition(closePos);
    }
}
