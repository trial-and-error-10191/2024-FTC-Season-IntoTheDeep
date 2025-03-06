// This file is a system file.
package org.firstinspires.ftc.teamcode.Assemblies;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Teleoperation.Field_TeleOp;

public class Robot {
    public Field_TeleOp fieldTeleOp;
    public DriveTrain driveTrain;
    //public AscentMechanism ascentMechanism;
    public SampleClaw sampleClaw;
    public LimbArm limbArm;
    // This combines all the subsystems.
    public Robot(HardwareMap hwMap, Telemetry telemetry) {
        fieldTeleOp = new Field_TeleOp();
        driveTrain = new DriveTrain(hwMap, telemetry);
   //     ascentMechanism = new AscentMechanism(hwMap);
        sampleClaw = new SampleClaw(hwMap);
        limbArm = new LimbArm(hwMap, telemetry);
    }

    public void updateState(Gamepad gamepad) {
        sampleClaw.updateState(gamepad, limbArm.limbRotate.getCurrentPosition());
    }

    public void moveClaw(Gamepad gamepad, double rotationPosition) {
        sampleClaw.move(gamepad, rotationPosition);
    }
    public void sampleFind(Gamepad gamepad) {

    }
}