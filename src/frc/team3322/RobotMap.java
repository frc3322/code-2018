/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3322;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{
    // Drivetrain talons
    public static final int LEFT_BACK_MOTOR = 10;
    public static final int LEFT_MIDDLE_MOTOR = 2;
    public static final int LEFT_FRONT_MOTOR = 11;
    public static final int RIGHT_BACK_MOTOR = 4;
    public static final int RIGHT_MIDDLE_MOTOR = 6;
    public static final int RIGHT_FRONT_MOTOR = 5;

    // Pneumatics
    public static final int SHIFTER_SOLENOID_REVERSE = 0;
    public static final int SHIFTER_SOLENOID_FORWARD = 1;
}
