package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;

import static frc.team3322.Robot.drivetrain;
import static frc.team3322.Robot.oi;

public class DriveControl extends Command {

    private boolean straightModeStart;
    private boolean straightModeRun;
    private long runDelay;
    private double straightAngle;

    public DriveControl() {
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        straightModeStart = false;
    }

    @Override
    protected void execute() {
        double speedInput = -oi.stick.getRawAxis(RobotMap.XBOX.STICK_L_Y_AXIS);
        double rotationInput = oi.stick.getRawAxis(RobotMap.XBOX.STICK_R_X_AXIS);

        if (Math.abs(rotationInput) < .15) {
            if (Math.abs(speedInput) > .15) {
                if (!straightModeStart) {
                    straightModeStart = true;

                    runDelay = System.currentTimeMillis();
                }

                // Wait a bit before setting our desired angle
                if (System.currentTimeMillis() - runDelay > 250 && !straightModeRun) {
                    straightAngle = drivetrain.navx.getAngle();
                    drivetrain.driveAngleInit(straightAngle);
                    straightModeRun = true;
                }

                if (straightModeRun) {
                    drivetrain.driveAngle(speedInput, straightAngle);
                } else {
                    drivetrain.drive(speedInput, rotationInput);
                }
            }
        } else {
            straightModeStart = false;
            straightModeRun = false;
            drivetrain.drive(speedInput, rotationInput);
        }

        SmartDashboard.putBoolean("Driving straight", straightModeStart);
        SmartDashboard.putNumber("Straight angle", straightAngle);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
