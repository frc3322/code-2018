package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class TurnToAngle extends Command {
    private final double desiredAngle;

    public TurnToAngle(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        setTimeout(5);

        double initialAngle = drivetrain.navx.getAngle();
        desiredAngle = initialAngle + degrees;
    }

    @Override
    protected void initialize() {
        drivetrain.driveAngleInit(desiredAngle);
    }

    @Override
    protected void execute() {
        drivetrain.driveAngle(0);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(desiredAngle - drivetrain.navx.getAngle()) < 1 || isTimedOut();
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
