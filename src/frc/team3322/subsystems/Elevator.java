package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.PIDController;
import frc.team3322.RobotMap;
import frc.team3322.commands.ElevatorControl;

public class Elevator extends Subsystem {

    public static final double TOP = 56;
    public static final double SCALE = 55;
    public static final double SWITCH = 18;
    public static final double BOTTOM = 1;

    // TODO implement climbing mode
    private static final double MAX_POWER = .5;
    private static final double ELEVATOR_KP = 0.2;
    private static final double ELEVATOR_KI = .3;
    private static final double ELEVATOR_KD = 0.15;
    private static final double ELEVATOR_DECAY = .3;

    private static final double DOWN_SPEED_MODIFIER = .75;

    private double upSpeed = .5;
    private double downSpeed = .3;

    public double downSpeedModifier = .75;

    private SpeedControllerGroup elevator;

    private PIDController pid;

    private Encoder encoder;
    private DigitalInput bottomLimitSwitch;

    private double lastHeightError = 0;

    public Elevator() {
        WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_1);
        WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR_2);
        elevator = new SpeedControllerGroup(elevatorMotor1, elevatorMotor2);
        elevator.setInverted(true);

        encoder = new Encoder(RobotMap.DIO.ELEVATOR_ENCODER_A, RobotMap.DIO.ELEVATOR_ENCODER_B);

        pid = new PIDController("Elevator", ELEVATOR_KP, ELEVATOR_DECAY, ELEVATOR_KI, ELEVATOR_KD);
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
        if ((speed > 0 && isAtTop()) || (speed < 0 && isAtBottom())) {
            elevator.set(0);
        } else {
            elevator.set(speed);
        }
    }

    public void stop() {
        elevator.set(0);
    }

    public void toggleClimbMode() {
        if (downSpeedModifier != DOWN_SPEED_MODIFIER) {
            downSpeedModifier = 1;
        } else {
            downSpeedModifier = DOWN_SPEED_MODIFIER;
        }
    }

    public void goToPosInit(double height) {
        pid.initialize(height, getHeight());
    }

    public void goToPos() {
        double out = pid.output(getHeight());
        if (Math.abs(out) > MAX_POWER) {
            out = out / Math.abs(out) * MAX_POWER;
        }
        move(out);
    }

    public void resetEncoder() {
        encoder.reset();
    }

    private double toInchRatio(double input) {
        // This ratio determines the lift translation based on experimental data
        double inchesTraveled = 58.3;
        double encoderTicks = 8371;
        return input * (inchesTraveled / encoderTicks);
    }

    public double getHeight() {
        if (encoder != null) {
            return toInchRatio(encoder.getDistance());
        }
        return -1;
    }

    public boolean isAtSwitch() {
        return Math.abs(SWITCH - getHeight()) < 2;
    }

    public boolean isAtScale() {
        return Math.abs(SCALE - getHeight()) < 2;
    }

    public boolean isAtTop() {
        return getHeight() >= TOP;
    }

    public boolean isAtBottom() {
        return getHeight() <= BOTTOM;
    }
}
