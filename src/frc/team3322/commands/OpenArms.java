package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class OpenArms extends Command {
    public OpenArms() {
        // Use requires() here to declare subsystem dependencies
        requires(arms);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        arms.openArm();
    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    protected void end() {
        arms.stopArm();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
