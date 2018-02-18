package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private final double desiredDistance;
    private double speed = .65;

    private double straightAngle;
    private double deltaDistance = 0;
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

        deltaDistance = desiredDistance - curDistance;
        drivetrain.driveAngle(speed, straightAngle);

        SmartDashboard.putNumber("DriveDistance delta", deltaDistance);
    }

    @Override
    protected boolean isFinished() {
        return deltaDistance < 2;
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