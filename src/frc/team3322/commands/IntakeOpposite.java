package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.intakes;


public class IntakeOpposite extends Command {
    public IntakeOpposite() {
        requires(intakes);
    }

    @Override
    protected void execute() {
        intakes.spinOpposite();
    }

    @Override
    protected boolean isFinished() {
        return false;
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
