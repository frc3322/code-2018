package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class ArcToAngle extends Command {
    private final double desiredAngle;
    private final double radius;
    private final double angularSpeedMod;

    public ArcToAngle(double degrees, double radius) {
        requires(drivetrain);

        this.desiredAngle = drivetrain.navx.getAngle() + degrees;
        this.radius = radius;
        this.angularSpeedMod = 1;
    }

    @Override
    protected void execute() {
        double omegaError = desiredAngle - drivetrain.navx.getRate();

        drivetrain.driveArc(radius, angularSpeedMod);
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
