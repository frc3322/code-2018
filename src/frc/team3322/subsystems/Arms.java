package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.PIDController;
import frc.team3322.RobotMap;
import frc.team3322.commands.ArmsControl;

public class Arms extends Subsystem {

    private static final double ARMS_KP = .3;
    private static final double ARMS_DECAY = .2;
    private static final double ARMS_KI = .2;
    private static final double ARMS_KD = .3;
    private static final double ARMS_MAX_SPEED = .3;

    public static final double ARMS_PREPARE_PICKUP = 500; // @TODO find actual values
    public static final double ARMS_RETRACT = 1000; // @TODO find actual values

    private WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.CAN.ARM_LEFT);
    private WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.CAN.ARM_RIGHT);

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
        setDefaultCommand(new ArmsControl());
    }

    public void goToRotationInit(double rotation) {
        for (int i = 0; i <= 1; ++i) {
            pid[i].initialize(rotation, getRotation(i));
        }
    }

    public void goToRotation() {
        for (int i = 0; i <= 1; ++i) {
            double out = pid[i].output(getRotation(i));
            if (Math.abs(out) > ARMS_MAX_SPEED) {
                out = out / Math.abs(out) * ARMS_MAX_SPEED;
            }

            set(i, out);
        }
    }

    public void hasReachedState() {

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
        return toDegrees(enc_left.getDistance());
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

    public double toDegrees(double input) {
        return input / 256 * 365;
    }

    // TODO: remove
    @Deprecated
    public boolean hasLeftReachedEnd() {
        return enc_left.getDistance() > 10; // some value
    }

    @Deprecated
    public boolean hasRightReachedEnd() {
        return enc_right.getDistance() > 10; // some value
    }

    public boolean haveBothReachedEnd() {
        return hasLeftReachedEnd() && hasRightReachedEnd();
    }
}