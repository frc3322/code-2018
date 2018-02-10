package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class IntakeOut extends Command {
    public IntakeOut() {
        //requires(arms);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        arms.intakeOut();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        arms.stopIntake();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
