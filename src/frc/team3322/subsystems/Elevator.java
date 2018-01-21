package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;

public class Elevator extends Subsystem {

    private SpeedControllerGroup elevator;
    private DoubleSolenoid shifter;

    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;

    private double speed = 1;

    public Elevator() {
        WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_1);
        WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_2);

        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);

        shifter = new DoubleSolenoid(RobotMap.ELEVATOR_SHIFTER_FORWARD, RobotMap.ELEVATOR_SHIFTER_REVERSE);

        topLimitSwitch = new DigitalInput(RobotMap.TOP_LIMIT_SWITCH);
        bottomLimitSwitch = new DigitalInput(RobotMap.BOTTOM_LIMIT_SWITCH);
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void moveUp() {
        elevator.set(1);
    }

    public void moveDown() {
        elevator.set(-1);
    }

    public void stop() {
        elevator.set(0);
    }

    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isAtTop() {
        return topLimitSwitch.get();
    }

    public boolean isAtBottom() {
        return bottomLimitSwitch.get();
    }
}

