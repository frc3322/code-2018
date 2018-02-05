package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Elevator;

import static frc.team3322.Robot.elevator;

public class ElevatorToSwitch extends Command {
    public ElevatorToSwitch() {
        requires(elevator);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        elevator.goToPos(Elevator.SWITCH);
    }

    @Override
    protected boolean isFinished() {
        return elevator.isAtSwitch();
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
