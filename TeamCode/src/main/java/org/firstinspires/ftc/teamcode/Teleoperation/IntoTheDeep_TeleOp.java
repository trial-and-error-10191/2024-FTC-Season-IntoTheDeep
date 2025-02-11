   package org.firstinspires.ftc.teamcode.Teleoperation;
   import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
   import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

   import org.firstinspires.ftc.teamcode.Assemblies.Robot;


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

               robot.driveTrain.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
               // Makes the claw open/close
               robot.sampleClaw.clawClamp(gamepad2.a);
               // Makes the claw extend/contract
               robot.sampleClaw.clawExtend(gamepad2.left_bumper, gamepad2.right_bumper, gamepad2.y);
               // Makes the claw rotate
               robot.sampleClaw.clawRotate(gamepad2.left_trigger, gamepad2.right_trigger, gamepad2.y);

               // Makes the limb arm extend/contract, and gives the option to have precise movement
               if (gamepad2.left_stick_y < 0.05 && gamepad2.left_stick_y > -0.05) {   // Makes sure there's no drifting
                   gamepad2.left_stick_y = 0;
               }
               robot.limbArm.RunMotor(-gamepad2.left_stick_y);

               // Makes the limb arm rotate
               if (gamepad2.right_stick_x < 0.05 && gamepad2.right_stick_x > -0.05) { // Makes sure there's no drifting
                   gamepad2.right_stick_x = 0;
               }
               //robot.driveTrain.LiftHandle(robot.limbArm.limbExtend.getCurrentPosition());
               //robot.limbArm.armRotate(gamepad2.right_stick_y);
               robot.limbArm.rotateByPower(gamepad2.right_stick_y);
               // Spool correction stuff
               robot.limbArm.spoolCorrection(gamepad1.dpad_up, gamepad1.dpad_down);
               // Makes the arm rise to the high net for samples
//               robot.limbArm.goUpToHighNet(gamepad1.y);
//               // Makes the arm prepped to grab a sample from the submersible
//               robot.limbArm.turnToSubmersible(gamepad1.x);
//               // Makes the arm rise to the high bar for specimens
//               robot.limbArm.goUpToHighBar(gamepad1.b);

               // Provides telemetry for all motors, servos, and sensors.
//               telemetry.addData("Front Driving Motors (Left, Right)", "%4.2f, %4.2f",
//                       robot.driveTrain.leftFrontDrive.getPower(),
//                       robot.driveTrain.rightFrontDrive.getPower());
//               telemetry.addData("Back Driving Motors (Left, Right)", "%4.2f, %4.2f",
//                       robot.driveTrain.leftBackDrive.getPower(),
//                       robot.driveTrain.rightBackDrive.getPower());
//               telemetry.addData("Extending limb (Extend)", "%4.2f",
//                       robot.limbArm.limbExtend.getPower());
//               telemetry.addData("Rotating limb (Rotate)", "%4.2f",
//                       robot.limbArm.limbRotate.getPower());
//               telemetry.addData("Extending power (Extend)", "%4.2f",
//                       gamepad2.left_stick_y);
//               telemetry.addData("Rotating power (Rotate)", "%4.2f",
//                       gamepad2.right_stick_x);
//               telemetry.addData("Extending encoder (Extend)", "%5d",
//                       robot.limbArm.limbExtend.getCurrentPosition());
//               telemetry.addData("Rotating encoder (Rotate)", "%5d",
//                       robot.limbArm.limbRotate.getCurrentPosition());
               telemetry.addData("Claw servo (Grip)", "%4.2f",
                       robot.sampleClaw.servoClamp.getPosition());
               telemetry.addData("Extend Encoder Count: d%", robot.limbArm.limbExtend.getCurrentPosition());
               telemetry.addData("Extend Target: d%", robot.limbArm.limbExtend.getTargetPosition());
               telemetry.addData("", "");
               telemetry.addData("Rotation Encoder Count: ", "%d", robot.limbArm.limbRotate.getCurrentPosition());
               telemetry.update();
           }
       }
   }