package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmMechanism {
    private final DcMotor extendMotor;
    private final TouchSensor extensionLowerLimitSwitch;
    private final int extensionMaxEncoderCount = 10000;
    private final int EXTENSION_INCREMENT = 10;
    private final double EXTENSION_SPEED = 0.2;

    private final DcMotor rotateMotor;
    private final TouchSensor rotationLowerLimitSwitch;
    private final int rotationMaxEncoderCount = 10000;
    private final int ROTATION_INCREMENT = 10;
    private final double ROTATION_SPEED = 0.2;

    private final Telemetry telemetry;

    ArmMechanism(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        extendMotor = hwMap.get(DcMotor.class, "extend_motor");
        extendMotor.setDirection(DcMotor.Direction.FORWARD);
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rotateMotor = hwMap.get(DcMotor.class, "rotate_motor");
        rotateMotor.setDirection(DcMotor.Direction.FORWARD);
        rotateMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        extensionLowerLimitSwitch = hwMap.get(TouchSensor.class, "extension_limit_switch");
        rotationLowerLimitSwitch = hwMap.get(TouchSensor.class, "rotation_limit_switch");
    }

    public void update(Gamepad gamepad) {
        extendLift(gamepad);
        rotateArm(gamepad);
    }

    private void extendLift(Gamepad gamepad) {
        // dpad up/down to extend/retract lift
        // Determine where driver is telling motor to go
        boolean raisingLift = gamepad.dpad_up;
        boolean loweringLift = gamepad.dpad_down;

        if (raisingLift) { // If going up, move lift up while guard against overextending
            if (extendMotor.getCurrentPosition() >= extensionMaxEncoderCount) {
                extendMotor.setPower(0.0);
                extendMotor.setTargetPosition(extensionMaxEncoderCount);
                extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } else {
                int targetPosition = extendMotor.getCurrentPosition() + EXTENSION_INCREMENT;
                extendMotor.setTargetPosition(targetPosition);
                extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendMotor.setPower(EXTENSION_SPEED);
            }
        } else if (loweringLift) { // If going down, guard against retracting too far
            if (extensionLowerLimitSwitch.isPressed()) {
                extendMotor.setPower(0.0);
                extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } else {
                int targetPosition = extendMotor.getCurrentPosition() - EXTENSION_INCREMENT;
                extendMotor.setTargetPosition(targetPosition);
                extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendMotor.setPower(EXTENSION_SPEED);
            }
        }
    }

    private void rotateArm(Gamepad gamepad) {
        // dpad right/left to rotate arm down/up
        // Determine where driver is telling motor to go
        boolean raisingArm = gamepad.dpad_left;
        boolean loweringArm = gamepad.dpad_right;

        if (raisingArm) { // If going up, guard against rotating beyond "vertical" limit
            if (rotationLowerLimitSwitch.isPressed()) {
                rotateMotor.setPower(0.0);
                rotateMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } else {
                int targetPosition = rotateMotor.getCurrentPosition() - ROTATION_INCREMENT;
                rotateMotor.setTargetPosition(targetPosition);
                rotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rotateMotor.setPower(ROTATION_SPEED);
            }
        } else if (loweringArm) { // If going down, guard against lowering too far
            if (rotateMotor.getCurrentPosition() >= rotationMaxEncoderCount) {
                rotateMotor.setPower(0.0);
                rotateMotor.setTargetPosition(rotationMaxEncoderCount);
                rotateMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } else {
                int targetPosition = rotateMotor.getCurrentPosition() + ROTATION_INCREMENT;
                rotateMotor.setTargetPosition(targetPosition);
                rotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rotateMotor.setPower(ROTATION_SPEED);
            }
        }
    }
}
