   package org.firstinspires.ftc.teamcode;
   import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
   import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
   import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
   import com.qualcomm.robotcore.util.ElapsedTime;

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
           while (opModeIsActive()) {

               // This controls the drive train using three double input methods.
               // The fourth input is a boolean for the direction toggle.
               // The last input is the time the function uses to space out inputs for the direction switch.
               robot.driveTrain.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
               // Makes the claw open/close
               robot.sampleClaw.clawClamp(gamepad2.a);
               // Makes the claw extend/contract
               robot.sampleClaw.clawExtend(gamepad2.left_bumper, gamepad2.right_bumper);
               // Makes the claw rotate
               robot.sampleClaw.clawRotate(gamepad2.left_trigger, gamepad2.right_trigger);
               // Makes the limb arm extend/contract
               robot.limbArm.armExtend(gamepad2.dpad_up, gamepad2.dpad_down);
               // Makes the limb arm rotate
               robot.limbArm.armRotate(gamepad2.dpad_left, gamepad2.dpad_right);

               // Provides telemetry for all motors, servos, and sensors.
               telemetry.addData("Front Driving Motors (Left, Right)", "%4.2f, %4.2f",
                       robot.driveTrain.leftFrontDrive.getPower(),
                       robot.driveTrain.rightFrontDrive.getPower());
               telemetry.addData("Back Driving Motors (Left, Right)", "%4.2f, %4.2f",
                       robot.driveTrain.leftBackDrive.getPower(),
                       robot.driveTrain.rightBackDrive.getPower());
               telemetry.addData("Scooper Arm", "%4.2f",
                       robot.scoopyArm.getPower());
               telemetry.addData("Max Limit Switch Status", "%b",
                       robot.scoopyArm.getLimitMax());
               telemetry.addData("Lower Limit Switch Status", "%b",
                       robot.scoopyArm.getLimitLower());
               telemetry.update();
           }
       }
   }

