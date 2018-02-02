package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;
import frc.team3322.commands.ElevatorControl;

public class Elevator extends Subsystem {

    public static final double TOP = 1000;
    public static final double SCALE = 300;
    public static final double SWITCH = 100;
    public static final double BOTTOM = 0;

    private SpeedControllerGroup elevator;
    private DoubleSolenoid shifter;

    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;
    private Encoder encoder;

    public Elevator() {
        WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_1);
        WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_2);
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);

        shifter = new DoubleSolenoid(RobotMap.PCM.ELEVATOR_SHIFTER_FORWARD, RobotMap.PCM.ELEVATOR_SHIFTER_REVERSE);

        topLimitSwitch = new DigitalInput(RobotMap.DI.TOP_LIMIT_SWITCH);
        bottomLimitSwitch = new DigitalInput(RobotMap.DI.BOTTOM_LIMIT_SWITCH);
        encoder = new Encoder(RobotMap.DI.ELEVATOR_ENCODER_A, RobotMap.DI.ELEVATOR_ENCODER_B);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorControl());
    }

    public void moveUp() {
        elevator.set(.5);
    }

    public void moveDown() {
        elevator.set(-.5);
    }

    public void move(double speed) {
        elevator.set(speed);
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
        return false; // topLimitSwitch.get();
    }

    public boolean isAtBottom() {
//        if(bottomLimitSwitch.get()) {
//            resetEncoder();
//            return true;
//        }
        return false;
    }

    public double getHeight() {
        //return encoder.getDistance();
        return -1;
    }

    public void resetEncoder() {
        encoder.reset();
    }
}
