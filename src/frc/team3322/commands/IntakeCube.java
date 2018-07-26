package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.intakes;


public class IntakeCube extends Command {
    public IntakeCube() {
        requires(intakes);
    }

    @Override
    protected void execute() {
        intakes.spinInwards();
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
