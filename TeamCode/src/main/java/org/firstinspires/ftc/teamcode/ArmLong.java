package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmLong {
    // motor instantiating
    DcMotor MainMoto = null;
    DcMotor RotationMoto = null;
    // Variable Instantiating
double EncoderUpperLimit = 3000;
double EncoderBottomLimit = 0;
double EncoderRotationForwardLimit = 1000;
double EncoderRotationBackwardLimit = -1000;
double Deadzone = 0.05;
    static final double     COUNTS_PER_MOTOR_REV    = 537.6;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.5;     // For figuring circumference, 3.5 for small wheels, 5.5 for big wheels
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.5;
    static final double     TURN_SPEED              = 1;


    //Hardware map stuff
    public ArmLong(HardwareMap hwMap, Telemetry telemetry) {
        MainMoto = hwMap.get(DcMotor.class, "Extender");
        RotationMoto = hwMap.get(DcMotor.class, "Rotator");
        MainMoto.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RotationMoto.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MainMoto.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RotationMoto.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    //Tele-operation Function
    public void TeleOpExtend(double straightPower, double RotationPower) {
       // for the straight motor
        if (Math.abs(straightPower) > Deadzone) {
            if (MainMoto.getCurrentPosition() < EncoderUpperLimit && MainMoto.getCurrentPosition() > EncoderBottomLimit) {
                MainMoto.setPower(straightPower);
            } else if (MainMoto.getCurrentPosition() < EncoderBottomLimit) {
                MainMoto.setPower(1);
            } else if (MainMoto.getCurrentPosition() > EncoderUpperLimit) {
                MainMoto.setPower(-1);
            }
        }
        // for the rotation motor
        if (Math.abs(RotationPower) > Deadzone) {
            if (RotationMoto.getCurrentPosition() < EncoderRotationForwardLimit && RotationMoto.getCurrentPosition() > EncoderRotationBackwardLimit) {
                RotationMoto.setPower(straightPower);
            } else if (RotationMoto.getCurrentPosition() < EncoderRotationBackwardLimit) {
                RotationMoto.setPower(1);
            } else if (RotationMoto.getCurrentPosition() > EncoderRotationForwardLimit) {
                RotationMoto.setPower(-1);
            }
        }
    }
    // Autonomous function
    public void AutoExtend(double Length) {


    }
}
