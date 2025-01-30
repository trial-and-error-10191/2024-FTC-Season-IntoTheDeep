package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    DcMotor limbExtend, limbRotate;             // DC motors for lift arm
    CRServo spoolServo;                         // Servo that hold wires for lift
    private final double EXTEND_POWER = 0.5;                      // Motor power for lift extension
    double rotatePower = 0;                     // Motor power for lift rotation
    int extensionLimit = 3780;                     // Limit for extension
    final int maxExtendPos = 3780;             // Encoder counter max for lift extension
    int maxRotatePos = -2356;                   // Encoder counter for lift rotation
    int targetPosition = 0;
    DigitalChannel limitExtend;                 // Limit switch for bottom lift position
    DigitalChannel limitRotate;                 // Limit switch to prevent lift rotation
    private final int EXTENSION_RATE = 160;

    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbExtend.setDirection(DcMotor.Direction.REVERSE);
        limbExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        targetPosition = limbExtend.getCurrentPosition();
        limbExtend.setTargetPosition(targetPosition);
        limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        limbExtend.setPower(EXTEND_POWER);

        limbRotate = hwMap.get(DcMotor.class, "limbRotate");

        spoolServo = hwMap.get(CRServo.class, "spoolServo");

        limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        limitRotate = hwMap.get(DigitalChannel.class, "limitRotate");
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
        if (limbExtend.getCurrentPosition() > extensionLimit) {
            targetPosition = extensionLimit;
            limbExtend.setTargetPosition(targetPosition);
        }
        if (turn < 0) {                           // Makes the arm rotate down?
            rotatePower = turn;
            if (limbRotate.getCurrentPosition() <= maxRotatePos) {
                rotatePower = 0;
            }
        }
        else if (turn > 0) {                      // Makes the arm rotate up?
            rotatePower = turn;
            if (!limitRotate.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rotatePower = 0;
            }
        }
        else {
            rotatePower = 0;
        }
        limbRotate.setPower(rotatePower);
    }
// work on at home
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
            spoolServo.setPower(isUp ? 1 : -1);
        }
    }
}
