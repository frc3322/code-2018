package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;

public class Arms extends Subsystem {

    private static final double MAX_CURRENT = 10; // in amps
    private double armSpeed = .35;
    private double intakeSpeed = .35;

    private WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.CAN.LEFT_ARM);
    private WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.CAN.RIGHT_ARM);

    private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.CAN.LEFT_INTAKE);
    private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.CAN.RIGHT_INTAKE);

    private SpeedControllerGroup arms;
    private SpeedControllerGroup intakes;

    private enum State {
        OPENED,
        CLOSED,
        MOVING
    }

    private State currentState = State.MOVING;

    public Arms() {
        leftArm.setInverted(true);
        leftIntake.setInverted(true);

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
        if (currentState == State.OPENED) {
            arms.set(0);
            return;
        }

        if (!hasLeftReachedEnd()) {
            leftArm.set(.7);
        } else {
            leftArm.set(0);
        }
        if (!hasRightReachedEnd()) {
            rightArm.set(armSpeed);
        } else {
            rightArm.set(0);
        }

        if (haveBothReachedEnd()) {
            currentState = State.OPENED;
        } else {
            currentState = State.MOVING;
        }
    }

    public void close() {
        if (currentState == State.CLOSED) {
            arms.set(0);
            return;
        }

        if (!hasLeftReachedEnd()) {
            leftArm.set(-armSpeed);
        } else {
            leftArm.set(0);
        }
        if (!hasRightReachedEnd()) {
            rightArm.set(-armSpeed);
        } else {
            rightArm.set(0);
        }

        if (haveBothReachedEnd()) {
            currentState = State.CLOSED;
        } else {
            currentState = State.MOVING;
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
        SmartDashboard.putNumber("Left arm current", leftArm.getOutputCurrent());
        //return false;
        //if (leftArm.getOutputCurrent() == 0) return false;
        return leftArm.getOutputCurrent() > MAX_CURRENT;
    }

    public boolean hasRightReachedEnd() {
        SmartDashboard.putNumber("Right arm current", rightArm.getOutputCurrent());
        //return false;
        //if (rightArm.getOutputCurrent() == 0) return false;
        return rightArm.getOutputCurrent() > MAX_CURRENT;
    }

    public boolean haveBothReachedEnd() {
        return hasLeftReachedEnd() && hasRightReachedEnd();
    }
}