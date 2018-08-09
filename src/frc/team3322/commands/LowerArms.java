package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;


public class LowerArms extends Command {
    public LowerArms() {
        requires(arms);
    }

    @Override
    protected void execute() {
        arms.lowerArms();
    }

    @Override
    protected boolean isFinished() {
        return arms.haveReachedParallel();
    }

    @Override
    protected void end() {
        arms.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
