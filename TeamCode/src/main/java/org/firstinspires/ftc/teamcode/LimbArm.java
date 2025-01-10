package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    DcMotor limbExtend, limbRotate;
    double extendPower = 0;
    double rotatePower = 0;
    double maxExtendPos = 10;
    double MAX_POS = 1;
    double MIN_POS = -1;
    DigitalChannel limitExtend;
    DigitalChannel limitRotate;


    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
        limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        limitRotate = hwMap.get(DigitalChannel.class, "limitRotate");
    }
    public void armExtend(float extend) {
        if (extend > extendPower) { // Makes the arm extend
            extendPower += INCREMENT;
            if (limbExtend.getCurrentPosition() >= maxExtendPos) {
                extendPower = 0;
            }
        }
        else if (extend < extendPower) { // Makes the arm contract
            extendPower -= INCREMENT;
            if (!limitExtend.getState()) {
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                extendPower = 0;
            }
        }
        limbExtend.setPower(extendPower);
    }
    public void armExtendAuto(double limbExtendAuto) {
        if (extendPower < limbExtendAuto) { // Makes the arm extend for autonomous
            extendPower += INCREMENT;
            if (limitExtend.getState()) {
                limbExtend.getCurrentPosition();
                limbExtend.setPower(MAX_POS);
            }
        }
        else if (extendPower > limbExtendAuto) { // Makes the arm contract for autonomous
            extendPower -= INCREMENT;
            if (!limitExtend.getState()) {
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                extendPower = 0;
            }
        }
        limbExtend.setPower(extendPower);
    }
    public void armRotate(float turn) {
        if (turn > rotatePower) { // Makes the arm rotate to the left
            rotatePower += INCREMENT;
            if (limitRotate.getState()) {
                limbExtend.getCurrentPosition();
                limbExtend.setPower(MAX_POS);
            }
        }
        else if (turn < rotatePower) { // Makes the arm rotate to the right
            rotatePower -= INCREMENT;
            if (!limitRotate.getState()) {
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotatePower = 0;
            }
        }
        limbRotate.setPower(rotatePower);
    }
    public void armRotateAuto(double limbRotateAuto) {
        if (rotatePower < limbRotateAuto) { // Makes the arm rotate to the left in autonomous
            rotatePower += INCREMENT;
            if (limitRotate.getState()) {
                limbExtend.getCurrentPosition();
                limbExtend.setPower(MAX_POS);
            }
        }
        else if (rotatePower > limbRotateAuto) { // Makes the arm rotate to the right in autonomous
            rotatePower -= INCREMENT;
            if (!limitRotate.getState()) {
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotatePower = 0;
            }
        }
        limbRotate.setPower(rotatePower);
    }
}
