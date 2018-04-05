package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

import static frc.team3322.Robot.elevator;


public class ElevatorToggleClimbMode extends InstantCommand {

    @Override
    protected void execute() {
        elevator.toggleClimbMode();
    }
}
