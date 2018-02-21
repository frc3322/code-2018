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
    private double downSpeed = .4;

    private SpeedControllerGroup elevator;

    private Encoder encoder;
    private DigitalInput bottomLimitSwitch;

    private double lastHeightError = 0;

    public Elevator() {
        WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_1);
        WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_2);
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);

        encoder = new Encoder(RobotMap.DIO.ELEVATOR_ENCODER_A, RobotMap.DIO.ELEVATOR_ENCODER_B);
        bottomLimitSwitch = null;
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
        move(upSpeed);
    }

    public void moveDown() {
        move(-downSpeed);
    }

    public void move(double speed) {
        if (isAtTop() || isAtBottom()) {
            elevator.set(0);
        } else {
            elevator.set(speed);
        }
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

        lastHeightError = error;
    }

    public void resetEncoder() {
        encoder.reset();
    }

    public double toInchRatio(double input) {
        return input * 1;
    }

    // TODO implement the following checks
    public boolean isAtTop() {
        return false;
    }

    public boolean isAtBottom() {
        return false;
    }

    public double getHeight() {
        if (encoder != null) {
            return toInchRatio(encoder.getDistance());
        }
        return -1;
    }

    public boolean isAtSwitch() {
        return false;
    }

    public boolean isAtScale() {
        return false;
    }
}
