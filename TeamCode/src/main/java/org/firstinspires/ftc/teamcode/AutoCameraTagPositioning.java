package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutomaticCameraTagPositioning", group = "Concept")
//@Disabled
public class AutoCameraTagPositioning {

    private HuskyLens huskyLens;

    @Override
    public void runOpMode() {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
    }

}
