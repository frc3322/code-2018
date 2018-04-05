package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.RobotMap;

import static frc.team3322.Robot.elevator;
import static frc.team3322.Robot.oi;


public class ElevatorControl extends Command {

    private final int UP_AXIS;
    private final int DOWN_AXIS;

    private boolean holding = false;

    public ElevatorControl() {
        // Use requires() here to declare subsystem dependencies
        requires(elevator);

        this.UP_AXIS = RobotMap.XBOX.TRIGGER_L_AXIS;
        this.DOWN_AXIS = RobotMap.XBOX.TRIGGER_R_AXIS;
    }

    @Override
    protected void execute() {
        double moveInput = oi.stick.getRawAxis(UP_AXIS) - oi.stick.getRawAxis(DOWN_AXIS) * elevator.downSpeedModifier;

        if (moveInput < .1) {
            if (!holding) {
                holding = true;
                elevator.goToPosInit(elevator.getHeight());
            }

            elevator.goToPos();
        } else {
            holding = false;

            elevator.move(moveInput);
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
