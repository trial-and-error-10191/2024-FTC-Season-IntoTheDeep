// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {

    public enum ROBOT_HUNTER {
        MANUAL,
        SAMPLE_HUNT;
    }

    Robot.ROBOT_HUNTER state;

    public DriveTrain driveTrain;
    public SampleClaw sampleClaw;
    public LimbArm limbArm;
    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrain(hwMap, telemetry);
        sampleClaw = new SampleClaw(hwMap);
        limbArm = new LimbArm(hwMap, telemetry);
        state = ROBOT_HUNTER.MANUAL;
    }

    public void updateState(Gamepad gamepad) {
        sampleClaw.updateState(gamepad, limbArm.limbRotate.getCurrentPosition());

        if (gamepad.y) {
            state = ROBOT_HUNTER.MANUAL;
            driveTrain.setModeMANUAL();
            sampleClaw.setModeMANUAL();
            limbArm.setModeMANUAL();
        }
        if (gamepad.a) {
            state = ROBOT_HUNTER.SAMPLE_HUNT;
            sampleClaw.state = SampleClaw.ClawState.SAMPLE_HUNTING;
            driveTrain.state = DriveTrain.TurnState.LEFT;
        }
    }

    public void moveClaw(Gamepad gamepad, double rotationPosition) {
        sampleClaw.move(gamepad, rotationPosition);
    }
    public void setMove(Gamepad gamepad1, Gamepad gamepad2) {
        if (state == ROBOT_HUNTER.MANUAL) {
            driveTrain.move(gamepad1);
            sampleClaw.move(gamepad2, limbArm.rotatePosition());
            limbArm.move(gamepad2);
        }
        else if (state == ROBOT_HUNTER.SAMPLE_HUNT) {
            if (limbArm.limbRotate.getCurrentPosition() > -2087) {
                limbArm.rotationPosition(-2087);
            }
            driveTrain.turnToHeading(DriveTrain.TURN_SPEED, -90);
            driveTrain.fieldControl(gamepad1, true);
            sampleClaw.PositionServoDown(limbArm.limbRotate.getCurrentPosition());
            sampleClaw.clawRotate(gamepad2.left_trigger, gamepad2.right_trigger, gamepad2.y);
            sampleClaw.clawClamp(gamepad2.a);
            limbArm.RunMotor(-gamepad2.left_stick_y);
            limbArm.rotateByPower(-gamepad2.right_stick_y * 0.5f);
        }
    }
    public boolean doesManual() { // Sets up a return statement for telemetry reasons
        if (state == ROBOT_HUNTER.MANUAL) {
            return true;
        }
        return false;
    }
}