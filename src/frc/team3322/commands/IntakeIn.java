package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class IntakeIn extends Command {
    public IntakeIn() {
        requires(arms);
        setTimeout(1);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        arms.receiveCube();
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
