package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

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
        // TODO: verify motor current
        return arms.haveBothReachedEnd();
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
