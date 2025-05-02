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
        SPECIMEN_PLACE,
        SPECIMEN_GRAB
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
            state = ROBOT_HUNTER.SPECIMEN_PLACE;
            sampleClaw.state = SampleClaw.ClawState.SPECIMEN_HUNTING;
            driveTrain.state = DriveTrain.TurnState.FORWARD;
            limbArm.state = LimbArm.LimbState.SPECIMEN_HANG;
        }
        if (gamepad.x) {
            state = ROBOT_HUNTER.SPECIMEN_GRAB;
            sampleClaw.state = SampleClaw.ClawState.SPECIMEN_HUNTING;
            driveTrain.state = DriveTrain.TurnState.BACKWARD;
            limbArm.state = LimbArm.LimbState.SPECIMEN_GRAB;
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
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                limbArm.limbRotate.setTargetPosition(-2080);
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                limbArm.limbRotate.setPower(limbArm.ROTATE_POWER * 0.5);
                Wait(3);
            }
            limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveTrain.turnToHeading(DriveTrain.TURN_SPEED, -90);
            driveTrain.move(gamepad1);
            limbArm.RunMotor(-gamepad2.left_stick_y);
            limbArm.rotateByPower(-gamepad2.right_stick_y);
            sampleClaw.move(gamepad2, limbArm.limbRotate.getCurrentPosition());
            sampleClaw.clawClamp(gamepad2.a);
        }
        else if (state == ROBOT_HUNTER.SPECIMEN_PLACE) {
            if (limbArm.limbRotate.getCurrentPosition() < -1100) {
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                limbArm.limbRotate.setTargetPosition(-988);
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                limbArm.limbRotate.setPower(limbArm.ROTATE_POWER * 0.5);
                Wait(3);
            }
            limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveTrain.turnToHeading(DriveTrain.TURN_SPEED, 0);
            driveTrain.move(gamepad1);
            limbArm.RunMotor(-gamepad2.left_stick_y);
            limbArm.rotateByPower(-gamepad2.right_stick_y);
            sampleClaw.move(gamepad2, limbArm.limbRotate.getCurrentPosition());
            sampleClaw.clawClamp(gamepad2.a);
        }
        else if (state == ROBOT_HUNTER.SPECIMEN_GRAB) {
            if (limbArm.limbRotate.getCurrentPosition() < -1880 || limbArm.limbRotate.getCurrentPosition() > -500) {
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                limbArm.limbRotate.setTargetPosition(-1730);
                limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                limbArm.limbRotate.setPower(limbArm.ROTATE_POWER * 0.5);
                Wait(3);
            }
            limbArm.limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveTrain.turnToHeading(DriveTrain.TURN_SPEED, 180);
            driveTrain.move(gamepad1);
            limbArm.RunMotor(-gamepad2.left_stick_y);
            limbArm.rotateByPower(-gamepad2.right_stick_y);
            sampleClaw.move(gamepad2, limbArm.limbRotate.getCurrentPosition());
            sampleClaw.clawClamp(gamepad2.a);
        }
    }
    public boolean doesManual() { // Sets up a return statement for telemetry reasons
        return state == ROBOT_HUNTER.MANUAL;
    }
    public void Wait(double seconds) {
        ElapsedTime Time   = new ElapsedTime();
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesn't need anything
        } // end of while loop
    }
}