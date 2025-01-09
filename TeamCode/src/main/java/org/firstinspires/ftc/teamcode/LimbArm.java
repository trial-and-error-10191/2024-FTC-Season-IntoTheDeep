package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    DcMotor limbExtend, limbRotate;
    double power = 0;

    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
    }
    public void armExtend(boolean extend, boolean contract) {
        if (extend) {
            power += INCREMENT;
        }
        else if (contract) {
            power -= INCREMENT;
        }
        limbExtend.setPower(power);
    }
    public void armExtendAuto(double limbExtendAuto) {
        if (power < limbExtendAuto) {
            power += INCREMENT;
        }
        else if (power > limbExtendAuto) {
            power -= INCREMENT;
        }
        limbExtend.setPower(power);
    }
    public void armRotate(boolean left, boolean right) {
        if (left) {
            power += INCREMENT;
        }
        else if (right) {
            power -= INCREMENT;
        }
        limbRotate.setPower(power);
    }
    public void armRotateAuto(double limbRotateAuto) {
        if (power < limbRotateAuto) {
            power += INCREMENT;
        }
        else if (power > limbRotateAuto) {
            power -= INCREMENT;
        }
        limbRotate.setPower(power);
    }
}
