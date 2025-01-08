package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimbArm {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    DcMotor limbExtend, limbRotate;
    double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position

    public LimbArm(HardwareMap hwMap) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbRotate = hwMap.get(DcMotor.class, "limbRotate");
    }
    public void armExtend(boolean extend, boolean contract) {
        if (extend) {
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (contract) {
            position -= INCREMENT;
            if (position <= MIN_POS) {
                position = MIN_POS;
            }
        }
        limbExtend.setPower(position);
    }
    public void armExtendAuto(double limbExtendAuto) {
        if (position < limbExtendAuto) {
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (position > limbExtendAuto) {
            position -= INCREMENT;
            if (position <= MIN_POS) {
                position = MIN_POS;
            }
        }
        limbExtend.setPower(position);
    }
    public void armRotate(boolean left, boolean right) {
        if (left) {
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (right) {
            position -= INCREMENT;
            if (position <= MIN_POS) {
                position = MIN_POS;
            }
        }
        limbRotate.setPower(position);
    }
    public void armRotateAuto(double limbRotateAuto) {
        if (position < limbRotateAuto) {
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        }
        else if (position > limbRotateAuto) {
            position -= INCREMENT;
            if (position <= MIN_POS) {
                position = MIN_POS;
            }
        }
        limbRotate.setPower(position);
    }
}
