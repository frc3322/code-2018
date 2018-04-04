package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private final double desiredDistance;
    private double speed = .7;

    private double straightAngle;
    private double distanceFromTarget = 0;
    private double initialDisplacement;

    public DriveDistance(double distance) {
        requires(drivetrain);

        this.desiredDistance = distance;
    }

    public DriveDistance(double distance, double speed) {
        this(distance);

        this.speed = speed;
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
        speed = speed * this.speed + 0.2;
        speed *= distanceFromTarget/Math.abs(distanceFromTarget);

        drivetrain.driveAngle(this.speed);

        SmartDashboard.putNumber("DriveDistance remaining", distanceFromTarget);
    }

    @Override
    protected boolean isFinished() {
        return distanceFromTarget < 2;
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