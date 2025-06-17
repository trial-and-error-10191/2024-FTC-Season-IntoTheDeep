package org.firstinspires.ftc.teamcode.StateMachine;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class SampleClawSM {
    boolean ClawOpen = false;
    boolean lastInput = false;

    static double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS = 1.0;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position
    static final double openPos = 0.8;    // servo position for open claw
    static final double closePos = 0.47;     // servo position for closed claw
    double rotatePosition = 0.5;                  // Start at halfway position
    double extendPosition = 0.8;                  // Start at halfway position
    private Telemetry telemetry = null;

    boolean testVar = false;


    // Define class members
    public Servo servoClamp;
    public Servo servoExtend;
    public Servo servoRotation;

    public SampleClawSM(HardwareMap hwMap, Telemetry telemetry) {
        // initiates servo name
        servoClamp = hwMap.get(Servo.class, "claw_Clamp");
        servoExtend = hwMap.get(Servo.class, "claw_Extend");
        servoRotation = hwMap.get(Servo.class, "claw_Rotation");
        this.telemetry = telemetry;
    }

    public void setManualMode() {
        testVar = true;
    }

    public void manualRun(Gamepad gamepad1, Gamepad gamepad2) {
        testVar = false;
    }
}





