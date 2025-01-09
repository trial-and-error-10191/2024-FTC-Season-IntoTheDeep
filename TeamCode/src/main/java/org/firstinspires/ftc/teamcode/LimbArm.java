package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    DcMotor limbExtend, limbRotate;
    double power = 0;
    double MAX_POS = 1;
    double MIN_POS = 0;

    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
    }
    public void armExtend(boolean extend, boolean contract) {
        if (extend) {
            power += INCREMENT;
            if (power >= MAX_POS) {
                power = MAX_POS;
            }
        }
        else if (contract) {
            power -= INCREMENT;
            if (power <= MIN_POS) {
                power = MIN_POS;
            }
        }
        limbExtend.setPower(power);
    }
    public void armExtendAuto(double limbExtendAuto) {
        if (power < limbExtendAuto) {
            power += INCREMENT;
            if (power >= MAX_POS) {
                power = MAX_POS;
            }
        }
        else if (power > limbExtendAuto) {
            power -= INCREMENT;
            if (power <= MIN_POS) {
                power = MIN_POS;
            }
        }
        limbExtend.setPower(power);
    }
    public void armRotate(boolean left, boolean right) {
        if (left) {
            power += INCREMENT;
            if (power >= MAX_POS) {
                power = MAX_POS;
            }
        }
        else if (right) {
            power -= INCREMENT;
            if (power <= MIN_POS) {
                power = MIN_POS;
            }
        }
        limbRotate.setPower(power);
    }
    public void armRotateAuto(double limbRotateAuto) {
        if (power < limbRotateAuto) {
            power += INCREMENT;
            if (power >= MAX_POS) {
                power = MAX_POS;
            }
        }
        else if (power > limbRotateAuto) {
            power -= INCREMENT;
            if (power <= MIN_POS) {
                power = MIN_POS;
            }
        }
        limbRotate.setPower(power);
    }
}
