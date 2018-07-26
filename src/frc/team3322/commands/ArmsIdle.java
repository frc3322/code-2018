package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class ArmsIdle extends Command {
    public ArmsIdle() {
        requires(arms);
    }

    @Override
    protected void execute() {
        arms.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
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
