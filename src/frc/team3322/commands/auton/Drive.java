package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3322.Robot.drivetrain;


public class Drive extends Command {
    private double speed = .65;

    private double straightAngle;

    public Drive() {
        requires(drivetrain);
    }

    public Drive(double speed) {
        this();

        this.speed = speed;
    }

    @Override
    protected void initialize() {
        straightAngle = drivetrain.navx.getAngle();
        drivetrain.driveAngleInit(straightAngle);
    }

    @Override
    protected void execute() {
        drivetrain.driveAngle(speed);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
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