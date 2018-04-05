package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;

public class ArmsToPreparePickup extends Command {
    public ArmsToPreparePickup() {
        requires(arms);
    }

    @Override
    protected void initialize() {
        arms.goToRotationInit(Arms.POS_PREPARE_PICKUP);
    }

    @Override
    protected void execute() {
        arms.goToRotation();
    }

    @Override
    protected boolean isFinished() {
        return arms.haveReached(Arms.POS_PREPARE_PICKUP);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
