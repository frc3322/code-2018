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

        this.desiredDistance = meters / 4.9;
    }

    @Override
    protected void initialize() {
        angle = drivetrain.navx.getAngle();
        drivetrain.navx.resetDisplacement();
    }

    @Override
    protected void execute() {
        double curDistance = Math.sqrt(Math.pow(drivetrain.navx.getDisplacementX() * Math.cos(Math.toRadians(angle)), 2)
                + Math.pow(drivetrain.navx.getDisplacementX() * Math.sin(Math.toRadians(angle)), 2));

        deltaDistance = desiredDistance - curDistance;
        drivetrain.driveAngle(1, angle);

        SmartDashboard.putNumber("Displacement X", drivetrain.navx.getDisplacementX());
        SmartDashboard.putNumber("Displacement Y", drivetrain.navx.getDisplacementY());
        SmartDashboard.putNumber("Displacement Z", drivetrain.navx.getDisplacementZ());
        System.out.println("deltaDistance: " + deltaDistance);
    }

    @Override
    protected boolean isFinished() {
        return deltaDistance < 1;
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