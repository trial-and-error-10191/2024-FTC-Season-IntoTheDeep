package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SampleAndSpecimenManipulator {
    private final ArmMechanism arm;
    private final ClawMechanism claw;
    private final Telemetry telemetry;

    SampleAndSpecimenManipulator(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        arm = new ArmMechanism(hwMap, telemetry);
        claw = new ClawMechanism(hwMap, telemetry);
    }

    public void update(Gamepad gamepad) {
        arm.update(gamepad);
        claw.update(gamepad);
    }

    public void UpdateState() {
        arm.UpdateState();
    }
}
