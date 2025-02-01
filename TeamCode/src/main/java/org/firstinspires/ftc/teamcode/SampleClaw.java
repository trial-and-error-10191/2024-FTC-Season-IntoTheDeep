package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SampleClaw {
    boolean ClawOpen = false;
    boolean lastInput = false;

    static double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    static final double openPos     =  10.0;    // servo position for open claw
    static final double closePos    =  0.0;     // servo position for closed claw
    double rotatePosition = 0;                  // Start at halfway position
    double extendPosition = 0;                  // Start at halfway position


    // Define class members
    Servo servoClamp;
    Servo servoExtend;
    Servo servoRotation;

    public SampleClaw(HardwareMap hwMap) {
        // initiates servo name
        servoClamp    = hwMap.get(Servo.class, "claw_Clamp");
        servoExtend   = hwMap.get(Servo.class, "claw_Extend");
        servoRotation = hwMap.get(Servo.class, "claw_Rotation");
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
    public void clawClampAuto(boolean open) {
        if (open) {
            servoClamp.setPosition(openPos);
        }
        else {
            servoClamp.setPosition(closePos);
        }
    }
//    public void clawClampAuto(boolean clawArmClamp) {        // claw clamping for autonomous
//        if (clawArmClamp) {
//            ClawOpen = true;
//            servoClamp.setPosition(openPos);
//        }
//        else { // Makes the claw close
//            ClawOpen = false;
//            servoClamp.setPosition(closePos);
//        }
//    }

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
        }
        else if (contracting) {              // Makes the claw extend down?
            extendPosition -= INCREMENT;
            if (extendPosition <= MIN_POS ) {
                extendPosition = MIN_POS;
            }
        }
        servoExtend.setPosition(extendPosition);
    }
    public void clawExtendAuto(boolean extend, boolean contracting) {
        if (extend) {                        // Makes the claw extend up?
            extendPosition += INCREMENT;
            if (extendPosition >= MAX_POS) {
                extendPosition = MAX_POS;
            }
        }
        else if (contracting) {              // Makes the claw extend down?
            extendPosition -= INCREMENT;
            if (extendPosition <= MIN_POS ) {
                extendPosition = MIN_POS;
            }
        }
        servoExtend.setPosition(extendPosition);
    }

//    public void clawExtendAuto(double clawArmPosition) {    // claw extending for autonomous
//        if (extendPosition < clawArmPosition) {             // Tells claw to rise
//            extendPosition += INCREMENT;
//            if (extendPosition >= MAX_POS) {
//                extendPosition = MAX_POS;
//            }
//        }
//        else if (extendPosition > clawArmPosition) {        // Makes the claw lower
//            extendPosition -= INCREMENT;
//            if (extendPosition <= MIN_POS ) {
//                extendPosition = MIN_POS;
//            }
//        }
//        servoExtend.setPosition(extendPosition);
//    }

    public void clawRotate(float left, float right, boolean slow) {
        if (slow) {
            INCREMENT = 0.0005;
        }
        if (!slow) {
            INCREMENT = 0.01;
        }
        if (left > 0) {                                     // rotates claw to the left
            rotatePosition += INCREMENT;
            if (rotatePosition >= MAX_POS) {
                rotatePosition = MAX_POS;
            }
        }
        else if (right > 0) {                               // rotates claw to the right
            rotatePosition -= INCREMENT;
            if (rotatePosition <= MIN_POS ) {
                rotatePosition = MIN_POS;
            }
        }
        servoRotation.setPosition(rotatePosition);
    }
    public void clawRotateAuto(float left, float right) {
        if (left > 0) {                                     // rotates claw to the left
            rotatePosition += INCREMENT;
            if (rotatePosition >= MAX_POS) {
                rotatePosition = MAX_POS;
            }
        }
        else if (right > 0) {                               // rotates claw to the right
            rotatePosition -= INCREMENT;
            if (rotatePosition <= MIN_POS ) {
                rotatePosition = MIN_POS;
            }
        }
        servoRotation.setPosition(rotatePosition);
    }

//    public void clawRotateAuto(double clawArmRotate) {      // claw rotation for autonomous
//        if (rotatePosition < clawArmRotate) {               // claw rotation to the left
//            rotatePosition += INCREMENT;
//            if (rotatePosition >= MAX_POS) {
//                rotatePosition = MAX_POS;
//            }
//        }
//        else if (rotatePosition > clawArmRotate) {          // claw rotation to the right
//            rotatePosition -= INCREMENT;
//            if (rotatePosition <= MIN_POS ) {
//                rotatePosition = MIN_POS;
//            }
//        }
//        servoRotation.setPosition(rotatePosition);
//    }

   public void OpenClaw() {
// opens the claw
        servoClamp.setPosition(openPos);
    }
  public void CloseClaw() {
// closes the claw
        servoClamp.setPosition(closePos);
    }

   public void RotateClaw(double Posistion_Claw) {
// rotates the claw to a given posistion
        servoRotation.setPosition(Posistion_Claw);
    }

   public void ExtendClaw(double Posistion_Extend) {
// rotates the claw in a different axis
        servoExtend.setPosition(Posistion_Extend);
    }
}
