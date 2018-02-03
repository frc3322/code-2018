package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;

import static frc.team3322.Robot.cubeIntake;


public class EjectCube extends TimedCommand {

    public EjectCube(double timeout) {
        super(timeout);
        requires(cubeIntake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        cubeIntake.ejectCube();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        cubeIntake.stopIntake();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
