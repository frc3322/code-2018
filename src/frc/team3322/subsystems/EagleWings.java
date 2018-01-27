package frc.team3322.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;

public class EagleWings extends Subsystem {

    Solenoid leftWing;
    Solenoid rightWing;

    public EagleWings() {
        leftWing = new Solenoid(RobotMap.PCM.LEFT_WING);
        rightWing = new Solenoid(RobotMap.PCM.RIGHT_WING);
    }

    public void initDefaultCommand() {}

    public void deploy() {
        leftWing.set(true);
        rightWing.set(true);
    }

    public void stop() {
        leftWing.set(false);
        rightWing.set(false);
    }
}

