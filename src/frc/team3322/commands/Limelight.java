package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends Command{

    double Kp = -0.1f;
    edu.wpi.first.networktables.NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry targetx = table.getEntry("tx");


    double tx = targetx.getDouble(0);
    double aim_error = tx;
    double steering_adjust = Kp*aim_error;
    double AimMinCmd = 0.095f;

    float KpDistance = -0.1f;
    NetworkTableEntry distance_error = table.getEntry("ty");

    SmartDashboard.putNumber("distance_error", distance_error);

    protected void execute() {
        driving_adjust = KpDistance * distance_error;

        left_command += distance_adjust;
        right_command += distance_adjust;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
