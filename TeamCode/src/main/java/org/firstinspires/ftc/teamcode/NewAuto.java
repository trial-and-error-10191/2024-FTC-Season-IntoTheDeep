// This file is a system file.
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "DeepAuto (broken) ", group = "Concept")
public class NewAuto extends LinearOpMode {

    Robot robot = null;
    ElapsedTime runtimeTimer = null;

    // Distance Sensor code
    public DistanceSensor sensorDistance;

    //public RobotAutoDriveByEncoder_Linear EncoderDrive;
    public
    @Override
    void runOpMode(){
        runtimeTimer = new ElapsedTime();
        runtimeTimer.startTime();

        robot = new Robot(hardwareMap, telemetry);

        waitForStart(); {
            if (opModeIsActive()) {

            }
        }  // end waitForStart
    }   // end runOpMode()
}