package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.Robot;
import frc.team3322.RobotMap;
import frc.team3322.commands.ElevatorControl;

public class Elevator extends Subsystem {

    public static final double TOP = 1000;
    public static final double SCALE = 300;
    public static final double SWITCH = 100;
    public static final double BOTTOM = 0;

    private static final double ELEVATOR_KP = 0.2;
    private static final double ELEVATOR_KD = 0.15;

    private double upSpeed = .5;
    private double downSpeed = .45;

    private SpeedControllerGroup elevator;
    private DoubleSolenoid shifter;

    private Encoder encoder;
    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;

    private double lastHeightError;

    public Elevator() {
        WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_1);
        WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_2);
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);

        shifter = new DoubleSolenoid(RobotMap.PCM.ELEVATOR_SHIFTER_FORWARD, RobotMap.PCM.ELEVATOR_SHIFTER_REVERSE);

        if (RobotMap.PCM.ELEVATOR_SHIFTER_FORWARD != -1 && RobotMap.PCM.ELEVATOR_SHIFTER_REVERSE != -1)
            encoder = new Encoder(RobotMap.DIO.ELEVATOR_ENCODER_A, RobotMap.DIO.ELEVATOR_ENCODER_B);
        if (RobotMap.DIO.ELEVATOR_LIMIT_TOP != -1)
            topLimitSwitch = new DigitalInput(RobotMap.DIO.ELEVATOR_LIMIT_TOP);
        if (RobotMap.DIO.ELEVATOR_LIMIT_BOTTOM != -1)
            bottomLimitSwitch = new DigitalInput(RobotMap.DIO.ELEVATOR_LIMIT_BOTTOM);
    }

    public Elevator(double upSpeed, double downSpeed) {
        this();
        this.upSpeed = upSpeed;
        this.downSpeed = downSpeed;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorControl());
    }

    public void moveUp() {
        elevator.set(upSpeed);
    }

    public void moveDown() {
        elevator.set(-downSpeed);
    }

    public void move(double speed) {
        elevator.set(speed);
    }

    public void stop() {
        elevator.set(0);
    }

    public void goToPos(double height) {
        double error = height - getHeight();
        double kp = SmartDashboard.getNumber("Elevator kp", ELEVATOR_KP);
        double kd = SmartDashboard.getNumber("Elevator kd", ELEVATOR_KD);

        double speed = kp * error + kd * (lastHeightError - error);

        move(speed);
    }

    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public void resetEncoder() {
        encoder.reset();
    }


    // TODO implement the following checks

    public boolean isAtTop() {
        return topLimitSwitch != null && topLimitSwitch.get();
    }

    public boolean isAtBottom() {
        if (bottomLimitSwitch != null) {
            resetEncoder();
            return bottomLimitSwitch.get();
        }
        return false;
    }

    public double getHeight() {
        if (encoder != null) {
            return encoder.getDistance();
        }
        return -1;
    }

    // TODO incorporate hysteresis
    public boolean isAtSwitch() {
        return false;
    }

    public boolean isAtScale() {
        return false;
    }
}
