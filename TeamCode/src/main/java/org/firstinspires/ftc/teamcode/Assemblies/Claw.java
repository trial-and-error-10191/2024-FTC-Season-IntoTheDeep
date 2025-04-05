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
    boolean clawOpen = true;

    public Claw(HardwareMap hwMap, Telemetry telemetry) {
        rightServo = hwMap.get(Servo.class,"rightservo");
        leftServo  = hwMap.get(Servo.class,"leftservo");
        rightServo.setPosition(servoPosition);
        leftServo.setPosition(1.0);
        this.telemetry = telemetry;
    }

    public void open(boolean gamepad) {
        if (gamepad) {
            rightServo.setPosition(1.0);
            leftServo.setPosition(0.0);
            clawOpen = true;
        }
    }

    public void close(boolean gamepad){
        if (gamepad) {
            rightServo.setPosition(0.0);
            leftServo.setPosition(1.0);
            clawOpen = false;
        }
    }

    public void clawTelemetry() {
        telemetry.addData("Limit Switch", "%b", clawOpen);
    }

}
