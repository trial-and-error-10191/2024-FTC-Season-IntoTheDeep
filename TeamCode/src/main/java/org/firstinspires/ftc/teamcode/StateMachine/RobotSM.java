// This file is a system file.
package org.firstinspires.ftc.teamcode.StateMachine;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotSM {
    public DriveTrainSM driveTrain;
    public SampleClawSM sampleClaw;
    public LimbArmSM limbArm;
    private Telemetry telemetry;

    public RobotSM(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrainSM(hwMap, telemetry);
        sampleClaw = new SampleClawSM(hwMap, telemetry);
        limbArm = new LimbArmSM(hwMap, telemetry);
        this.telemetry = telemetry;
    }

    private RobotState state = RobotState.DEFAULT;

    private enum RobotState {
        MANUAL,       // Full manual control of robot
        SAMPLE_PLACE, // Sets the robot up to place a sample
        DEFAULT       // debugging state, robot in this state shouldn't do anything
    }

    public void updateState(Gamepad gamepad, Gamepad gamepad2) {
        // How do we want to change state?
        // Option 1) Reserve one button for each state
        // Option 2) Create loop of states, have one button to more forward and separate one to move backward
        // Option 3) Use sensor input to automatically switch between states (somehow)
        state = getState(gamepad, gamepad2);
        updateSubsystems();
    }

    // This is assuming we pick Option 1 listed in updateState function.
    // May need to take a different form otherwise.
    private RobotState getState(Gamepad gamepad, Gamepad gamepad2) {
        if (gamepad.y) {
            return RobotState.MANUAL;
        } else if (gamepad.x) {
            return RobotState.DEFAULT;
        } else if (gamepad2.dpad_up) {
            return RobotState.SAMPLE_PLACE;
        } else {
            return state;
        }
        //return RobotState.MANUAL;
    }

    // This is to update subsystem properties to align with current robot states
    // Examples: max lift height, lift rotation speed, etc.
    private void updateSubsystems() {
        switch (state) {
            case MANUAL:
                // Change subsytem properties to allow manual control
                driveTrain.setManualMode();
                sampleClaw.setManualMode();
                limbArm.setManualMode();
                break;
            case SAMPLE_PLACE:
                driveTrain.setManualMode();
                sampleClaw.setManualMode();
                limbArm.setSamplePlaceMode();
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
                driveTrain.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
                sampleClaw.clawClamp(gamepad2.a);
                sampleClaw.clawExtend(gamepad2.left_bumper, gamepad2.right_bumper,  gamepad2.y);
                sampleClaw.clawRotate(gamepad2.left_trigger, gamepad2.right_trigger,  gamepad2.y);
                limbArm.RunMotor(-gamepad2.left_stick_y);
                limbArm.rotateByPower(-gamepad2.right_stick_y);
                limbArm.spoolCorrection(gamepad1.dpad_up, gamepad1.dpad_down);
                telemetry.addData("State:", "MANUAL");
                break;
            case SAMPLE_PLACE:
                driveTrain.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
                sampleClaw.clawClamp(gamepad2.a);
                sampleClaw.clawExtend(gamepad2.left_bumper, gamepad2.right_bumper,  gamepad2.y);
                sampleClaw.clawRotate(gamepad2.left_trigger, gamepad2.right_trigger,  gamepad2.y);
                limbArm.rotateByPower(-gamepad2.right_stick_y);
                limbArm.spoolCorrection(gamepad1.dpad_up, gamepad1.dpad_down);
                if (limbArm.limbExtend.getCurrentPosition() < limbArm.maxExtendPos) { // Making the robot stay at the max extension it can go to
                    limbArm.RunMotor(limbArm.maxExtendPos);
                }
                telemetry.addData("State:", "SAMPLE_PLACE");
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