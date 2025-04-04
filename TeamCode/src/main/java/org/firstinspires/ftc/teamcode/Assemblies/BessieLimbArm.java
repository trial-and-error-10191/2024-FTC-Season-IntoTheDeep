package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BessieLimbArm {
    Telemetry telemetry;
    public DcMotor limbExtend;
    public final double EXTEND_POWER = 0.5;                      // Motor power for lift extension
    int extensionLimit = 3780;                     // Limit for extension
    public final int maxExtendPos = 3780;             // Encoder counter max for lift extension
    int targetPosition = 0;
    //DigitalChannel limitExtend;                 // Limit switch for bottom lift position
    private final int EXTENSION_RATE = 160;
    public BessieLimbArm(HardwareMap hwMap, Telemetry telemetry) {
        limbExtend = hwMap.get(DcMotor.class, "limbExtend");
        limbExtend.setDirection(DcMotor.Direction.REVERSE);
        limbExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        targetPosition = limbExtend.getCurrentPosition();
        limbExtend.setTargetPosition(targetPosition);
        limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        limbExtend.setPower(EXTEND_POWER);

        //limitExtend = hwMap.get(DigitalChannel.class, "limitExtend");
        this.telemetry = telemetry;
    }

    public void RunMotor(float extend) {
        float servoExtend = extend;
        if (extend != 0) {
            targetPosition = limbExtend.getCurrentPosition() + (int) (extend * EXTENSION_RATE);
        }
        if (targetPosition > extensionLimit) { // If going up, guard against overextending
            targetPosition = extensionLimit;
            servoExtend = 0;
        } //else if (extend < 0 && !limitExtend.getState()) { // If going down, guard against retracting too far
//            limbExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            targetPosition = limbExtend.getCurrentPosition();
//            servoExtend = 0;
//            limbExtend.setTargetPosition(targetPosition);
//            limbExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
        limbExtend.setTargetPosition(targetPosition);
        //telemetry.addData("ExtendLimit", "%b", !limitExtend.getState());
    }
    public void AutoExtendMotor(int extendAuto) { // Allows the arm to extend in autonomous
        if (extendAuto > maxExtendPos) {
            extendAuto = maxExtendPos;
        }
        limbExtend.setTargetPosition(extendAuto);
    }
}
