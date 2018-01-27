package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class TurnAngle extends Command {
    private final double angle;

    public TurnAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);

        this.angle = angle;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        drivetrain.driveAngle(0, angle);
    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
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
