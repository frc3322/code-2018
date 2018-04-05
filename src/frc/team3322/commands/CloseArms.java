package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;


public class CloseArms extends Command {
    public CloseArms() {
        requires(arms);
    }

    @Override
    protected void execute() {
        arms.close();
    }

    @Override
    protected boolean isFinished() {
        return arms.haveReached(Arms.POS_CLOSED);
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
