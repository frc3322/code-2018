package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import java.awt.geom.Point2D;

import static frc.team3322.Robot.drivetrain;


public class DriveToPoint extends Command {
	private final double finalX;
	private final double finalY;

	private double errorX;
	private double errorY;

	public DriveToPoint(Point2D point) {
		this(point.getX(), point.getY());
	}

	public DriveToPoint(double x, double y) {
		// Use requires() here to declare subsystem dependencies
		requires(drivetrain);

		this.finalX = x;
		this.finalY = y;
	}

	@Override
	protected void initialize() {
		drivetrain.navx.resetDisplacement();
	}

	@Override
	protected void execute() {
		double dy = finalY - drivetrain.navx.getDisplacementY();
		double dx = finalX - drivetrain.navx.getDisplacementX();
		double angle = Math.atan2(dy, dx);

		errorX = dx;
		errorY = dy;

		drivetrain.driveAngle(1, angle);
	}

	@Override
	protected boolean isFinished() {
		return errorX < .1 && errorY < .1;
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