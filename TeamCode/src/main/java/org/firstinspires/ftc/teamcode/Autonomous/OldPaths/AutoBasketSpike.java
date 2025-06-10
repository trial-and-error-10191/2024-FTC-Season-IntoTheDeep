package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoBasketSpike", group="Robot")
public class AutoBasketSpike extends AutoBase {
    @Override
    public void runOpMode() {
        driveStraight(SPEED, 2, 0);
        Wait(0.20);
        turnToHeading(SPEED, 90);
        StrafeRobot(SPEED, -6, 90);
        Wait(0.2);
        driveStraight(SPEED, 35, 90);
        Wait(0.2);
        claw.ExtendClaw(0.3);
        arm.ExtendAutoArm(arm.maxExtendPos);
        arm.armRotateAuto(-200);
        Wait(1);
        claw.OpenClaw();
        Wait(0.4);
        driveStraight(SPEED, -8, 90);
        Wait(0.2);
        arm.ExtendAutoArm(0);
        Wait(0.3);
        turnToHeading(SPEED, 0);
        Wait(0.2);
        driveStraight(SPEED, 45, 0);
        Wait(0.2);
        turnToHeading(SPEED, -90);
        Wait(1);
        driveStraight(SPEED, 20, -90);
        arm.ExtendAutoArm(arm.maxExtendPos - 1000);
        Wait(0.5);
        arm.armRotateAuto(-1500);
        Wait(0.5);
        //  arm.armRotateAuto(-2000);
    }
}
