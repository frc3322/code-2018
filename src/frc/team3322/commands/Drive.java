package frc.team3322.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.RobotMap;

import static frc.team3322.Robot.drivetrain;
import static frc.team3322.Robot.oi;

public class Drive extends Command {
    double straightAngle;
    boolean drivingStraight;

    public Drive() {
        requires(drivetrain);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (Math.abs(oi.stick.getRawAxis(RobotMap.XBOX.STICK_R_X_AXIS)) < .01) {
            if (!drivingStraight) {
                drivingStraight = true;
                straightAngle = drivetrain.navx.getAngle();
            }
            drivetrain.driveAngle(oi.stick.getY(GenericHID.Hand.kLeft), straightAngle);
        } else {
            if (drivingStraight) {
                drivingStraight = false;
            }
            drivetrain.drive(oi.stick.getRawAxis(RobotMap.XBOX.STICK_L_Y_AXIS), -oi.stick.getRawAxis(RobotMap.XBOX.STICK_R_X_AXIS));
        }
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
