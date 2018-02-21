package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class OpenArms extends Command {
    public OpenArms() {
        requires(arms);
    }

    @Override
    protected void execute() {
        arms.open();
    }

    @Override
    protected boolean isFinished() {
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
