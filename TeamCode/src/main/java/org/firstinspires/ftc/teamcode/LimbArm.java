package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.stream.IntStream;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    DcMotor limbExtend, limbRotate;             // DC motors for lift arm
    double extendPower = 0;                     // Motor power for lift extension
    double rotatePower = 0;                     // Motor power for lift rotation
    int extensionLimit = 0;                     // Limit for extension
    int maxExtendPos = -3780;                   // Encoder counter max for lift extension
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
    }

    public int extendLimit() {
        int rotatePos = limbRotate.getCurrentPosition();
        if (rotatePos <= 0 && rotatePos > -630) {                  // This one reaches to the corner of our reach
            extensionLimit = -1126;
        }
        else if (rotatePos <= -630 && rotatePos > -1099) {         // This one reaches up almost to the top
            extensionLimit = 297;
        }
        else if (rotatePos <= -1099 && rotatePos > -1600) {        // This one rises up slightly
            extensionLimit = 828;
        }
        else if (rotatePos <= -1600 && rotatePos > -1940) {        // This one goes straight forward
            extensionLimit = 940;
        }
        else if (rotatePos <= -1940 && rotatePos > maxRotatePos) { // This one touches the ground
            extensionLimit = 690;
        }
        return extensionLimit;
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
    public void armExtend(float extend) {
        maxExtendPos = extendLimit();
        if (extend > 0) {                           // Makes the arm extend
            extendPower = extend;
//            if (limbExtend.getCurrentPosition() <= maxExtendPos) {
//                extendPower = 0;
//            }
//            else if (limbRotate.getCurrentPosition() <= -2380 && limbExtend.getCurrentPosition() <= -2785) {
//                extendPower = -0.2;
//            }
//            else {
//                extendPower = 0;
//            }
        }
        else if (extend <= 0) {                      // Makes the arm contract
            extendPower = extend;
            if (!limitExtend.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                limbExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extendPower = 0;
            }
        }
        limbExtend.setPower(extendPower);
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
                rotatePower = 0;
            }
        }
        limbRotate.setPower(rotatePower);
    }
}
