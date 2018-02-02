package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private final double desiredDistance;
    private double errorDistance = 0;

    public DriveDistance(double meters) {
        requires(drivetrain);

        this.desiredDistance = meters;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double angle = drivetrain.navx.getAngle();
        double curDistance = drivetrain.navx.getDisplacementX() * Math.cos(Math.toRadians(angle))
                + drivetrain.navx.getDisplacementY() * Math.sin(Math.toRadians(angle));

        errorDistance = desiredDistance - curDistance;
        drivetrain.drive(1, 0);
    }

    @Override
    protected boolean isFinished() {
        return errorDistance < 1;
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
