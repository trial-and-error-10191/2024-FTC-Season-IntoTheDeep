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
        waitForStart();
Claw.OpenClaw();
Wait(1);
Claw.CloseClaw();
Wait(1);
Claw.RotateClaw(0.5);
Wait(1);
Claw.ExtendClaw( 0.5);
Wait(1);

    }
}
