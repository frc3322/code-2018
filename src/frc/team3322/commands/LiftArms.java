package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;


public class LiftArms extends Command {
    public LiftArms() {
        requires(arms);
    }

    @Override
    protected void execute() {
        arms.liftArms();
    }

    @Override
    protected boolean isFinished() {
        return arms.haveReachedPerpendicular();
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
