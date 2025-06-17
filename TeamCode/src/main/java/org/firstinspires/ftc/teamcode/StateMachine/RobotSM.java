// This file is a system file.
package org.firstinspires.ftc.teamcode.StateMachine;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotSM {
    public DriveTrainSM driveTrain;
    public AscentMechanismSM ascentMechanism;
    public SampleClawSM sampleClaw;
    public LimbArmSM limbArm;
    private Telemetry telemetry;

    public RobotSM(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrainSM(hwMap, telemetry);
        ascentMechanism = new AscentMechanismSM(hwMap, telemetry);
        sampleClaw = new SampleClawSM(hwMap, telemetry);
        limbArm = new LimbArmSM(hwMap, telemetry);
        this.telemetry = telemetry;
    }

    private RobotState state = RobotState.DEFAULT;

    private enum RobotState {
        MANUAL, // Full manual control of robot
        DEFAULT // debugging state, robot in this state shouldn't do anything
    }

    public void updateState(Gamepad gamepad) {
        // How do we want to change state?
        // Option 1) Reserve one button for each state
        // Option 2) Create loop of states, have one button to more forward and separate one to move backward
        // Option 3) Use sensor input to automatically switch between states (somehow)
        state = getState(gamepad);
        updateSubsystems();
    }

    // This is assuming we pick Option 1 listed in updateState function.
    // May need to take a different form otherwise.
    private RobotState getState(Gamepad gamepad) {
        if (gamepad.x) {
            return RobotState.MANUAL;
        } else {
            return RobotState.DEFAULT;
        }
    }

    // This is to update subsystem properties to align with current robot states
    // Examples: max lift height, lift rotation speed, etc.
    private void updateSubsystems() {
        switch (state) {
            case MANUAL:
                // Change subsytem properties to allow manual control
                driveTrain.setManualMode();
                ascentMechanism.setManualMode();
                sampleClaw.setManualMode();
                limbArm.setManualMode();
                break;
            case DEFAULT:
                telemetry.addData("Warning:", "In Default (Do Nothing) State");
                break;
            default:
                telemetry.addData("Error:", "Unrecognized State, Unable to update subsystems");
                break;
        }
    }

    public void run(Gamepad gamepad1, Gamepad gamepad2) {
        switch (state) {
            case MANUAL:
                driveTrain.manualRun(gamepad1, gamepad2);
                ascentMechanism.manualRun(gamepad1, gamepad2);
                sampleClaw.manualRun(gamepad1, gamepad2);
                limbArm.manualRun(gamepad1, gamepad2);
                telemetry.addData("State:", "MANUAL");
                break;
            case DEFAULT: // explicit state to do nothing in
                telemetry.addData("State:", "DEFAULT");
                break;
            default:
                telemetry.addData("Error:", "Unrecognized State. Unable to move robot.");
                break;
        }
    }
}