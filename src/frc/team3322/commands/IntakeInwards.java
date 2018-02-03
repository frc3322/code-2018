package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class IntakeInwards extends Command {
    public IntakeInwards() {
        // Use requires() here to declare subsystem dependencies
        requires(arms);
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
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
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
