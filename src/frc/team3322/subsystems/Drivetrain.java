package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;
import frc.team3322.commands.DriveControl;

public class Drivetrain extends Subsystem {

    private static final double DRIVEANGLE_KP = .4;
    private static final double DRIVEANGLE_KD = .3;

    private DifferentialDrive robotDrive;
    private DoubleSolenoid shifter;
    public AHRS navx;
    private Encoder enc_left;
    private Encoder enc_right;

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
        enc_left = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_LA, RobotMap.DIO.DRIVETRAIN_ENCODER_LB);
        enc_right = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_RA, RobotMap.DIO.DRIVETRAIN_ENCODER_RB);
        enc_right.setReverseDirection(true);

        SmartDashboard.putNumber("DriveAngle kp", DRIVEANGLE_KP);
        SmartDashboard.putNumber("DriveAngle kd", DRIVEANGLE_KD);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
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
                driveAngle(speed, straightAngle);
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

    private double toWheelRatio(double input) {
        // This ratio determines the wheel translation based on experimental data
        // TODO: update for P2
        return input / (6080 / 15) * 12;
    }

    public double getLeftDisplacement() {
        return toWheelRatio(enc_left.get());
    }

    public double getRightDisplacement() {
        return toWheelRatio(enc_right.get());
    }

    public double getLeftTicks() {
        return enc_left.get();
    }

    public double getRightTicks() {
        return enc_right.get();
    }

    public double getRobotDisplacement() {
        return (toWheelRatio(enc_left.get()) + toWheelRatio(enc_left.get())) / 2;
    }

    public double getLeftVelocity() {
        return toWheelRatio(enc_left.getRate());
    }

    public double getRightVelocity() {
        return toWheelRatio(enc_right.getRate());
    }

    public double getRobotVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }

    public void resetPositioning() {
        enc_left.reset();
        enc_right.reset();
        navx.resetDisplacement();
    }
}
