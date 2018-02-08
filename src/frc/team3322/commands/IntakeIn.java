package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.arms;


public class IntakeIn extends Command {
    public IntakeIn() {
        // Use requires() here to declare subsystem dependencies
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
