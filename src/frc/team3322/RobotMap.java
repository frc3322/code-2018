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
public abstract class RobotMap {
    // Drivetrain talons
    public static final int LEFT_BACK_MOTOR = 1;
    public static final int LEFT_MIDDLE_MOTOR = 2;
    public static final int LEFT_FRONT_MOTOR = 3;
    public static final int RIGHT_BACK_MOTOR = 4;
    public static final int RIGHT_MIDDLE_MOTOR = 5;
    public static final int RIGHT_FRONT_MOTOR = 6;

    // Pneumatics
    public static final int SHIFTER_SOLENOID_REVERSE = 0;
    public static final int SHIFTER_SOLENOID_FORWARD = 0;

    //CubeIntake talons
    public static final int LEFT_INTAKE = 7;
    public static final int RIGHT_INTAKE = 8;
    public static final int LEFT_ARM = 9;
    public static final int RIGHT_ARM = 10;

}
