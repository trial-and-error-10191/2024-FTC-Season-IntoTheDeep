package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class ArmLong {
    // motor instantiating
    DcMotor MainMoto = null;
    // Variable Instantiating



    //Hardware map stuff
    public ArmLong(HardwareMap hwMap, Telemetry telemetry) {
        MainMoto = hwMap.get(DcMotor.class, "Extender");
    }
    //Tele-operation Function
    public void TeleOpExtend(double power) {
        if (power > 0.05)
            if (MainMoto.getCurrentPosition() < 3000 && MainMoto.getCurrentPosition() > 0) {
                MainMoto.setPower(power);
            }
    }

    public void AutoExtend(double Length) {


    }
}
