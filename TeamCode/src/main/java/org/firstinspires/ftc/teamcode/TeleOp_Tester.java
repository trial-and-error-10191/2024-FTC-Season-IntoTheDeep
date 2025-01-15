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

            // This is to test to see if the merge code I made works or not

            customGamepad.mergeGamepads(gamepad1, gamepad2);
            // This first part of the telemetry is the booleans mentioned in the previous CustomGamepad file.

            telemetry.addData("Gamepad1.a Data", "%b",
                    gamepad1.a);
            telemetry.addData("Gamepad1.b Data", "%b",
                    gamepad1.b);
            telemetry.addData("Gamepad1.x Data", "%b",
                    gamepad1.x);
            telemetry.addData("Gamepad1.y Data", "%b",
                    gamepad1.y);
            telemetry.addData("Gamepad2.a Data", "%b",
                    gamepad2.a);
            telemetry.addData("Gamepad2.b Data", "%b",
                    gamepad2.b);
            telemetry.addData("Gamepad2.x Data", "%b",
                    gamepad2.x);
            telemetry.addData("Gamepad2.y Data", "%b",
                    gamepad2.y);
            telemetry.addData("Gamepad1.dpad_up Data", "%b",
                    gamepad1.dpad_up);
            telemetry.addData("Gamepad1.dpad_right Data", "%b",
                    gamepad1.dpad_right);
            telemetry.addData("Gamepad1.dpad_left Data", "%b",
                    gamepad1.dpad_left);
            telemetry.addData("Gamepad1.dpad_up Data", "%b",
                    gamepad1.dpad_down);
            telemetry.addData("Gamepad2.dpad_up Data", "%b",
                    gamepad2.dpad_up);
            telemetry.addData("Gamepad2.dpad_right Data", "%b",
                    gamepad2.dpad_right);
            telemetry.addData("Gamepad2.dpad_left Data", "%b",
                    gamepad2.dpad_left);
            telemetry.addData("Gamepad2.dpad_down Data", "%b",
                    gamepad2.dpad_down);
            telemetry.addData("Gamepad1.left_bumper Data", "%b",
                    gamepad1.left_bumper);
            telemetry.addData("Gamepad2.left_bumper Data", "%b",
                    gamepad2.left_bumper);
            telemetry.addData("Gamepad1.right_bumper Data", "%b",
                    gamepad1.right_bumper);
            telemetry.addData("Gamepad2.right_bumper Data", "%b",
                    gamepad2.right_bumper);
            telemetry.addData("Gamepad1.left_stick_button Data", "%b",
                    gamepad1.left_stick_button);
            telemetry.addData("Gamepad2.left_stick_button Data", "%b",
                    gamepad2.left_stick_button);
            telemetry.addData("Gamepad1.right_stick_button Data", "%b",
                    gamepad1.right_stick_button);
            telemetry.addData("Gamepad2.right_stick_button Data", "%b",
                    gamepad2.right_stick_button);

            // This part of the telemetry is the floats mentioned in the previous CustomGamepad file.

            telemetry.addData("Gamepad1.left_stick_x Data", "%b",
                    gamepad1.left_stick_x);
            telemetry.addData("Gamepad2.left_stick_x Data", "%b",
                    gamepad2.left_stick_x);
            telemetry.addData("Gamepad1.left_stick_y Data", "%b",
                    gamepad1.left_stick_y);
            telemetry.addData("Gamepad2.left_stick_y Data", "%b",
                    gamepad2.left_stick_y);
            telemetry.addData("Gamepad1.right_stick_x Data", "%b",
                    gamepad1.right_stick_x);
            telemetry.addData("Gamepad2.right_stick_x Data", "%b",
                    gamepad2.right_stick_x);
            telemetry.addData("Gamepad1.right_stick_y Data", "%b",
                    gamepad1.right_stick_y);
            telemetry.addData("Gamepad2.right_stick_y Data", "%b",
                    gamepad2.right_stick_y);
            telemetry.addData("Gamepad1.left_trigger Data", "%b",
                    gamepad1.left_trigger);
            telemetry.addData("Gamepad2.left_trigger Data", "%b",
                    gamepad2.left_trigger);
            telemetry.addData("Gamepad1.right_trigger Data", "%b",
                    gamepad1.right_trigger);
            telemetry.addData("Gamepad2.right_trigger Data", "%b",
                    gamepad2.right_trigger);

        } // end of while (opModeIsActive())

    } // end of public void runOpMode

} // end of class TeleOp_Tester