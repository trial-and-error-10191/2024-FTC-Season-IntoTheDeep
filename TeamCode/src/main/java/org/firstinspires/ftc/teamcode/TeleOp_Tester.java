package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This TeleOp file is for testing only



@TeleOp(name = "TeleOp_Tester", group = "LinearOpMode")
public class TeleOp_Tester extends LinearOpMode {

    @Override

    public void runOpMode(){

        // Initiates the robots system and subsystems!
        Robot robot = new Robot(hardwareMap, telemetry);

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

        }

    }

}
