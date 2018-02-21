package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;

import static frc.team3322.Robot.drivetrain;
import static frc.team3322.Robot.oi;

public class DriveControl extends Command {

    private final int SPEED_AXIS;
    private final int ROTATION_AXIS;

    public DriveControl() {
        requires(drivetrain);

        SPEED_AXIS = RobotMap.XBOX.STICK_L_Y_AXIS;
        ROTATION_AXIS = RobotMap.XBOX.STICK_R_X_AXIS;
    }

    @Override
    protected void initialize() {
        drivetrain.driveStraightInit();
    }

    @Override
    protected void execute() {
        double speedInput = -oi.stick.getRawAxis(SPEED_AXIS);
        double rotationInput = oi.stick.getRawAxis(ROTATION_AXIS);

        drivetrain.driveStraight(speedInput, rotationInput);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
