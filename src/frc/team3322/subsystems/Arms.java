package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;

public class Arms extends Subsystem {

    private double armSpeed = .5;
    private double intakeSpeed = .75;

    private SpeedControllerGroup arms;
    private SpeedControllerGroup intake;

    public Arms() {
        WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.CAN.LEFT_ARM);
        WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.CAN.RIGHT_ARM);
        leftArm.setInverted(true);
        arms = new SpeedControllerGroup(leftArm,rightArm);

        WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.CAN.LEFT_INTAKE);
        WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.CAN.RIGHT_INTAKE);
        rightIntake.setInverted(true);
        intake = new SpeedControllerGroup(leftIntake, rightIntake);
    }

    public Arms(double armSpeed, double intakeSpeed) {
        this();
        this.armSpeed = armSpeed;
        this.intakeSpeed = intakeSpeed;
    }

    public void initDefaultCommand() {}

    public void openArm() {
        arms.set(armSpeed);
    }

    public void closeArm() {
        arms.set(-armSpeed);
    }

    public void stopArm() {
        arms.set(0);
    }

    public void receiveCube() {
        intake.set(intakeSpeed);
    }

    public void ejectCube() {
        intake.set(-intakeSpeed);
    }

    public void stopIntake() {
        intake.set(0);
    }
}