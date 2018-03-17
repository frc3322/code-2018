package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Elevator;

import static frc.team3322.Robot.elevator;

public class ElevatorToSwitch extends Command {
    public ElevatorToSwitch() {
        requires(elevator);
        setTimeout(3);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        //elevator.goToPos(Elevator.SWITCH);
        elevator.move(0.4);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || elevator.isAtSwitch();
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
