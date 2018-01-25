package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;

public class CubeIntake extends Subsystem {

    private static final double DEFAULT_ARM_SPEED = .5;
    private static final double DEFAULT_INTAKE_SPEED = .75;

    public SpeedControllerGroup intake;
    public SpeedControllerGroup arms;

    public CubeIntake() {
        WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.LEFT_INTAKE);
        leftIntake.setInverted(true);
        WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.RIGHT_INTAKE);
        intake = new SpeedControllerGroup(leftIntake,rightIntake);

        WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.LEFT_ARM);
        leftArm.setInverted(true);
        WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.RIGHT_ARM);
        arms = new SpeedControllerGroup(leftArm,rightArm);
    }

    public void initDefaultCommand() {}

    public void openArm() {
        arms.set(DEFAULT_ARM_SPEED);
    }

    public void closeArm() {
        arms.set(-DEFAULT_ARM_SPEED);
    }

    public void stopArm() {
        arms.stopMotor();
    }

    public void receiveCube() {
        intake.set(DEFAULT_INTAKE_SPEED);
    }

    public void ejectCube() {
        intake.set(-DEFAULT_INTAKE_SPEED);
    }
}