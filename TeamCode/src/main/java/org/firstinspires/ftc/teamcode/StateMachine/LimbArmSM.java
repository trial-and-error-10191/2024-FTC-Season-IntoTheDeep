package org.firstinspires.ftc.teamcode.StateMachine;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimbArmSM {
    Telemetry telemetry;
    public DcMotor limbExtend;
    public DcMotor limbRotate;                  // DC motors for lift arm
    CRServo spoolServo;                         // Servo that hold wires for lift
    public final double EXTEND_POWER = 0.5;     // Motor power for lift extension
    public final double ROTATE_POWER = 0.75;    // Motor power for lift rotation
    int extensionLimit = 3780;                  // Limit for extension
    public final int maxExtendPos = 3780;       // Encoder counter max for lift extension
    int maxRotatePos = -2356;                   // max encoder counter for lift rotation
    int minRotatePos = 0;
    int rotatePos;                              // Encoder counter for lift rotation
    int targetPosition;
    float rotationSensitivity = 1;
    float extensionSensitivity = 1;
    DigitalChannel limitExtend;                 // Limit switch for bottom lift position
    DigitalChannel limitRotate;                 // Limit switch to prevent lift rotation
    boolean testVar = false;

    public LimbArmSM(HardwareMap hwMap, Telemetry telemetry) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbExtend.setDirection(DcMotor.Direction.REVERSE);
        limbExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        targetPosition = limbExtend.getCurrentPosition();
        limbExtend.setTargetPosition(targetPosition);
        limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        limbExtend.setPower(EXTEND_POWER);

        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
        limbRotate.setDirection(DcMotor.Direction.FORWARD);
        rotatePos = limbRotate.getCurrentPosition();
        limbRotate.setTargetPosition(rotatePos);
        limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        limbRotate.setPower(ROTATE_POWER);

        spoolServo = hwMap.get(CRServo.class, "spoolServo");

        // Set up limit switches
        limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        limitRotate = hwMap.get(DigitalChannel.class, "limitRotate");

        this.telemetry = telemetry;
    }

    public void RunMotor(float extend) {
        float servoExtend = extend;
        extendLimit();

        if (extend != 0) {
            final int EXTENSION_RATE = 160;
            targetPosition = limbExtend.getCurrentPosition() + (int) (extend * EXTENSION_RATE);
        }
        if (targetPosition > extensionLimit) { // If going up, guard against overextending
            targetPosition = extensionLimit;
            servoExtend = 0;
        } else if (extend < 0 && !limitExtend.getState()) { // If going down, guard against retracting too far
            limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            targetPosition = limbExtend.getCurrentPosition();
            servoExtend = 0;
            limbExtend.setTargetPosition(targetPosition);
            limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        limbExtend.setPower(servoExtend * extensionSensitivity);
        limbExtend.setTargetPosition(targetPosition);
        spoolServo.setPower(servoExtend * 0.85);
        telemetry.addData("ExtendLimit", "%b", !limitExtend.getState());
    }

    public void spoolCorrection(boolean expel, boolean reverse) { // Thing that allows the spool to be corrected manually
        if (expel) {
            spoolServo.setPower(0.5);
        }
        else if (reverse) {
            spoolServo.setPower(-0.5);
        }
        telemetry.addData("SpoolPower", "%4.2f", spoolServo.getPower());
    }

    public void rotateByPower(float turn) {
        float rotatePower = 0.0f;
        if (Math.abs(turn) > 0.05f) {
            rotatePower = turn;
        }
        rotatePos = limbRotate.getCurrentPosition();

        // Guard against rotating too far forward
        if (limbRotate.getCurrentPosition() <= maxRotatePos && turn < 0) {
            rotatePower = 0;
        }
        // Guard against rotating too far backward
        else if (turn >= 0 && !limitRotate.getState()) {
            rotatePower = 0;
            limbRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        // Prevents lift from rotating past limit switch by slowing down the lift whenever it gets close to the 0 position
        else if (rotatePos >= -200 && rotatePower > 0) {
            rotatePower *= 0.5f;
        }
        limbRotate.setPower(rotatePower * rotationSensitivity);
        telemetry.addData("Rotate Encoders", "%d", limbRotate.getCurrentPosition());
        telemetry.addData("Rotate Limit", "%b", !limitRotate.getState());
    }

    public void initRotateByPower() {
        limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void extendLimit() {
        int rotatePos = limbRotate.getCurrentPosition();
        if (rotatePos <= 0 && rotatePos > -849) {                  // This one reaches to the corner of our reach
            extensionLimit = maxExtendPos;
        }
        else if (rotatePos <= -849 && rotatePos > maxRotatePos) {        // This one rises up slightly
            extensionLimit = 2282;
        }
    }

    public void setManualMode() {
        rotationSensitivity = 1;
        extensionSensitivity = 1;
        initRotateByPower();
        extensionLimit = 3780;
        maxRotatePos = -2356;
        minRotatePos = 0;
    }

    public void setSamplePlaceMode() {
        rotationSensitivity = 0.5f;
        initRotateByPower();
        if (limbExtend.getCurrentPosition() < maxExtendPos) { // Making the robot stay at the max extension it can go to
            RunMotor(maxExtendPos);
        }
        maxRotatePos = -400;
        minRotatePos = 0;
    }

    public void setSampleGrabMode() {
        rotationSensitivity = 0.5f;
        limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        initRotateByPower();
        maxRotatePos = -2200;
        minRotatePos = -2000;
        if (limbRotate.getCurrentPosition() < maxRotatePos || limbRotate.getCurrentPosition() > minRotatePos) {
            limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            limbRotate.setTargetPosition(-2100);
            limbRotate.setPower(0.2);
            limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Wait(3);
            limbRotate.setPower(0);
        }
    }

    public void setSpecimenPlaceMode() {
        rotationSensitivity = 0.5f;
        limbExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        initRotateByPower();
        extensionSensitivity = 0.5f;
        extensionLimit = 2500;
        if (limbExtend.getCurrentPosition() > maxExtendPos) { // Making the robot stay at the max extension it can go to
            limbExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RunMotor(1500);
            limbExtend.setPower(0.2);
            limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Wait(3);
            limbExtend.setPower(0);
        }
        maxRotatePos = -500;
        minRotatePos = 0;
        if (limbRotate.getCurrentPosition() < maxRotatePos) {
            limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            limbRotate.setTargetPosition(-250);
            limbRotate.setPower(0.2);
            limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Wait(3);
            limbRotate.setPower(0);
        }
    }

    public void setSpecimenGrabMode() {
        rotationSensitivity = 0.5f;
        limbRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        initRotateByPower();
        maxRotatePos = -2000;
        minRotatePos = -1800;
        if (limbRotate.getCurrentPosition() < maxRotatePos || limbRotate.getCurrentPosition() > minRotatePos) {
            limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            limbRotate.setTargetPosition(-1900);
            limbRotate.setPower(0.2);
            limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Wait(3);
            limbRotate.setPower(0);
        }
    }

    public void manualRun(Gamepad gamepad1, Gamepad gamepad2) {
        testVar = false;
    }

    public void Wait(double seconds) {
        ElapsedTime Time   = new ElapsedTime();
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesn't need anything
        } // end of while loop
    } // end of public void Wait
}