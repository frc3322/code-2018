package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class DriveDistance extends Command {
    private final double distance;
    private double error = 0;

    public DriveDistance(double distance) {
        requires(drivetrain);

        this.distance = distance;
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        error = distance - drivetrain.getDisplacement().getX();
        drivetrain.drive(1, 0);
    }

    @Override
    protected boolean isFinished() {
        return error < 1;
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
