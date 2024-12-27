package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous(name = "AutomaticAICameraTagPositioning", group = "Concept")
//@Disabled
public class AutoAICameraTagPositioning extends LinearOpMode {

    private HuskyLens huskyLens;

    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;
    int horizontalLine = 300;
    int verticalLine = 300;

    final double SPEED_GAIN  =  0.1; //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double STRAFE_GAIN =  0.1; //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
    final double TURN_GAIN   =  0.1; //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
    final double MAX_AUTO_SPEED = 0.5; //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_STRAFE= 0.5; //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN  = 0.3; //  Clip the turn speed to this max value (adjust for your robot)

    ElapsedTime runtimeTimer = null;

    @Override
    public void runOpMode() {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");

        runtimeTimer = new ElapsedTime();
        runtimeTimer.startTime();

    } // end runOpMode()



} // end of class