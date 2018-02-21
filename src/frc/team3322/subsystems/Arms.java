package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.RobotMap;
import frc.team3322.commands.IntakeIdle;

public class Arms extends Subsystem {

    private double armSpeed = .35;

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

        arms = new SpeedControllerGroup(leftArm, rightArm);
    }

    public Arms(double armSpeed, double intakeSpeed) {
        this();
        this.armSpeed = armSpeed;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new IntakeIdle());
    }

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
        updateDashboard();
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