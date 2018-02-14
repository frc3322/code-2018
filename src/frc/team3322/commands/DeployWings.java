package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.wings;


public class DeployWings extends Command {
    public DeployWings() {
        requires(wings);
        setTimeout(2);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        wings.deploy();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        wings.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
