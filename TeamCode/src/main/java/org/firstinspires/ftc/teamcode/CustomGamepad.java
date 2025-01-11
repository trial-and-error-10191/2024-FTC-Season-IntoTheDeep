//This file is specifically for merging the two gamepads.

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class CustomGamepad extends Gamepad {

   public void mergeGamepads(Gamepad gamepad1, Gamepad gamepad2) {

       // all of the booleans mentioned in this segment are NOT any other kind of controller but the one we are using now.

       this.a = gamepad1.a || gamepad2.a;
       this.b = gamepad1.b || gamepad2.a;
       this.x = gamepad1.x || gamepad2.x;
       this.y = gamepad1.y || gamepad2.y;
       this.dpad_down = gamepad1.dpad_down || gamepad2.dpad_down;
       this.dpad_left = gamepad1.dpad_left || gamepad2.dpad_left;
       this.dpad_up = gamepad1.dpad_up || gamepad2.dpad_up;
       this.dpad_right = gamepad1.dpad_right || gamepad2.dpad_right;
       this.left_bumper = gamepad1.left_bumper || gamepad2.left_bumper;
       this.right_bumper = gamepad1.right_bumper || gamepad2.right_bumper;
       this.left_stick_button = gamepad1.left_stick_button || gamepad2.left_stick_button;
       this.right_stick_button = gamepad1.right_stick_button || gamepad2.right_stick_button;

       // all of the floats in this segment are NOT any other kind of controller but the one we are using now.

       this.left_stick_x = (gamepad1.left_stick_x + gamepad2.left_stick_x) / 2;
       this.left_stick_y = (gamepad1.left_stick_y + gamepad2.left_stick_y) / 2;
       this.right_stick_x = (gamepad1.right_stick_x + gamepad2.right_stick_x) / 2;
       this.right_stick_y = (gamepad1.right_stick_y + gamepad2.right_stick_y) / 2;
       this.left_trigger = (gamepad1.left_trigger + gamepad2.left_trigger) / 2;
       this.right_trigger = (gamepad1.right_trigger + gamepad2.right_trigger) / 2;

   } //end of public void mergeGamepads

} //end of class CustomGamepad