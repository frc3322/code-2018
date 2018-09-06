package frc.team3322.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;
import frc.team3322.commands.IntakeIdle;

public class Intakes extends Subsystem {

    private double intakeSpeed = .5;

    private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.CAN.INTAKE_LEFT);
    private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.CAN.INTAKE_RIGHT);

    private SpeedControllerGroup intakes;

    PowerDistributionPanel pdp = new PowerDistributionPanel();

    public Intakes() {
        leftIntake.setInverted(true);

        intakes = new SpeedControllerGroup(leftIntake, rightIntake);
    }

    public Intakes(double intakeSpeed) {
        this();
        this.intakeSpeed = intakeSpeed;
    }

    public double getLeftIntakeCurrent() {
        return pdp.getCurrent(RobotMap.CAN.INTAKE_LEFT);
    }

    public double getRightIntakeCurrent() {
        return pdp.getCurrent(RobotMap.CAN.INTAKE_RIGHT);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new IntakeIdle());
    }

    public void spinInwards() {
        intakes.set(-intakeSpeed);
    }

    public void spinOutwards() {
        intakes.set(intakeSpeed);
    }

    public void spinOpposite() {
        leftIntake.set(-intakeSpeed);
        rightIntake.set(intakeSpeed*-.25);
    }

    public void set(double speed) {
        intakes.set(speed);
    }

    public void stop() {
        intakes.set(0);
    }
}

