package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private final double desiredDistance;
    private double deltaDistance = 0;
    private double angle;

    public DriveDistance(double meters) {
        requires(drivetrain);

        this.desiredDistance = meters;
    }

    @Override
    protected void initialize() {
        angle = drivetrain.navx.getAngle();
        drivetrain.navx.resetDisplacement();
    }

    @Override
    protected void execute() {
        SmartDashboard.putBoolean("Driving distance", true);
        double curDistance = Math.sqrt(Math.pow(drivetrain.navx.getDisplacementX(), 2)
                + Math.pow(drivetrain.navx.getDisplacementY(), 2));

        deltaDistance = desiredDistance - curDistance;
        drivetrain.driveAngle(1, angle);

        SmartDashboard.putNumber("Delta distance", deltaDistance);
    }

    @Override
    protected boolean isFinished() {
        return deltaDistance < 1;
    }

    @Override
    protected void end() {
        drivetrain.stop();
        SmartDashboard.putBoolean("Driving distance", false);
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}