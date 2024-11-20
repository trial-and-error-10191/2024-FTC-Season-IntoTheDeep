package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Operation HueHue (Concept: StopMotor)", group = "LinearOpMode")
public class ConceptStopMotor extends LinearOpMode {
    // levi was here

    // @Override

    // objects

    private DcMotor leftFront   = null;
    private DcMotor         rightFront  = null;
    private ElapsedTime     runtime = new ElapsedTime();
    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    TouchSensor touchSensor;  // Touch sensor Object
    ElapsedTime timer = new ElapsedTime();
    double seconds = 1;

    // no touchey
    public void runOpMode() {

        Robot robot = new Robot(hardwareMap, telemetry);
        telemetry.addData("Status", "START NOW COUNTDOWN T-MINUS 20 SECONDS TOTAL");
        telemetry.update();

        touchSensor = hardwareMap.get(TouchSensor.class, "sensor_touch");


        waitForStart();
        while (opModeIsActive()) {

//            if (touchSensor.isPressed()) {
//                telemetry.addData("Touch Sensor", "Is Pressed");
//                robot.driveTrain.stop();
//                timer.reset();
//                while (timer.seconds() < .5) {
//                    // Do nothing
//                }
                robot.driveTrain.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

//            } else {
//                telemetry.addData("Touch Sensor", "Is Not Pressed");
//                robot.driveTrain.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//            }
            // Provides telemetry for all motors, servos, and sensors.
            telemetry.addData("Front Driving Motors (Left, Right)", "%4.2f, %4.2f",
                    robot.driveTrain.leftFrontDrive.getPower(),
                    robot.driveTrain.rightFrontDrive.getPower());
            telemetry.addData("Back Driving Motors (Left, Right)", "%4.2f, %4.2f",
                    robot.driveTrain.leftBackDrive.getPower(),
                    robot.driveTrain.rightBackDrive.getPower());
            telemetry.update();

        }
    }
}