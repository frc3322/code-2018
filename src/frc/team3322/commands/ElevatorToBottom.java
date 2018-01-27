package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.elevator;


public class ElevatorToBottom extends Command {
    public ElevatorToBottom() {
        requires(elevator);
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        elevator.moveDown();
    }

    @Override
    protected boolean isFinished() {
        return elevator.isAtBottom();
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
