package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;

public class Arms extends Subsystem {

    private double armSpeed = .35;
    private double intakeSpeed = .35;

    private WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.CAN.LEFT_ARM);
    private WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.CAN.RIGHT_ARM);

    private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.CAN.LEFT_INTAKE);
    private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.CAN.RIGHT_INTAKE);

    private SpeedControllerGroup arms;
    private SpeedControllerGroup intakes;

    public Arms() {
        leftArm.setInverted(true);
        rightIntake.setInverted(true);

        arms = new SpeedControllerGroup(leftArm, rightArm);
        intakes = new SpeedControllerGroup(leftIntake, rightIntake);
    }

    public Arms(double armSpeed, double intakeSpeed) {
        this();
        this.armSpeed = armSpeed;
        this.intakeSpeed = intakeSpeed;
    }

    public void initDefaultCommand() {}

    public void open() {
        if (!hasLeftReachedEnd()) {
            leftArm.set(armSpeed*.85);
        } else {
            leftArm.set(0);
        }
        if (!hasRightReachedEnd()) {
            rightArm.set(armSpeed);
        } else {
            rightArm.set(0);
        }
    }

    public void close() {
        if (!hasLeftReachedEnd()) {
            leftArm.set(-armSpeed*.75*.85);
        } else {
            leftArm.set(0);
        }
        if (!hasRightReachedEnd()) {
            rightArm.set(-armSpeed*.75);
        } else {
            rightArm.set(0);
        }
    }

    public void stop() {
        arms.set(0);
    }

    public void intakeIn() {
        intakes.set(-intakeSpeed);
    }

    public void intakeOut() {
        intakes.set(intakeSpeed);
    }

    public void stopIntake() {
        intakes.set(0);
    }

    public boolean hasLeftReachedEnd() {
        return leftArm.getOutputCurrent() < 20;
    }

    public boolean hasRightReachedEnd() {
        return rightArm.getOutputCurrent() < 20;
    }

    public boolean haveBothReachedEnd() {
        return hasLeftReachedEnd() && hasRightReachedEnd();
    }
}