package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    // Set up variables
    Servo rightServo;
    Servo leftServo;

    Telemetry telemetry;

    double servoPosition = 0.0;
    boolean clawOpen = false;
    boolean lastInput = false;

    public Claw(HardwareMap hwMap, Telemetry telemetry) {
        rightServo = hwMap.get(Servo.class,"rightservo");    // expansion servo slot 1?
        leftServo  = hwMap.get(Servo.class,"leftservo");     // expansion servo slot 2?
        rightServo.setPosition(servoPosition);
        leftServo.setPosition(1.0);
        this.telemetry = telemetry;
    }

    public void open(boolean gamepad) {
        if (!lastInput && gamepad) {
            clawOpen = !clawOpen;
            if (clawOpen) {
                rightServo.setPosition(1.0);
                leftServo.setPosition(0.0);
            } else {
                rightServo.setPosition(1.0);
                leftServo.setPosition(0.0);
            }
        }
        lastInput = gamepad;
    }

    public void clawTelemetry() {
        telemetry.addData("ClawOpen", "%b", clawOpen);
        telemetry.addData("rightServoPos", "%4.2f", rightServo.getPosition());
        telemetry.addData("leftServoPos", "%4.2f", leftServo.getPosition());
    }

}
