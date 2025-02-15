package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SampleClaw {
    boolean ClawOpen = false;
    boolean lastInput = false;

    static double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS = 1.0;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position
    static final double openPos = 0.5;    // servo position for open claw
    static final double closePos = 1.0;     // servo position for closed claw
    double rotatePosition = 0.5;                  // Start at halfway position
    double extendPosition = 0.8;                  // Start at halfway position
    public boolean slow = false;
    boolean PreviousPress = false;

    // Define class members
    public Servo servoClamp;
    Servo servoExtend;
    Servo servoRotation;

    private enum ClawState {
        MANUAL,
        SAMPLE_HUNTING,
        SPECIMEN_HUNTING;
    }
public void SlowToggle(Gamepad gamepad2) {
    if (gamepad2.y && !PreviousPress) {
        slow = !slow;
    }
    PreviousPress = gamepad2.y;
}

    ClawState state;

    public SampleClaw(HardwareMap hwMap) {
        // initiates servo name
        servoClamp = hwMap.get(Servo.class, "claw_Clamp");
        servoExtend = hwMap.get(Servo.class, "claw_Extend");
        servoRotation = hwMap.get(Servo.class, "claw_Rotation");
        state = ClawState.MANUAL;
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

    public void clawExtend(boolean extend, boolean contracting) {
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

        servoExtend.setPosition(extendPosition);
    }

    public void clawRotate(float left, float right) {
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
        } else if (right > 0) {                               // rotates claw to the right
            rotatePosition -= INCREMENT;
            if (rotatePosition <= MIN_POS) {
                rotatePosition = MIN_POS;
            }
        }
        servoRotation.setPosition(rotatePosition);
    }

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

    public void PositionServoDown(double RotationEC) {
        double Theta = (RotationEC / 22.64) + 90;
        double servoAngle = 90 - Theta;
        double requiredServoPosistion = (servoAngle * 0.0041) - 0.2;
        servoExtend.setPosition(requiredServoPosistion);
    }

    public void PositionServoHorizontal(double RotationEC) {
        double Theta = (RotationEC / 22.64) + 90;
        double servoAngle = 180 - Theta;
        double requiredServoPosistion = (servoAngle * 0.0041) - 0.2;
        servoExtend.setPosition(requiredServoPosistion);
        servoRotation.setPosition(0.7);
    }


    public void updateState(Gamepad gamepad, int rotationPosition) {
        // based on gamepad and rotation position, set claw state
        boolean isUp = rotationPosition > -1300;
        if (gamepad.x) {
                state = ClawState.MANUAL;
        }
        if (gamepad.y) {
            if (!isUp)
                state = ClawState.SAMPLE_HUNTING;
        }
        if (gamepad.b) {
                state = ClawState.SPECIMEN_HUNTING;
        }
    }

        public void move (Gamepad gamepad2, double rotationPosition){
if (state == ClawState.MANUAL) {
    // Makes the claw open/close
               clawClamp(gamepad2.a);
               // Makes the claw extend/contract
               clawExtend(gamepad2.left_bumper, gamepad2.right_bumper);
               // Makes the claw rotate
               clawRotate(gamepad2.left_trigger, gamepad2.right_trigger);
} else if (state == ClawState.SAMPLE_HUNTING) {
    PositionServoDown(rotationPosition);
    clawRotate(gamepad2.left_trigger, gamepad2.right_trigger);
    clawClamp(gamepad2.a);
} else if (state == ClawState.SPECIMEN_HUNTING){
    PositionServoHorizontal(rotationPosition);
    clawClamp(gamepad2.a);
}
        }
    }





