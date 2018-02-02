package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.RobotMap;

import static frc.team3322.Robot.elevator;
import static frc.team3322.Robot.oi;


public class ElevatorControl extends Command {

    private final int UP_AXIS;
    private final int DOWN_AXIS;

    public ElevatorControl() {
        // Use requires() here to declare subsystem dependencies
        requires(elevator);

        this.UP_AXIS = RobotMap.XBOX.TRIGGER_L_AXIS;
        this.DOWN_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (Math.abs(UP_AXIS - DOWN_AXIS) < .01) {
            elevator.stop();
        } else {
            elevator.move(oi.stick.getRawAxis(UP_AXIS) - oi.stick.getRawAxis(DOWN_AXIS));
        }
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
