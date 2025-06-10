package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoObsPark", group="Robot")
public class AutoObsPark extends AutoBase {
    @Override
    public void runOpMode() {
        autoSettings();
        StrafeRobot(SPEED, 38, 0);
        Wait(1);
    }
}