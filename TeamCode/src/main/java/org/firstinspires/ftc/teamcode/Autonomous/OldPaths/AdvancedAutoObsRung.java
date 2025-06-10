package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AdvancedAutoObsRung", group="Robot")
public class AdvancedAutoObsRung extends AutoBase {
    @Override
    public void runOpMode() {
        autoSettings();
        claw.CloseClaw();
        claw.ExtendClaw(0.54);
        arm.ExtendAutoArm(1542);
        StrafeRobot(SPEED, -5, 0);
        arm.armRotateAuto(-1203);
        claw.ExtendClaw(0.65);
        Wait(2.5);
        driveStraight(SPEED, 28, 0);
        Wait(1.5);
        claw.OpenClaw();
        Wait(0.1);
        claw.RotateClaw(0.5);
        arm.ExtendAutoArm(0);
        Wait(0.2);
        arm.armRotateAuto(-10);
        driveStraight(SPEED, -4, 0);
        StrafeRobot(SPEED, 35, 0);
        arm.armRotateAuto(-1858);
        claw.ExtendClaw(0.55); // higher# is higher claw pos
        driveStraight(SPEED, 22, 0);
        turnToHeading(SPEED, 180);
        StrafeRobot(SPEED, -13, 180);
        driveStraight(SPEED, 47, 180);
        arm.ExtendAutoArm(300);
        Wait(1);
        claw.CloseClaw();
        Wait(1);
        arm.armRotateAuto(-100);
        Wait(1);
    }
}

