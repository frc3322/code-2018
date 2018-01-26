package frc.team3322.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;

public class EagleWings extends Subsystem {

    Solenoid leftWing;
    Solenoid rightWing;

    public EagleWings() {
        leftWing = new Solenoid(RobotMap.LEFT_WING);
        rightWing = new Solenoid(RobotMap.RIGHT_WING);
    }

    public void deploy() {
        leftWing.set(true);
        rightWing.set(true);
    }

    public void stop() {
        leftWing.set(false);
        rightWing.set(false);
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

