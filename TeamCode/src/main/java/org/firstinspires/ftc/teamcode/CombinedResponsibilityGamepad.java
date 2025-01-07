package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class CombinedResponsibilityGamepad extends Gamepad {
    CombinedResponsibilityGamepad() {}


    public void MergeGamepads(Gamepad player1, Gamepad player2) {
        if (player1 != null && player2 != null) {
            // Controls from player 1
            this.left_stick_y = Range.clip(player1.left_stick_y + player2.left_stick_x, -1.0f, 1.0f);
            this.left_stick_x = Range.clip(player1.left_stick_x + player2.left_stick_x, -1.0f, 1.0f);
            this.left_stick_button = player1.left_stick_button || player2.left_stick_button;
            this.right_stick_y = Range.clip(player1.right_stick_y + player2.right_stick_y, -1.0f, 1.0f);
            this.right_stick_x = Range.clip(player1.right_stick_x + player2.right_stick_x, -1.0f, 1.0f);
            this.right_stick_button = player1.right_stick_button || player2.right_stick_button;

            this.left_bumper = player1.left_bumper || player2.left_bumper;
            this.right_bumper = player1.right_bumper || player2.right_bumper;
            this.left_trigger = Range.clip(player1.left_trigger + player2.left_trigger, -1.0f, 1.0f);
            this.right_trigger = Range.clip(player1.right_trigger + player2.right_trigger, -1.0f, 1.0f);

            // Controls from player 2
            this.a = player1.a || player2.a;
            this.b = player1.b || player2.b;
            this.x = player1.x || player2.x;
            this.y = player1.y || player2.y;

            this.dpad_down = player1.dpad_down || player2.dpad_down;
            this.dpad_left = player1.dpad_left || player2.dpad_left;
            this.dpad_up = player1.dpad_up || player2.dpad_up;
            this.dpad_right = player1.dpad_right || player2.dpad_right;
        } else if (player1 == null && player2 == null) {
            SetDefaultValues();
        }else if (player1 == null) {
            CopyGamepad(player2);
        } else { // player2 == null
            CopyGamepad(player1);
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


    private void SetDefaultValues() {
        this.a = false;
        this.b = false;
        this.x = false;
        this.y = false;

        this.dpad_right = false;
        this.dpad_up = false;
        this.dpad_left = false;
        this.dpad_down = false;

        this.left_bumper = false;
        this.right_bumper = false;
        this.left_trigger = 0.0f;
        this.right_trigger = 0.0f;

        this.left_stick_button = false;
        this.left_stick_x = 0.0f;
        this.left_stick_y = 0.0f;
        this.right_stick_button = false;
        this.right_stick_x = 0.0f;
        this.right_stick_y = 0.0f;
    }
}
