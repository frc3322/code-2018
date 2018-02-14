package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;

import static frc.team3322.Robot.drivetrain;
import static frc.team3322.Robot.oi;

public class DriveControl extends Command {

    private final int SPEED_AXIS;
    private final int ROTATION_AXIS;

    private boolean straightModeStart;
    private boolean straightModeRun;
    private long runDelay;
    private double straightAngle;

    public DriveControl() {
        requires(drivetrain);

        SPEED_AXIS = RobotMap.XBOX.STICK_L_Y_AXIS;
        ROTATION_AXIS = RobotMap.XBOX.STICK_R_X_AXIS;
    }

    @Override
    protected void initialize() {
        straightModeStart = false;
    }

    @Override
    protected void execute() {
        double speedInput = -oi.stick.getRawAxis(SPEED_AXIS);
        double rotationInput = oi.stick.getRawAxis(ROTATION_AXIS);

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

        // TODO: allow toggling of autoshift
        drivetrain.autoShift();

        SmartDashboard.putBoolean("Driving straight", straightModeStart);
        SmartDashboard.putNumber("Straight angle", straightAngle);
    }

    @Override
    protected boolean isFinished() {
        return false;
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
