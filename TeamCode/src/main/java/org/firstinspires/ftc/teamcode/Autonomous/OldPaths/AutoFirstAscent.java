package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoObsPark", group="Robot")
public class AutoFirstAscent extends AutoBase {
    @Override
    public void runOpMode() {
        autoSettings();
        driveStraight(SPEED, 2, 0);
        StrafeRobot(SPEED, -20, 0);
        driveStraight(SPEED, 54, 0);
        arm.armRotateAuto(-2000);
        Wait(0.5);
        turnToHeading(SPEED, -90);
        driveStraight(SPEED, 10, -90);
        arm.AutoExtendMotor(2200);
        arm.armRotateAuto(-1000);
        Wait(0.5);
    }
}
