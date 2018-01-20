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
    // CAN
    // Drivetrain
    public static final int LEFT_BACK_MOTOR = 10;
    public static final int LEFT_MIDDLE_MOTOR = 2;
    public static final int LEFT_FRONT_MOTOR = 11;
    public static final int RIGHT_BACK_MOTOR = 4;
    public static final int RIGHT_MIDDLE_MOTOR = 6;
    public static final int RIGHT_FRONT_MOTOR = 5;

    // Elevator
    public static final int ELEVATOR_MOTOR_1 = 7;
    public static final int ELEVATOR_MOTOR_2 = 8;


    // PCM
    // Drivetrain
    public static final int SHIFTER_FORWARD = 0;
    public static final int SHIFTER_REVERSE = 1;

    // Elevator
    public static final int ELEVATOR_SHIFTER_FORWARD = 2;
    public static final int ELEVATOR_SHIFTER_REVERSE = 3;


    // DI
    // Elevator
    public static final int TOP_LIMIT_SWITCH = 0;
    public static final int BOTTOM_LIMIT_SWITCH = 1;
}
