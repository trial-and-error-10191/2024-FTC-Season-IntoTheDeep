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
    //public RobotAutoDriveByEncoder_Linear EncoderDrive;
    public
    @Override

    void runOpMode(){
        runtimeTimer = new ElapsedTime();
        runtimeTimer.startTime();

        robot = new Robot(hardwareMap, telemetry);

        waitForStart(); {
            if (opModeIsActive()) {
                // Putting the leftinches and rightinches as the same number will make the robot spin
                robot.driveTrain.encoderDrive(0.6,5,-5, -5, 5, 0.9);
                robot.driveTrain.encoderDrive(0.5,115, 115, 115, 115, 2.8);
                robot.driveTrain.encoderDrive(0.3,-171,-171, -171,-171, 5.5);
                robot.driveTrain.encoderDrive(0.6,-20,20, 20, -20, 1);
            }
        }
    }   // end runOpMode()
}