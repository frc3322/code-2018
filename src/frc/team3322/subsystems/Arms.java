package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.PIDController;
import frc.team3322.RobotMap;
import frc.team3322.commands.ArmsIdle;

public class Arms extends Subsystem {

    private static final double ARMS_KP = .3;
    private static final double ARMS_DECAY = .2;
    private static final double ARMS_KI = .2;
    private static final double ARMS_KD = .3;
    private static final double MAX_SPEED = .6;

    public static final double POS_PREPARE_PICKUP = 40; // @TODO find actual values
    public static final double POS_RETRACTED = 100; // @TODO find actual values
    public static final double POS_CLOSED = -10; // TODO

    private WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.CAN.ARM_LEFT);
    private WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.CAN.ARM_RIGHT);

    private SpeedControllerGroup arms = new SpeedControllerGroup(leftArm, rightArm);

    private Encoder enc_left;
    private Encoder enc_right;

    PIDController[] pid = new PIDController[2];

    public Arms() {
        leftArm.setInverted(true);

        enc_left = new Encoder(RobotMap.DIO.ARM_LEFT_ENCODER_A, RobotMap.DIO.ARM_LEFT_ENCODER_B);
        enc_right = new Encoder(RobotMap.DIO.ARM_RIGHT_ENCODER_A, RobotMap.DIO.ARM_RIGHT_ENCODER_B);

        pid[0] = new PIDController("Arms left", ARMS_KP, ARMS_DECAY, ARMS_KI, ARMS_KD);
        pid[1] = new PIDController("Arms right", ARMS_KP, ARMS_DECAY, ARMS_KI, ARMS_KD);

        for (int i = 0; i <= 1; ++i) {
            pid[i].initialize(getRotation(i), getRotation(i));
        }
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ArmsIdle());
    }

    public void goToRotationInit(double rotation) {
        pid[0].initialize(rotation, getRotation(0));
        pid[1].initialize(rotation, getRotation(1));
    }

    public void goToRotation() {
        for (int i = 0; i <= 1; ++i) {
            double out = pid[i].output(getRotation(i));
            if (Math.abs(out) > MAX_SPEED) {
                out = out / Math.abs(out) * MAX_SPEED;
            }

            set(i, out);
        }
    }

    public void open() {
        if (haveReached(POS_RETRACTED)) {
            arms.set(0);
            return;
        }

        arms.set(MAX_SPEED);
    }

    public void close() {
        if (haveReached(POS_CLOSED)) {
            arms.set(0);
            return;
        }

        arms.set(-MAX_SPEED);
    }

    public void set(int side, double speed) {
        if (side == 0) {
            leftArm.set(speed);
        } else {
            rightArm.set(speed);
        }
    }

    public void stop() {
        leftArm.set(0);
        rightArm.set(0);
    }

    private double getCombinedRotation() {
        return (getLeftRotation() + getRightRotation()) / 2;
    }

    private double getLeftRotation() {
        return toDegrees(-enc_left.getDistance());
    }

    private double getRightRotation() {
        return toDegrees(enc_right.getDistance());
    }

    private double getRotation(int side) {
        if (side == 0) {
            return getLeftRotation();
        } else {
            return getRightRotation();
        }
    }

    public boolean haveReached(double position) {
        return (Math.abs(getLeftRotation() - position) < 10) && (Math.abs(getRightRotation() - position) < 10);
    }

    public double toDegrees(double input) {
        return input / 1024 * 365;
    }

    public void resetPosition() {
        enc_left.reset();
        enc_right.reset();
    }
}