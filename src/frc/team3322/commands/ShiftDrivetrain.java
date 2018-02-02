package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;


public class ShiftDrivetrain extends Command {
    public ShiftDrivetrain() {
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        drivetrain.toggleShifter();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
