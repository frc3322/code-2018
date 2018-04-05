package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;
import static frc.team3322.Robot.elevator;

public class ArmsToRetracted extends Command {
    public ArmsToRetracted() {
    }

    @Override
    protected void initialize() {
        arms.goToRotationInit(Arms.ARMS_RETRACT);
    }

    @Override
    protected void execute() {
        arms.goToRotation();
    }

    @Override
    protected boolean isFinished() {
        return arms.hasReached(arms.ARMS_RETRACT);
    }

    @Override
    protected void end() {
        arms.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
