package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoTest", group="Robot")
public class AutoTest extends AutoBase {
    @Override
    public void runOpMode() {
        driveStraight(SPEED, 2, 0);
        turnToHeading(SPEED, 90);
        driveStraight(SPEED, 31, 92);
        turnToHeading(SPEED, 110);
        arm.ExtendAutoArm(arm.maxExtendPos - 250);
        arm.armRotateAuto(-189);
        claw.ExtendClaw(0.3);
        Wait(1.5);
        claw.OpenClaw();
        // Start second sample
        driveStraight(SPEED, -5, -90);
        turnToHeading(SPEED, 0);
        arm.ExtendAutoArm(2282);
        driveStraight(SPEED, 11.5, 2);
        arm.armRotateAuto(-2235);
        Wait(2);
        claw.ExtendClaw(0.3);
        Wait(2.5);
        claw.CloseClaw();
        Wait(1.2);
        claw.CloseClaw();
        // Place 2nd sample
        arm.armRotateAuto(-180);
        Wait(1);
        driveStraight(SPEED, -11.5, 0);
        StrafeRobot(SPEED, 2, 0);
        turnToHeading(SPEED, 115);
        arm.ExtendAutoArm(2382);
        arm.armRotateAuto(-589); // -289
        claw.ExtendClaw(0.3);
        Wait(1.5);
        claw.OpenClaw();
        Wait(0.5);
        arm.armRotateAuto(-189);
        arm.ExtendAutoArm(0);
    }
}
