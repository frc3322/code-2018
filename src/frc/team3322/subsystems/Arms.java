package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;

public class Arms extends Subsystem {

    private double leftSpeed = .4;
    private double rightSpeed = .4;

    private WPI_TalonSRX leftArm = new WPI_TalonSRX(RobotMap.CAN.LEFT_ARM);
    private WPI_TalonSRX rightArm = new WPI_TalonSRX(RobotMap.CAN.RIGHT_ARM);

    private SpeedControllerGroup arms;

    private enum State {
        OPENED,
        CLOSED,
        MOVING
    }

    private State currentState = State.MOVING;

    public Arms() {
        leftArm.setInverted(true);
        SmartDashboard.putNumber("Left arm speed", leftSpeed);
        SmartDashboard.putNumber("Right arm speed", rightSpeed);

        arms = new SpeedControllerGroup(leftArm, rightArm);
    }

    public Arms(double leftSpeed, double rightSpeed) {
        this();
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    public void initDefaultCommand() {
    }

    public void open() {
        if (currentState == State.OPENED) {
            arms.set(0);
            return;
        }

        if (!hasLeftReachedEnd()) {
            leftArm.set(leftSpeed);
        } else {
            leftArm.set(0);
        }
        if (!hasRightReachedEnd()) {
            rightArm.set(rightSpeed);
        } else {
            rightArm.set(0);
        }

        if (haveBothReachedEnd()) {
            currentState = State.OPENED;
        } else {
            currentState = State.MOVING;
        }
        updateDashboard();
    }

    public void close() {
        if (currentState == State.CLOSED) {
            arms.set(0);
            return;
        }

        if (!hasLeftReachedEnd()) {
            leftArm.set(-leftSpeed);
        } else {
            leftArm.set(0);
        }
        if (!hasRightReachedEnd()) {
            rightArm.set(-rightSpeed);
        } else {
            rightArm.set(0);
        }

        if (haveBothReachedEnd()) {
            currentState = State.CLOSED;
        } else {
            currentState = State.MOVING;
        }
        updateDashboard();
    }

    public void stop() {
        arms.set(0);
        updateDashboard();
    }

    private void updateDashboard() {
        SmartDashboard.putNumber("Left arm current", leftArm.getOutputCurrent());
        SmartDashboard.putNumber("Right arm current", rightArm.getOutputCurrent());
    }

    public boolean hasLeftReachedEnd() {
        return false;
        //if (leftArm.getOutputCurrent() == 0) return false;
        //return leftArm.getOutputCurrent() > MAX_CURRENT;
    }

    public boolean hasRightReachedEnd() {
        return false;
        //if (rightArm.getOutputCurrent() == 0) return false;
        //return rightArm.getOutputCurrent() > MAX_CURRENT;
    }

    public boolean haveBothReachedEnd() {
        return hasLeftReachedEnd() && hasRightReachedEnd();
    }
}