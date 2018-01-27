/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3322;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.commands.auton.Auton;
import frc.team3322.subsystems.CubeIntake;
import frc.team3322.subsystems.Drivetrain;
import frc.team3322.subsystems.Elevator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
// If you rename or move this class, update the build.properties file in the project root
public class Robot extends TimedRobot 
{

    public static final Drivetrain drivetrain = new Drivetrain();
    public static final Elevator elevator = new Elevator();
    public static final CubeIntake cubeIntake = new CubeIntake();
    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser<Auton.StartPosition> startChooser = new SendableChooser<>();
    private SendableChooser<Auton.Action> actionChooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() 
    {
        oi = new OI();

        // Create sendable choosers for auton selection
        startChooser.addObject("Left", Auton.StartPosition.LEFT);
        startChooser.addDefault("Middle", Auton.StartPosition.MIDDLE);
        startChooser.addObject("Right", Auton.StartPosition.RIGHT);

        actionChooser.addObject("Do nothing", Auton.Action.DONOTHING);
        actionChooser.addDefault("Drive straight", Auton.Action.DRIVESTRAIGHT);
        actionChooser.addObject("Closest", Auton.Action.CLOSEST);
        actionChooser.addObject("Scale", Auton.Action.SCALE);
        actionChooser.addObject("Switch", Auton.Action.SWITCH);

        SmartDashboard.putData("Start pos", startChooser);
        SmartDashboard.putData("Auton action", actionChooser);
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() 
    {
        
    }

    @Override
    public void disabledPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() 
    {
        autonomousCommand = new Auton(startChooser.getSelected(), actionChooser.getSelected());

        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() 
    {
        autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() 
    {
        
    }
}
