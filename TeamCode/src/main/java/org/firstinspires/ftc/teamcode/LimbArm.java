package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    DcMotor limbExtend, limbRotate;             // DC motors for lift arm
    double extendPower = 0;                     // motor power for lift extension
    double rotatePower = 0;                     // motor power for lift rotation
    double maxExtendPos = 10;                   // encoder counter max for lift extension
    double maxRotatePos = 10;                   // encoder counter for lift rotation
    double MAX_POS = 1;
    double MIN_POS = -1;
    DigitalChannel limitExtend;                 // limit switch for bottom lift position
    DigitalChannel limitRotate;                 // limit switch to prevent lift rotation


    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
        limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        limitRotate = hwMap.get(DigitalChannel.class, "limitRotate");
    }
    public void armExtend(float extend) {
        if (extend > extendPower) {                           // Makes the arm extend
            extendPower = extend;
            if (limbExtend.getCurrentPosition() >= maxExtendPos) {
                extendPower = 0;
            }
        }
        else if (extend < extendPower) {                      // Makes the arm contract
            extendPower = extend;
            if (!limitExtend.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                extendPower = 0;
            }
        }
        limbExtend.setPower(extendPower);
    }

    public void armExtendAuto(double limbExtendAuto) {
        if (limbExtendAuto > extendPower) {                  // Makes the arm extend
            extendPower = limbExtendAuto;
            if (limbExtend.getCurrentPosition() >= maxExtendPos) {
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
        // note to self: test which way the arm rotates to program the limit switch correctly
        if (turn > rotatePower) {                           // Makes the arm rotate down?
            rotatePower = turn;
            if (limbRotate.getCurrentPosition() >= maxRotatePos) {
                rotatePower = 0;
            }
        }
        else if (turn < rotatePower) {                      // Makes the arm rotate up?
            rotatePower = turn;
            if (!limitRotate.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotatePower = 0;
            }
        }
        limbRotate.setPower(rotatePower);
    }
    public void armRotateAuto(double limbRotateAuto) {
        // note to self: test which way the arm rotates to program the limit switch correctly
        if (limbRotateAuto > rotatePower) {                 // Makes the arm rotate down?
            rotatePower = limbRotateAuto;
            if (limbRotate.getCurrentPosition() >= maxRotatePos) {
                rotatePower = 0;
            }
        }
        else if (limbRotateAuto < rotatePower) {           // Makes the arm rotate up?
            rotatePower = limbRotateAuto;
            if (!limitRotate.getState()) {      // Stop motor and reset encoder if limit switch is triggered
                limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotatePower = 0;
            }
        }
        limbRotate.setPower(rotatePower);
    }
}
