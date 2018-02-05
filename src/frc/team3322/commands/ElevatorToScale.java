package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Elevator;

import static frc.team3322.Robot.elevator;

public class ElevatorToScale extends Command {
    public ElevatorToScale() {
        requires(elevator);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        elevator.goToPos(Elevator.SCALE);
    }

    @Override
    protected boolean isFinished() {
        return elevator.isAtScale();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
