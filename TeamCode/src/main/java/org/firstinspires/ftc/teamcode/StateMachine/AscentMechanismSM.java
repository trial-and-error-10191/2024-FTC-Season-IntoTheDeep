package org.firstinspires.ftc.teamcode.StateMachine;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AscentMechanismSM {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    Servo   servo;
    Telemetry telemetry;

    boolean testVar = false;

    double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position

    public AscentMechanismSM(HardwareMap hwMap, Telemetry telemetry) {

        // initiates servo name
        servo = hwMap.get(Servo.class, "left_hand");
        this.telemetry = telemetry;
    }

    public void setManualMode() {
        testVar = true;
    }

    public void manualRun(Gamepad gamepad1, Gamepad gamepad2) {
        testVar = false;
    }
}
