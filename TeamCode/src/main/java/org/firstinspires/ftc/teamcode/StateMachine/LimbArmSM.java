package org.firstinspires.ftc.teamcode.StateMachine;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimbArmSM {
    Telemetry telemetry;
    public DcMotor limbExtend;
    public DcMotor limbRotate;             // DC motors for lift arm
    CRServo spoolServo;                         // Servo that hold wires for lift
    public final double EXTEND_POWER = 0.5;                      // Motor power for lift extension
    public final double ROTATE_POWER = 0.75;                     // Motor power for lift rotation
    int extensionLimit = 3780;                     // Limit for extension
    public final int maxExtendPos = 3780;             // Encoder counter max for lift extension
    int maxRotatePos = -2356;                  // max encoder counter for lift rotation
    int rotatePos = 0;                         // Encoder counter for lift rotation
    int targetPosition = 0;
    DigitalChannel limitExtend;                 // Limit switch for bottom lift position
    DigitalChannel limitRotate;                 // Limit switch to prevent lift rotation
    private final int EXTENSION_RATE = 160;
    private final int ROTATION_RATE = 40;
    boolean testVar = false;
    public int LimbExtendCount() {
    return limbRotate.getCurrentPosition();
}
    public LimbArmSM(HardwareMap hwMap, Telemetry telemetry) {
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

    public void setManualMode() {
        testVar = true;
    }

    public void manualRun(Gamepad gamepad1, Gamepad gamepad2) {
        testVar = false;
    }
}
