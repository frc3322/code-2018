package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3322.Robot;
import frc.team3322.subsystems.Limelight;
import frc.team3322.RobotMap;
import static frc.team3322.Robot.limelight;

public class AlignRobot extends Command{
    double speed_command = Robot.oi.chassisStick.getRawAxis(1);
    double angle_command = Robot.oi.chassisStick.getRawAxis(4);

    double horizontal = Limelight.getTx();

    public AlignRobot() {
        requires(limelight);
    }

    @Override
    protected void execute() {
        Robot.drivetrain.drive(speed_command * .7,horizontal * .1);
    }

    @Override
    protected boolean isFinished () {
        return true;
    }
}
