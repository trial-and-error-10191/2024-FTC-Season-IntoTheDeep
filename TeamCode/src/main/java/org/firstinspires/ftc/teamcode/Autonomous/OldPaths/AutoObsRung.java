package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoObsRung", group="Robot")
public class AutoObsRung extends AutoBase {
    @Override
    public void runOpMode() {
        autoSettings();
        claw.CloseClaw();
        claw.ExtendClaw(0.54);
        arm.ExtendAutoArm(1442);
        driveStraight(SPEED, 3, 0);
        StrafeRobot(SPEED, -12, 0);
        arm.armRotateAuto(-1123);
        claw.ExtendClaw(0.65);
        Wait(1.5);
        driveStraight(SPEED, 25, 0);
        Wait(1.5);
        claw.OpenClaw();
        Wait(0.4);
        claw.RotateClaw(0.5);
        arm.ExtendAutoArm(0);
        Wait(0.2);
        arm.armRotateAuto(-10);
        Wait(1);
        driveStraight(SPEED, -25, 0);
        Wait(0.5);
        StrafeRobot(SPEED, 44, 0);
    }
}