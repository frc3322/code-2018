package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.subsystems.Arms;

import static frc.team3322.Robot.arms;

public class ArmsToRetracted extends Command {
    public ArmsToRetracted() {
    }

    @Override
    protected void initialize() {
        arms.goToRotationInit(Arms.POS_RETRACTED);
    }

    @Override
    protected void execute() {
        arms.goToRotation();
    }

    @Override
    protected boolean isFinished() {
        return arms.haveReached(Arms.POS_RETRACTED);
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
