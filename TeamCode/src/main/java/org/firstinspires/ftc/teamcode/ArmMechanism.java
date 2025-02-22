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

    private enum ExtensionState {
        MANUAL,
        EXTEND_ONLY,
        RETRACT_ONLY
    }
    private ExtensionState extensionState = ExtensionState.MANUAL;


    private final DcMotor rotateMotor;
    private final TouchSensor rotationLowerLimitSwitch;
    private final int rotationMaxEncoderCount = 10000;
    private final int ROTATION_INCREMENT = 10;
    private final double ROTATION_SPEED = 0.2;

    private final Telemetry telemetry;

    private final float DEADZONE = 0.05f;

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


    public void UpdateState() {
        if (extendMotor.getCurrentPosition() >= extensionMaxEncoderCount) {
            extensionState = ExtensionState.RETRACT_ONLY;
        } else if (extensionLowerLimitSwitch.isPressed()) {
            extensionState = ExtensionState.EXTEND_ONLY;
        } else {
            extensionState = ExtensionState.MANUAL;
        }
    }

    public void update(Gamepad gamepad) {
        extendLift(gamepad);
        rotateArm(gamepad);
    }

    private void extendLift(Gamepad gamepad) {
        // left stick y to drive lift extension
        float liftExtensionSpeed = -gamepad.left_stick_y;
        if (Math.abs(liftExtensionSpeed) <= DEADZONE) {
            liftExtensionSpeed = 0.0f;
        }
        boolean extending = liftExtensionSpeed > 0.0f;
        boolean retracting = liftExtensionSpeed < 0.0f;

        switch(extensionState) {
            case MANUAL:
                MoveLift(liftExtensionSpeed);
                break;
            case EXTEND_ONLY:
                if (extending) {
                    MoveLift(liftExtensionSpeed);
                } else {// lift has gone down as far as allowed, stop lift and reset encoder
                    extendMotor.setPower(0.0);
                    extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
                break;
            case RETRACT_ONLY:
                if (retracting) {
                    MoveLift(liftExtensionSpeed);
                } else {// lift has reached/surpassed limit, send it back to max allowed limit
                    extendMotor.setTargetPosition(extensionMaxEncoderCount);
                }
                break;
        }
    }

    private void MoveLift(float speed) {
        int targetPosition = extendMotor.getCurrentPosition() + (int)(speed * EXTENSION_INCREMENT);
        extendMotor.setTargetPosition(targetPosition);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(EXTENSION_SPEED);
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
