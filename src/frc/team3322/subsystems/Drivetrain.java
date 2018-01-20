package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3322.RobotMap;
import frc.team3322.commands.TeleopDrive;

public class Drivetrain extends Subsystem {

    public DifferentialDrive robotDrive;
    private DoubleSolenoid shifter;

    public Drivetrain() {
        WPI_TalonSRX leftBackMotor = new WPI_TalonSRX(RobotMap.LEFT_BACK_MOTOR);
        WPI_TalonSRX leftMiddleMotor = new WPI_TalonSRX(RobotMap.LEFT_MIDDLE_MOTOR);
        WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR);
        WPI_TalonSRX rightBackMotor = new WPI_TalonSRX(RobotMap.RIGHT_BACK_MOTOR);
        WPI_TalonSRX rightMiddleMotor = new WPI_TalonSRX(RobotMap.RIGHT_MIDDLE_MOTOR);
        WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR);

        SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftBackMotor, leftMiddleMotor, leftFrontMotor);
        SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightBackMotor, rightMiddleMotor, rightFrontMotor);

        robotDrive = new DifferentialDrive(leftGroup, rightGroup);

        shifter = new DoubleSolenoid(RobotMap.SHIFTER_SOLENOID_REVERSE, RobotMap.SHIFTER_SOLENOID_FORWARD);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TeleopDrive());
    }

    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }
}

