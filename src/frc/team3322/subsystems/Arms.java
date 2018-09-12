package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.PIDController;
import frc.team3322.RobotMap;
import frc.team3322.commands.ArmsIdle;

public class Arms extends Subsystem {

    private static final double ARMS_KP = 0;
    private static final double ARMS_DECAY = 0;
    private static final double ARMS_KI = 0;
    private static final double ARMS_KD = 0;
    private static final double MAX_SPEED = 1;

    public static final double POS_PREPARE_PICKUP = 40; // @TODO find actual values
    public static final double POS_RETRACTED = 100; // @TODO find actual values
    public static final double POS_CLOSED = -10; // TODO

    private WPI_TalonSRX arms = new WPI_TalonSRX(RobotMap.CAN.ARMS);

    PowerDistributionPanel pdp = new PowerDistributionPanel();

    // TODO: make arms counter
    private DigitalInput hallEffectParallel = new DigitalInput(RobotMap.DIO.HALL_EFFECT_PARALLEL);
    private DigitalInput hallEffectPerpendicular = new DigitalInput(RobotMap.DIO.HALL_EFFECT_PERPENDICULAR);

    //private Encoder enc_left;
    //private Encoder enc_right;

    PIDController[] pid = new PIDController[1];

    public Arms() {
        //leftArm.setInverted(true);

        //enc_left = new Encoder(RobotMap.DIO.ARM_LEFT_ENCODER_A, RobotMap.DIO.ARM_LEFT_ENCODER_B);
        //enc_right = new Encoder(RobotMap.DIO.ARM_RIGHT_ENCODER_A, RobotMap.DIO.ARM_RIGHT_ENCODER_B);

        pid[0] = new PIDController("Arms", ARMS_KP, ARMS_DECAY, ARMS_KI, ARMS_KD);


        pid[0].initialize(getRotation(), getRotation());
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ArmsIdle());
    }

    public void goToRotationInit(double rotation) {
        pid[0].initialize(rotation, getRotation());
    }

    public void goToRotation() {
        double out = pid[0].output(getRotation());
        if (Math.abs(out) > MAX_SPEED) {
            out = out / Math.abs(out) * MAX_SPEED;
        }

        set(out);

    }

    public void liftArms() {


        arms.set(MAX_SPEED);
    }

    public void lowerArms() {

        arms.set(-MAX_SPEED);
    }

    public void set(double speed) {
        arms.set(speed);
    }

    public void stop() {
        arms.set(0);
    }

    public double getArmCurrent() {
    return pdp.getCurrent(RobotMap.CAN.ARMS);
    }


    //TODO: Change to counter
    private double getRotation() {
        //return toDegrees(-enc_left.getDistance());
    return 1;
    }


    public boolean haveReachedPerpendicular() {
        return false;/*hallEffectPerpendicular.get();*/
    }

    public boolean haveReachedParallel() {
        return false;/*hallEffectParallel.get();*/
    }

    public double toDegrees(double input) {
        return input / 1024 * 365;
    }

    //TODO: Change to counter
    public void resetPosition() {
        //enc_left.reset();
        //enc_right.reset();
    }
}