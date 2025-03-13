// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Teleoperation.Field_TeleOp;

public class Robot {

    private enum SampleFinder {
        MANUAL,
        SAMPLE_HUNT;
    }

    Robot.SampleFinder state;

    public DriveTrain driveTrain;
    public SampleClaw sampleClaw;
    public LimbArm limbArm;
    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        driveTrain = new DriveTrain(hwMap, telemetry);
        sampleClaw = new SampleClaw(hwMap);
        limbArm = new LimbArm(hwMap, telemetry);
    }

    public void updateState(Gamepad gamepad) {
        sampleClaw.updateState(gamepad, limbArm.limbRotate.getCurrentPosition());
        if (gamepad.y) {
            state = SampleFinder.MANUAL;
            driveTrain.setModeMANUAL();
            sampleClaw.setModeMANUAL();
            limbArm.setModeMANUAL();
        }
        if (gamepad.a) {
            state = SampleFinder.SAMPLE_HUNT;
        }
    }

    public void moveClaw(Gamepad gamepad, double rotationPosition) {
        sampleClaw.move(gamepad, rotationPosition);
    }
    public void setMove(Gamepad gamepad1, Gamepad gamepad2) {
        if (state == SampleFinder.MANUAL) {
            driveTrain.move(gamepad1);
            sampleClaw.move(gamepad2, limbArm.rotatePosition());
            limbArm.move(gamepad2);
        }
        else if (state == SampleFinder.SAMPLE_HUNT) {
            sampleClaw.state = SampleClaw.ClawState.SAMPLE_HUNTING;
            driveTrain.state = DriveTrain.TurnState.LEFT;
            limbArm.RunMotor(-gamepad2.left_stick_y);
            limbArm.rotateByPower(-gamepad2.right_stick_y * 0.5f);
        }
    }
    public boolean doesManual() { // Sets up a return statement for telemetry reasons
        if (state == SampleFinder.MANUAL) {
            return true;
        }
        return false;
    }
}