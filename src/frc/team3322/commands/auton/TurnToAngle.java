package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class TurnToAngle extends Command {
    private final double desiredAngle;
    private final double turnAngle;

    public TurnToAngle(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        turnAngle = degrees;
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
        drivetrain.drive(0, turnAngle);
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
