package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LimbArm {

    DcMotor limbExtend, limbRotate;             // DC motors for lift arm
    CRServo spoolServo;                         // Servo that hold wires for lift
    double extendPower = 0;                     // Motor power for lift extension
    double rotatePower = 0;                     // Motor power for lift rotation
    int extensionLimit = 0;                     // Limit for extension
    final int maxExtendPos = -3780;             // Encoder counter max for lift extension
    int maxRotatePos = -2356;                   // Encoder counter for lift rotation
    DigitalChannel limitExtend;                 // Limit switch for bottom lift position
    DigitalChannel limitRotate;                 // Limit switch to prevent lift rotation

    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbExtend.setDirection(DcMotor.Direction.REVERSE);
        limbExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
        limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        limitRotate = hwMap.get(DigitalChannel.class, "limitRotate");
        spoolServo = hwMap.get(CRServo.class, "spoolServo");
    }

    public void extendLimit() {
        int rotatePos = limbRotate.getCurrentPosition();
        if (rotatePos <= 0 && rotatePos > -1099) {                  // This one reaches to the corner of our reach
            extensionLimit = maxExtendPos;
        }
        else if (rotatePos <= -1099 && rotatePos > -1600) {        // This one rises up slightly
            extensionLimit = -2472;
        }
        else if (rotatePos <= -1600 && rotatePos > -1940) {        // This one goes straight forward
            extensionLimit = -2100;
        }
        else if (rotatePos <= -1940 && rotatePos > maxRotatePos) { // This one touches the ground
            extensionLimit = -2229;
        }
    }
    public void highNetSetUp(boolean net) { //Sets up the robot to put a sample in the high net
        if (net) {
            limbRotate.setTargetPosition(0);
            limbExtend.setTargetPosition(maxExtendPos);
        }
    }
    public void fromThePit(boolean pit) { //Sets up the robot to put a sample in the high net
        if (pit) {
            limbRotate.setTargetPosition(maxRotatePos);
        }
    }
    public void spoolServoFunction(double spool) {
        spoolServo.setPower(spool); // letting wires out = positive, putting wires back on the wheel = negative
    }
    public void armExtend(float extend) {
        extendLimit();
        if (extend > 0) { // Makes the arm extend
            extendPower = extend;
            if (limbExtend.getCurrentPosition() <= extensionLimit) {
                extendPower = 0;
            }
        }
        else if (extend <= 0) {                      // Makes the arm contract
            extendPower = extend;
            if (!limitExtend.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                limbExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extendPower = 0;
            }
        }
        limbExtend.setPower(extendPower / 2);
        spoolServo.setPower(extendPower);
    }

    public void armExtendAuto(double limbExtendAuto) {
        if (limbExtendAuto > extendPower) {                  // Makes the arm extend
            extendPower = limbExtendAuto;
            if (limbExtend.getCurrentPosition() <= maxExtendPos) {
                extendPower = 0;
            }
        }
        else if (extendPower > limbExtendAuto) {             // Makes the arm contract for autonomous
            extendPower = limbExtendAuto;
            if (!limitExtend.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                extendPower = 0;
            }
        }
        limbExtend.setPower(extendPower);
    }

    public void armRotate(float turn) {
        if (extensionLimit > limbExtend.getCurrentPosition()) {
//            limbExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            limbExtend.setTargetPosition(extensionLimit);
//            limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            limbExtend.setPower(-0.2);
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
    public void armRotateAuto(double limbRotateAuto) {
        // note to self: test which way the arm rotates to program the limit switch correctly
        if (limbRotateAuto > rotatePower) {                 // Makes the arm rotate down?
            rotatePower = limbRotateAuto;
            if (limbRotate.getCurrentPosition() <= maxRotatePos) {
                rotatePower = 0;
            }
        }
        else if (limbRotateAuto < rotatePower) {           // Makes the arm rotate up?
            rotatePower = limbRotateAuto;
            if (!limitRotate.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                limbRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rotatePower = 0;
            }
        }
        limbRotate.setPower(rotatePower);
    }
}
