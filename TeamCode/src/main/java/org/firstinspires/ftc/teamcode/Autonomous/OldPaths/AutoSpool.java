package org.firstinspires.ftc.teamcode.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoSpool", group="Robot")
public class AutoSpool extends AutoBase {
    @Override
    public void runOpMode() {
        autoSettings();
        arm.ExtendAutoArm(1000);
        Wait(3);
    }
}
