package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class AutoShiftDrivetrain extends Command {


    @Override
    protected void execute() {
        drivetrain.autoShift();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
