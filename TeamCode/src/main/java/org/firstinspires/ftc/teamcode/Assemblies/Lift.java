package org.firstinspires.ftc.teamcode.Assemblies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift {

    public DcMotor liftMotor1;              // expansion port 3
    public DcMotor liftMotor2;              // expansion port 2
    public DigitalChannel limitSwitch;      // control hub digital slot 5-4
    public DigitalChannel sensorTouch;      // expansion digital slot 4
    public DigitalChannel redLED;           // control hub digital slot 6
    public DigitalChannel greenLED;         // control hub digital slot 7
    public ElapsedTime runtime = new ElapsedTime();
    boolean toggle = false;
    Telemetry telemetry;

    public Lift(HardwareMap hwMap, Telemetry telemetry) {
        // Set up LEDs in robot's handles & power switch
        redLED = hwMap.get(DigitalChannel.class, "redLED");
        greenLED = hwMap.get(DigitalChannel.class, "greenLED");
        redLED.setMode(DigitalChannel.Mode.OUTPUT);
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);

        // Corresponds lift motor names to motor variables.
        liftMotor1 = hwMap.get(DcMotor.class, "LiftMotor1");
        liftMotor2 = hwMap.get(DcMotor.class, "LiftMotor2");
        // Set motor directions
        liftMotor1.setDirection(DcMotor.Direction.FORWARD);
        liftMotor2.setDirection(DcMotor.Direction.FORWARD);

        sensorTouch = hwMap.get(DigitalChannel.class, "sensor_touch");
        sensorTouch.setMode(DigitalChannel.Mode.INPUT);
        limitSwitch = hwMap.get(DigitalChannel.class, "limitSwitch");

        this.telemetry = telemetry;
    }

    public void toggleOn(boolean gamepad) {
        if (gamepad) {greenLED.setState(true);}
      }

    public void toggleOff(boolean gamepad) {
        if (gamepad) {greenLED.setState(false);}
    }
//    public void toggleLEDs(boolean gamepad) {
//        if (gamepad && runtime.time() > .75) {
//            toggle = !toggle;
//            if(toggle) {
//                redLED.setState(false);
//                greenLED.setState(true);
//            }
//            else{
//                redLED.setState(true);
//                greenLED.setState(false);
//            }
//            runtime.reset();
//        }
//    }
//
//    public void dualLift(double power) {
//        liftMotor1.setPower(power);
//        liftMotor2.setPower(power);
//    }
//
//    public void moveUp(float gamepad){
//        dualLift(gamepad);
//        if (!limitSwitch.getState() && gamepad > 0.0) {
//            dualLift(0.0);
//        }
//    }
//
//    public void moveDown(float gamepad){
//        dualLift(-gamepad);
//        if(!sensorTouch.getState() && gamepad < 0.0) {
//            dualLift(0.0);
//        }
//    }
//
    public void liftTelemetry(){
        // Telemetry info associated with the lift mechanism.
        telemetry.addData("GreenLED", "%b", greenLED.getState());
        telemetry.addData("Limit Switch", "%b", limitSwitch.getState());
        telemetry.addData("Touch Sensor", "%b", sensorTouch.getState());
//        telemetry.addData("Lift Motors", "%5.2f, %5.2f", liftMotor1.getPower(), liftMotor2.getPower());
    }

}
