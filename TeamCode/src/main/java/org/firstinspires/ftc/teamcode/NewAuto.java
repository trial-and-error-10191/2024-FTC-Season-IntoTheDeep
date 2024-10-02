// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
// import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "DeepAuto", group = "Concept")
// @com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "IntoTheDeep Autonomous", group = "Autonomous")
public class NewAuto extends LinearOpMode {
    Robot robot = null;
    ElapsedTime runtimeTimer = null;
    public DistanceSensor sensorDistance;
    public RobotAutoDriveByEncoder_Linear EncoderDrive;
    public
    @Override
    void runOpMode(){
        runtimeTimer = new ElapsedTime();
        runtimeTimer.startTime();

        robot = new Robot(hardwareMap);

        waitForStart(); {
            if (opModeIsActive()) {
                robot.driveTrain.autonomous1();
            }
        }
    }   // end runOpMode()
}