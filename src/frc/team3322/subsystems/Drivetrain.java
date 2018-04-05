package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.PIDController;
import frc.team3322.RobotMap;
import frc.team3322.commands.DriveControl;

public class Drivetrain extends Subsystem {

    private static final double DRIVEANGLE_KP = 1;
    private static final double DRIVEANGLE_KD = .3;
    private static final double WHEEL_SEPARATION = 22;

    private DifferentialDrive robotDrive;
    private DoubleSolenoid shifter;
    public AHRS navx;
    private Encoder enc_left;
    private Encoder enc_right;

    private PIDController pid;
    private static final double MAX_TURN = .2;

    private double lastAngleError = 0;
    private long lastShift;
    private int shiftCooldown = 1000;
    private int shiftLowThreshold = 1;
    private int shiftHighThreshold = 2;

    private boolean straightModeStart;
    private boolean straightModeRun;
    private long runDelay;
    private double straightAngle;


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
        navx = new AHRS(SPI.Port.kMXP);
        enc_left = new Encoder(RobotMap.DIO.DRIVETRAIN_LEFT_ENCODER_A, RobotMap.DIO.DRIVETRAIN_LEFT_ENCODER_B);
        enc_right = new Encoder(RobotMap.DIO.DRIVETRAIN_RIGHT_ENCODER_A, RobotMap.DIO.DRIVETRAIN_RIGHT_ENCODER_B);
        enc_right.setReverseDirection(true);

        pid = new PIDController("Drive angle", DRIVEANGLE_KP, 0, 0, DRIVEANGLE_KD);

        lastShift = System.currentTimeMillis() - shiftCooldown;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    public void driveAngleInit(double angle) {
        pid.initialize(angle, navx.getAngle());
    }

    public void driveAngle(double speed) {
        double turn = pid.output(navx.getAngle());
        if (Math.abs(turn) > MAX_TURN) {
            turn = turn / Math.abs(turn) * MAX_TURN;
        }
        drive(speed, turn);
    }

    public void driveArc(double radius, double speed) {
        // Ignore speed for now`
        double innerSpeed = (radius - WHEEL_SEPARATION / 2) / (radius + WHEEL_SEPARATION / 2) * Math.abs(speed);
        double outerSpeed = Math.abs(speed);

        if (speed > 0) {
            robotDrive.tankDrive(outerSpeed, innerSpeed);
        } else {
            robotDrive.tankDrive(innerSpeed, outerSpeed);
        }
    }

    public void driveStraightInit() {
        straightModeStart = false;
        straightModeRun = false;
    }

    public void driveStraight(double speed, double rotation) {
        if (Math.abs(speed) > .15 && Math.abs(rotation) < .15) {
            if (!straightModeStart) {
                straightModeStart = true;

                runDelay = System.currentTimeMillis();
            }

            // Wait a bit before setting our desired angle
            if (System.currentTimeMillis() - runDelay > 250 && !straightModeRun) {
                straightAngle = navx.getAngle();
                driveAngleInit(straightAngle);
                straightModeRun = true;
            }

            if (straightModeRun) {
                driveAngle(speed);
            } else {
                drive(speed, rotation);
            }
        } else {
            straightModeStart = false;
            straightModeRun = false;

            drive(speed, rotation);
        }

        SmartDashboard.putBoolean("Driving straight", straightModeRun);
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
            if (Math.abs(getRobotVelocity()) > shiftHighThreshold) {
                if (!isHigh()) {
                    shiftHigh();
                    lastShift = System.currentTimeMillis();
                }
            } else if (Math.abs(getRobotVelocity()) < shiftLowThreshold) {
                if (isHigh()) {
                    shiftLow();
                    lastShift = System.currentTimeMillis();
                }
            }
        }
    }

    private double toInchRatio(double input) {
        // This ratio determines the wheel translation based on experimental data
        double inchesTraveled = 15 * 12;
        int encoderTicks = 10759;
        // more tests: 10525 ticks per 15 ft
        return input * (inchesTraveled / encoderTicks);
    }

    public double getLeftTicks() {
        return enc_left.get();
    }

    public double getRightTicks() {
        return enc_right.get();
    }

    public double getLeftDisplacement() {
        return toInchRatio(enc_left.get());
    }

    public double getRightDisplacement() {
        return toInchRatio(enc_right.get());
    }

    public double getRobotDisplacement() {
        return Math.max(getLeftDisplacement(), getRightDisplacement());
    }

    public double getLeftVelocity() {
        return toInchRatio(enc_left.getRate());
    }

    public double getRightVelocity() {
        return toInchRatio(enc_right.getRate());
    }

    public double getRobotVelocity() {
        return Math.max(getLeftVelocity(), getRightVelocity());
    }

    public double getAcceleration() {
        return Math.sqrt(Math.pow(navx.getWorldLinearAccelX(), 2) + Math.pow(navx.getWorldLinearAccelY(), 2));
    }

    public void resetPositioning() {
        enc_left.reset();
        enc_right.reset();
        navx.resetDisplacement();
    }
}
