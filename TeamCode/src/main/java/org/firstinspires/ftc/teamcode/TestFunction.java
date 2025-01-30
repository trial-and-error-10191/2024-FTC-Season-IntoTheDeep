package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TestFunctions", group="Robot")
public class TestFunction extends LinearOpMode {
    private ElapsedTime Time = new ElapsedTime();
    public void Wait(double seconds) {
        Time.reset();
        while (Time.milliseconds()  < seconds * 1000) {
            // doesnt need anything
        }
    }

    @Override
    public void runOpMode() {
        SampleClaw Claw = new SampleClaw(hardwareMap);
        LimbArm Limb = new LimbArm(hardwareMap);
        waitForStart();
Limb.ExtendAutoArm(500);
Wait(5);
Limb.ExtendAutoArm(100);
Wait(5);
//Limb.auto_armRotate(0.5, 300);
//Wait(5);

    }
}
