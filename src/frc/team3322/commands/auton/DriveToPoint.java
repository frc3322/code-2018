package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

import java.awt.geom.Point2D;

import static frc.team3322.Robot.drivetrain;


public class DriveToPoint extends Command {
	private final Point2D point;
	private Point2D error = new Point2D.Double();

	public DriveToPoint(Point2D point) {
		// Use requires() here to declare subsystem dependencies
		requires(drivetrain);

		this.point = point;
	}

	@Override
	protected void initialize() {
		drivetrain.resetDisplacement();
	}

	@Override
	protected void execute() {
		double dy = point.getY() - drivetrain.navx.getDisplacementY();
		double dx = point.getX() - drivetrain.navx.getDisplacementX();
		double angle = Math.atan2(dy, dx);
		error.setLocation(dx, dy);

		drivetrain.driveAngle(1, angle);
	}

	@Override
	protected boolean isFinished() {
		return error.distance(0, 0) < 1;
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
