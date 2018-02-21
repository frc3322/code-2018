package frc.team3322.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3322.RobotMap;
import frc.team3322.commands.IntakeIdle;

public class Intakes extends Subsystem {

    private static final double MAX_CURRENT = 10; // in amps
    private double intakeSpeed = .35;

    private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.CAN.LEFT_INTAKE);
    private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.CAN.RIGHT_INTAKE);

    private SpeedControllerGroup intakes;

    public Intakes() {
        leftIntake.setInverted(true);

        intakes = new SpeedControllerGroup(leftIntake, rightIntake);
    }

    public Intakes(double intakeSpeed) {
        this();
        this.intakeSpeed = intakeSpeed;
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

    public void set(double speed) {
        intakes.set(speed);
    }

    public void stop() {
        intakes.set(0);
    }
}

