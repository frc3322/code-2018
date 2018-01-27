package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.elevator;


public class ElevatorToTop extends Command {
    public ElevatorToTop() {
        requires(elevator);
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        elevator.moveUp();
    }

    @Override
    protected boolean isFinished() {
        return elevator.isAtTop();
    }

    @Override
    protected void end() {
        elevator.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
