package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;
import frc.team3322.commands.DriveControl;

public class Drivetrain extends Subsystem {

    private static final double DRIVEANGLE_KP = .4;
    private static final double DRIVEANGLE_KD = .3;
    private static final double WHEEL_DIAMETER = 6;
    private static final double TICS_PER_REVOLUTION = 256;
    private static final double GEAR_RATIO = 0.5;

    private DifferentialDrive robotDrive;
    private DoubleSolenoid shifter;
    public AHRS navx;
    private Encoder leftEnc;
    private Encoder rightEnc;

    private double lastAngleError = 0;
    private long lastShift;
    private int shiftCooldown = 1000;
    private int shiftLowThreshold = 1;
    private int shiftHighThreshold = 2;


    public Drivetrain() {
        WPI_TalonSRX leftBackMotor = new WPI_TalonSRX(RobotMap.CAN.LEFT_BACK_MOTOR);
        WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(RobotMap.CAN.LEFT_FRONT_MOTOR);
        WPI_TalonSRX rightBackMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_BACK_MOTOR);
        WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_FRONT_MOTOR);

        SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftBackMotor, leftFrontMotor);
        SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightBackMotor, rightFrontMotor);
        leftGroup.setInverted(true);
        rightGroup.setInverted(true);

        robotDrive = new DifferentialDrive(leftGroup, rightGroup);

        shifter = new DoubleSolenoid(RobotMap.PCM.DRIVETRAIN_SHIFTER_FORWARD, RobotMap.PCM.DRIVETRAIN_SHIFTER_REVERSE);
        navx = new AHRS(SerialPort.Port.kMXP);

        leftEnc = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_LA, RobotMap.DIO.DRIVETRAIN_ENCODER_LB);
        rightEnc = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_RA, RobotMap.DIO.DRIVETRAIN_ENCODER_RB);
        rightEnc.setReverseDirection(true);

        SmartDashboard.putNumber("DriveAngle kp", DRIVEANGLE_KP);
        SmartDashboard.putNumber("DriveAngle kd", DRIVEANGLE_KD);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);

        SmartDashboard.putNumber("Velocity", Math.sqrt(Math.pow(navx.getVelocityX(), 2) + Math.pow(navx.getVelocityY(), 2)));

        SmartDashboard.putNumber("Displacement X", navx.getDisplacementX());
        SmartDashboard.putNumber("Displacement Y", navx.getDisplacementY());

        SmartDashboard.putNumber("Left displacement", getLeftDisplacement());
        SmartDashboard.putNumber("Right displacement", getRightDisplacement());

    }

    public void driveAngleInit(double angle) {
        lastAngleError = angle - navx.getAngle();
    }

    public void driveAngle(double speed, double angle) {
        double error = angle - navx.getAngle(); //getAngle() returns overall angle, not necessarily from -180 to 180
        double kp = SmartDashboard.getNumber("DriveAngle kp", DRIVEANGLE_KP);
        double kd = SmartDashboard.getNumber("DriveAngle kd", DRIVEANGLE_KD);

        double turn = kp * error + kd * (lastAngleError - error);

        drive(speed, turn);

        SmartDashboard.putNumber("DriveAngle error", error);
    }

    public void stop() {
        robotDrive.arcadeDrive(0, 0);
    }

    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    public boolean isHigh() {
        return shifter.get() == DoubleSolenoid.Value.kForward;
    }

    public void toggleShifter() {
        if (isHigh()) {
            shiftLow();
        } else {
            shiftHigh();
        }
    }

    public void autoShift() {
        if (System.currentTimeMillis() - lastShift < shiftCooldown) {
            if (Math.abs(getRobotVelocity()) > shiftLowThreshold) {
                if (!isHigh()) {
                    shiftHigh();
                    lastShift = System.currentTimeMillis();
                }
            } else if (Math.abs(getRobotVelocity()) < shiftHighThreshold) {
                if (isHigh()) {
                    shiftLow();
                    lastShift = System.currentTimeMillis();
                }
            }
        }
    }

    public double toWheelRatio(double input) {
        return input / TICS_PER_REVOLUTION * Math.PI * WHEEL_DIAMETER * GEAR_RATIO;
    }

    public double getLeftDisplacement() {
        return toWheelRatio(leftEnc.get());
    }

    public double getRightDisplacement() {
        return toWheelRatio(rightEnc.get());
    }

    public double getRobotDisplacement() {
        return (toWheelRatio(leftEnc.get()) + toWheelRatio(rightEnc.get())) / 2;
    }

    public double getLeftVelocity() {
        return toWheelRatio(leftEnc.getRate());
    }

    public double getRightVelocity() {
        return toWheelRatio(rightEnc.getRate());
    }

    public double getRobotVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }

    public void resetEncoders() {
        leftEnc.reset();
        rightEnc.reset();
    }
}
