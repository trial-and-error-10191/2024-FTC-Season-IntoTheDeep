package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="AutoTest", group="Robot")
public class AutoTest extends AutoBase {
    public void runOpMode() {
        driveStraight(TURN_SPEED, 2, 0);
        turnToHeading(TURN_SPEED, 90);
        driveStraight(TURN_SPEED, 31, 92);
        turnToHeading(TURN_SPEED, 110);
//        arm.ExtendAutoArm(arm.maxExtendPos - 250);
//        arm.armRotateAuto(-189);
//        claw.ExtendClaw(0.3);
        Wait(1.5);
        //claw.OpenClaw();
        // Start second sample
        driveStraight(TURN_SPEED, -5, -90);
        turnToHeading(TURN_SPEED, 0);
        //arm.ExtendAutoArm(2282);
        driveStraight(TURN_SPEED, 11.5, 2);
        //arm.armRotateAuto(-2235);
        Wait(2);
       // claw.ExtendClaw(0.3);
        Wait(2.5);
       // claw.CloseClaw();
        Wait(1.2);
//        claw.CloseClaw();
//        // Place 2nd sample
//        arm.armRotateAuto(-180);
        Wait(1);
        driveStraight(TURN_SPEED, -11.5, 0);
        StrafeRobot(TURN_SPEED, 2, 0);
        turnToHeading(TURN_SPEED, 115);
//        arm.ExtendAutoArm(2382);
//        arm.armRotateAuto(-589); // -289
//        claw.ExtendClaw(0.3);
        Wait(1.5);
//        claw.OpenClaw();
        Wait(0.5);
//        arm.armRotateAuto(-189);
//        arm.ExtendAutoArm(0);
    }
}
