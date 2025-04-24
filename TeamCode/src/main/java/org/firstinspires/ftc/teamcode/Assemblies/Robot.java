// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {

    public enum ROBOT_HUNTER {
        MANUAL,
        SAMPLE_HUNT,
        SPECIMEN_HUNT
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
            driveTrain.state = DriveTrain.TurnState.RIGHT;
            limbArm.state = LimbArm.LimbState.SAMPLE_PICK_UP;
        }
        if (gamepad.b) {
            state = ROBOT_HUNTER.SPECIMEN_HUNT;
            sampleClaw.state = SampleClaw.ClawState.SPECIMEN_HUNTING;
            driveTrain.state = DriveTrain.TurnState.FORWARD;
            limbArm.state = LimbArm.LimbState.SPECIMEN_HANG;
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
            limbArm.maxRotatePos = -2356;
        }
        else if (state == ROBOT_HUNTER.SAMPLE_HUNT) {
            if (limbArm.limbRotate.getCurrentPosition() > -2000) {
                limbArm.limbRotate.setTargetPosition(-2288);
                Wait(2);
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                limbArm.limbRotate.setPower(limbArm.ROTATE_POWER);
            }
            driveTrain.turnToHeading(DriveTrain.TURN_SPEED, -90);
            driveTrain.move(gamepad1);
//            sampleClaw.move(gamepad2, limbArm.limbRotate.getCurrentPosition());
//            sampleClaw.clawClamp(gamepad2.a);
        }
        else if (state == ROBOT_HUNTER.SPECIMEN_HUNT) {
            if (limbArm.limbRotate.getCurrentPosition() < -1000) {
                limbArm.limbRotate.setTargetPosition(-988);
                Wait(2);
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                limbArm.limbRotate.setPower(limbArm.ROTATE_POWER);
            }
            driveTrain.turnToHeading(DriveTrain.TURN_SPEED, 0);
            driveTrain.move(gamepad1);
//            sampleClaw.move(gamepad2, limbArm.limbRotate.getCurrentPosition());
//            sampleClaw.clawClamp(gamepad2.a);
        }
    }
    public boolean doesManual() { // Sets up a return statement for telemetry reasons
        if (state == ROBOT_HUNTER.MANUAL) {
            return true;
        }
        return false;
    }

    public void Wait(double seconds) {
        ElapsedTime Time   = new ElapsedTime();
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesn't need anything
        } // end of while loop
    }
}