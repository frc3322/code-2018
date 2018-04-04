package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;

public class ArmsToPreparePickup extends Command {
    public ArmsToPreparePickup() {
    }

    @Override
    protected void initialize() {
        arms.goToRotationInit(Arms.ARMS_PREPARE_PICKUP);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
