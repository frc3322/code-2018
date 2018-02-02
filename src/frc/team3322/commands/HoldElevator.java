package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.elevator;


public class HoldElevator extends Command {
    private double holdHeight;
    private double error;
    private double kp = 0.05;

    public HoldElevator() {
        // Use requires() here to declare subsystem dependencies
        requires(elevator);
    }

    @Override
    protected void initialize() {
        holdHeight = elevator.getHeight();
    }

    @Override
    protected void execute() {
        error = holdHeight - elevator.getHeight();
        //elevator.move(error * kp);
    }

    @Override
    protected boolean isFinished() {
        return false;
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
