package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "limbArmAuto", group="Robot")
public class limbAuto extends LinearOpMode {
    LimbArm arm;
    private ElapsedTime Time = new ElapsedTime();
    public void runOpMode() {
        arm = new LimbArm(hardwareMap, telemetry);
        // Wait for the game to start (Display Gyro value while waiting)
        while (opModeInInit()) {
            telemetry.addData(">", "Waiting to Start");
            telemetry.update();
        }
        testPath();
    }
    public void testPath() {
        arm.AutoExtendMotor(500);
        Wait(5);
        arm.armRotateAuto(-500);
        Wait(5);
    }
    public void Wait(double seconds) {
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesnt need anything
        }
    }
}
