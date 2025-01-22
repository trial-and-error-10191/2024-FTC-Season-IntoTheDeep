package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ClawMechanism {
    private final Servo elbowServo;
    private final double lowestBendPosition = 0.0;
    private final double highestBendPosition = 1.0;
    private double bendPosition = 0.0;

    private final Servo rotateServo;
    private final double clockwisePositionLimit = 1.0;
    private final double counterclockwisePositionLimit = 0.0;
    private double rotationPosition = 0.0;

    private final Servo clampServo;
    private boolean clawIsOpen = false;
    private final double clawOpenPosition = 1.0;
    private final double clawClosedPosition = 0.0;
    private boolean previousInput = false;

    private final double INCREMENT = 0.01;
    private final Telemetry telemetry;

    ClawMechanism(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        elbowServo = hwMap.get(Servo.class, "elbow_servo");
        rotateServo = hwMap.get(Servo.class, "rotate_servo");
        clampServo = hwMap.get(Servo.class, "clamp_servo");
    }

    public void update(Gamepad gamepad) {
        rotate(gamepad);
        bend(gamepad);
        clamp(gamepad);
    }

    private void rotate(Gamepad gamepad) {
        // right trigger - rotate claw clockwise
        // left trigger - rotate claw counter-clockwise
        float clockwise_power = gamepad.right_trigger;
        float cc_power = gamepad.left_trigger;
        float total_power = Range.clip(clockwise_power - cc_power, -1, 1);
        rotationPosition += Math.signum(total_power) * INCREMENT;
        rotationPosition = Range.clip(rotationPosition, counterclockwisePositionLimit, clockwisePositionLimit);

        rotateServo.setPosition(rotationPosition);
    }

    private void bend(Gamepad gamepad) {
        // right bumper - bend claw down
        // left bumper - bend claw up
        boolean bend_down = gamepad.right_bumper;
        boolean bend_up = gamepad.left_bumper;

        boolean both_pressed = bend_down && bend_up;
        boolean neither_pressed = !bend_down && !bend_up;
        if (both_pressed || neither_pressed) {
            bendPosition = elbowServo.getPosition();
        } else if (bend_down) {
            bendPosition = elbowServo.getPosition() - INCREMENT;
        } else {
            bendPosition = elbowServo.getPosition() + INCREMENT;
        }
        bendPosition = Range.clip(bendPosition, lowestBendPosition, highestBendPosition);

        elbowServo.setPosition(bendPosition);
    }

    private void clamp(Gamepad gamepad) {
        // a button - open/close claw
        // want to move the claw if previously button was not pressed and now it is pressed
        boolean moveClaw = (!previousInput && gamepad.a);
        if (moveClaw) {
            if (clawIsOpen) { // close claw if it's open
                clampServo.setPosition(clawClosedPosition);
                clawIsOpen = false;
            } else { // open claw if it's closed
                clampServo.setPosition(clawOpenPosition);
                clawIsOpen = true;
            }
        }
        previousInput = gamepad.a;
    }
}
