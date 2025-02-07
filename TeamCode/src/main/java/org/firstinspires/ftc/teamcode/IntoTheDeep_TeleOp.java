   package org.firstinspires.ftc.teamcode;
   import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
   import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This file is the main TeleOp file.

   @TeleOp(name = "IntoTheDeep TeleOp", group = "LinearOpMode")
   public class IntoTheDeep_TeleOp extends LinearOpMode {

       @Override

       public void runOpMode() {

           // Initiates the robots system and subsystems!
           Robot robot = new Robot(hardwareMap, telemetry);

           telemetry.addData("Status", "Waiting for Start");
           telemetry.update();

           waitForStart();
           robot.limbArm.initRotateByPower();
           while (opModeIsActive()) {
               // Driving
               robot.driveTrain.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
           }
       }
   }