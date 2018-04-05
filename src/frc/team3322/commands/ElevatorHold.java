package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.elevator;


public class ElevatorHold extends Command {
    public ElevatorHold() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.goToPosInit(elevator.getHeight());
    }

    @Override
    protected void execute() {
        elevator.goToPos();
    }

    @Override
    protected boolean isFinished() {
        return false;
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
