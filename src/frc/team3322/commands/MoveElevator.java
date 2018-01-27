package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.elevator;


public class MoveElevator extends Command {
    private final double speed;

    public MoveElevator(double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(elevator);

        this.speed = speed;
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        elevator.move(speed);
    }

    @Override
    protected boolean isFinished() {
        return elevator.isAtBottom() || elevator.isAtTop();
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
