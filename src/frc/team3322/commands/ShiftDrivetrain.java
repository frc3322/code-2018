package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

import static frc.team3322.Robot.drivetrain;


public class ShiftDrivetrain extends InstantCommand {
    @Override
    protected void execute() {
        drivetrain.toggleShifter();
    }
}
