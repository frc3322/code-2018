package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private final double desiredDistance;
    private double deltaDistance = 0;
    private double angle;
    private double speed;
    private double initialDisplacement;

    public DriveDistance(double meters, double speed) {
        requires(drivetrain);

        this.desiredDistance = meters;
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        angle = drivetrain.navx.getAngle();
        initialDisplacement = drivetrain.getTotalDisplacement();
    }

    @Override
    protected void execute() {
        SmartDashboard.putBoolean("Driving distance", true);
        double curDistance = drivetrain.getTotalDisplacement() - initialDisplacement;

        deltaDistance = desiredDistance - curDistance;
        drivetrain.driveAngle(speed, angle);

        SmartDashboard.putNumber("Delta distance", deltaDistance);
    }

    @Override
    protected boolean isFinished() {
        return deltaDistance < .5;
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