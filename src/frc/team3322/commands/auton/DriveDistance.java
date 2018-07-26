package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private double MAX_SPEED = .7;

    private final double desiredDistance;

    private double straightAngle;
    private double distanceFromTarget = 0;
    private double initialDisplacement;

    public DriveDistance(double distance) {
        requires(drivetrain);

        this.desiredDistance = distance;
    }

    public DriveDistance(double distance, double speed) {
        this(distance);

        this.MAX_SPEED = speed;
    }

    @Override
    protected void initialize() {
        initialDisplacement = drivetrain.getRobotDisplacement();
        straightAngle = drivetrain.navx.getAngle();
        drivetrain.driveAngleInit(straightAngle);
    }

    @Override
    protected void execute() {
        double curDistance = drivetrain.getRobotDisplacement() - initialDisplacement;
        distanceFromTarget = desiredDistance - curDistance;

        double speed;
        // Increment speed the first half, decrement speed the second half
        if (curDistance < desiredDistance/2) {
            speed = curDistance/(desiredDistance/2);
        } else {
            speed = (desiredDistance/2)/curDistance;
        }
        speed = speed * MAX_SPEED + 0.25;
        speed *= distanceFromTarget/Math.abs(distanceFromTarget);

        // TODO: fix motion profiling
        drivetrain.driveAngle(MAX_SPEED);
        SmartDashboard.putNumber("DriveDistance remaining", distanceFromTarget);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(distanceFromTarget) < 2;
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