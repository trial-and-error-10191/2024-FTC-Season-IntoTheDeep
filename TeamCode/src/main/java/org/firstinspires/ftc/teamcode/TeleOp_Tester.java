package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This TeleOp file is for testing only

@TeleOp(name = "TeleOp_Tester", group = "LinearOpMode")
public class TeleOp_Tester extends LinearOpMode {

    @Override

    public void runOpMode(){

        CustomGamepad customGamepad = new CustomGamepad();

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            //
            customGamepad.mergeGamepads(gamepad1, gamepad2);
            telemetry.addData("Gamepad1.a Merge Data", "%b",
                    robot.scoopyArm.getLimitLower());


        }

    }

}