package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmMechanism {
    private DcMotor extendMotor = null;
    // to prevent the motor from contracting too much
    private DigitalChannel extensionLowerLimitSwitch = null;
    // prevent the motor from extending too far
    private final int extensionMaxEncoderCount = 10000;
    private DcMotor rotateMotor = null;
    private DigitalChannel rotationLowerLimitSwitch = null;
    private final int rotationMaxEncoderCount = 10000;
    private Telemetry telemetry = null;

    ArmMechanism(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        extendMotor = hwMap.get(DcMotor.class, "extend_motor");
        rotateMotor= hwMap.get(DcMotor.class, "rotate_motor");
    }

    public void update(Gamepad gamepad) {

    }
}
