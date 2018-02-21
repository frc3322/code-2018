package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.intakes;


public class IntakeOut extends Command {
    public IntakeOut() {
        requires(intakes);
    }

    @Override
    protected void execute() {
        intakes.spinOutwards();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        intakes.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
