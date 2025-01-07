package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class SplitResponsibilityGamepad extends Gamepad {
    public SplitResponsibilityGamepad() {}
    public void MergeGamepads(Gamepad player1, Gamepad player2) {
        if (player1 != null && player2 != null) {
            // Controls from player 1
            this.left_stick_y = player1.left_stick_y;
            this.left_stick_x = player1.left_stick_x;
            this.left_stick_button = player1.left_stick_button;
            this.right_stick_y = player1.right_stick_y;
            this.right_stick_x = player1.right_stick_x;
            this.right_stick_button = player1.right_stick_button;

            this.left_bumper = player1.left_bumper;
            this.right_bumper = player1.right_bumper;
            this.left_trigger = player1.left_trigger;
            this.right_trigger = player1.right_trigger;

            // Controls from player 2
            this.a = player2.a;
            this.b = player2.b;
            this.x = player2.x;
            this.y = player2.y;

            this.dpad_down = player2.dpad_down;
            this.dpad_left = player2.dpad_left;
            this.dpad_up = player2.dpad_up;
            this.dpad_right = player2.dpad_right;
        } else if (player1 == null) {
            this.CopyGamepad(player2);
        } else { // player2 == null
            this.CopyGamepad(player1);
        }
    }


    private void CopyGamepad(Gamepad gamepad) {
        this.a = gamepad.a;
        this.b = gamepad.b;
        this.x = gamepad.x;
        this.y = gamepad.y;

        this.dpad_right = gamepad.dpad_right;
        this.dpad_up = gamepad.dpad_up;
        this.dpad_left = gamepad.dpad_left;
        this.dpad_down = gamepad.dpad_down;

        this.left_bumper = gamepad.left_bumper;
        this.right_bumper = gamepad.right_bumper;
        this.left_trigger = gamepad.left_trigger;
        this.right_trigger = gamepad.right_trigger;

        this.left_stick_button = gamepad.left_stick_button;
        this.left_stick_x = gamepad.left_stick_x;
        this.left_stick_y = gamepad.left_stick_y;
        this.right_stick_button = gamepad.right_stick_button;
        this.right_stick_x = gamepad.right_stick_x;
        this.right_stick_y = gamepad.right_stick_y;
    }
}
