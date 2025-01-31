package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimbArm {
    Telemetry telemetry;
    DcMotor limbExtend, limbRotate;             // DC motors for lift arm
    CRServo spoolServo;                         // Servo that hold wires for lift
    private final double EXTEND_POWER = 0.5;                      // Motor power for lift extension
    private final double ROTATE_POWER = 0.5;                     // Motor power for lift rotation
    int extensionLimit = 3780;                     // Limit for extension
    final int maxExtendPos = 3780;             // Encoder counter max for lift extension
    int maxRotatePos = -2356;                  // max encoder counter for lift rotation
    int rotatePos = 0;                         // Encoder counter for lift rotation
    int targetPosition = 0;
    DigitalChannel limitExtend;                 // Limit switch for bottom lift position
    DigitalChannel limitRotate;                 // Limit switch to prevent lift rotation
    private final int EXTENSION_RATE = 160;
    private final int ROTATION_RATE = 40;

    public LimbArm(HardwareMap hwMap, Telemetry telemetry) {
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
        limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        limitRotate = hwMap.get(DigitalChannel.class, "limitRotate");
        this.telemetry = telemetry;
    }

    public void RunMotor(float extend) {
        float servoExtend = extend;
        //extendLimit();
        if (extend != 0) {
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
        limbExtend.setTargetPosition(targetPosition);
        spoolServo.setPower(servoExtend * 0.85);
    }

//    public void extendLimit() {
//        int rotatePos = limbRotate.getCurrentPosition();
//        if (rotatePos <= 0 && rotatePos > -1099) {                  // This one reaches to the corner of our reach
//            extensionLimit = maxExtendPos;
//        }
//        else if (rotatePos <= -1099 && rotatePos > -1600) {        // This one rises up slightly
//            extensionLimit = 2472;
//        }
//        else if (rotatePos <= -1600 && rotatePos > -1940) {        // This one goes straight forward
//            extensionLimit = 2100;
//        }
//        else if (rotatePos <= -1940 && rotatePos > maxRotatePos) { // This one touches the ground
//            extensionLimit = 2229;
//        }
//    }

    public void armRotate(float turn) {
//        if (limbExtend.getCurrentPosition() > extensionLimit) {
//            targetPosition = extensionLimit;
//            limbExtend.setTargetPosition(targetPosition);
//        }
        //if (turn != 0) {
        if (Math.abs(turn) >= 0.05f) {
            rotatePos = limbRotate.getCurrentPosition() + (int) (turn * ROTATION_RATE);
        }
        if (rotatePos < maxRotatePos) {
            rotatePos = maxRotatePos;
        } else if (turn > 0 && !limitRotate.getState()) {
            limbRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rotatePos = limbRotate.getCurrentPosition();
            limbRotate.setTargetPosition(rotatePos);
            limbRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        limbRotate.setTargetPosition(rotatePos);
    }

    public void ExtendAutoArm(int Counts) {
        if (Counts < 0) {
            limbExtend.setTargetPosition(0);
        } else if (Counts > maxExtendPos) {
            limbExtend.setTargetPosition(maxExtendPos);
        } else {
            limbExtend.setTargetPosition(Counts);
        }
        limbExtend.setPower(EXTEND_POWER);
        limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        boolean isUp = ((Counts - limbExtend.getCurrentPosition()) > 0);
        while (limbExtend.isBusy()) {
            telemetry.addData("isBusy", spoolServo.getPower() );
            telemetry.addData("LifeExtension", limbExtend.getCurrentPosition() );
            telemetry.update();
            spoolServo.setPower(isUp ? 0.9 : -0.9);
        }
        spoolServo.setPower(0);
    }

    public void auto_armRotate(double Speed, int Counts) {
        limbRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        limbRotate.setTargetPosition(Counts);
        limbRotate.setPower(Speed);
    }
}
