package frc.team3322.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3322.RobotMap;
import frc.team3322.commands.TeleopDrive;

public class Drivetrain extends Subsystem {

    private DifferentialDrive robotDrive;

    private DoubleSolenoid shifter;
    public AHRS navx;


    public Drivetrain() {
        WPI_TalonSRX leftBackMotor = new WPI_TalonSRX(RobotMap.CAN.LEFT_BACK_MOTOR);
        //WPI_TalonSRX leftMiddleMotor = new WPI_TalonSRX(RobotMap.LEFT_MIDDLE_MOTOR);
        WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(RobotMap.CAN.LEFT_FRONT_MOTOR);
        WPI_TalonSRX rightBackMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_BACK_MOTOR);
        //WPI_TalonSRX rightMiddleMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_MIDDLE_MOTOR);
        WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_FRONT_MOTOR);

        SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftBackMotor, leftFrontMotor);
        SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightBackMotor, rightFrontMotor);

        robotDrive = new DifferentialDrive(leftGroup, rightGroup);

        shifter = new DoubleSolenoid(RobotMap.PCM.SHIFTER_REVERSE, RobotMap.PCM.SHIFTER_FORWARD);
        navx = new AHRS(SerialPort.Port.kMXP);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TeleopDrive());
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    public void driveAngle(double speed, double angle) {
        double error = navx.getAngle() - angle; //getAngle() returns overall angle, not necessarily from -180 to 180
        robotDrive.arcadeDrive(speed, error*.05); // TODO tune constant
    }

    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    public double getDistance(){
        return 0;
    }
}

