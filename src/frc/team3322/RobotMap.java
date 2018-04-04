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
    /**
     * IDs for Talons and other CAN devices
     */
    public static class CAN {
        // Drivetrain
        public static final int LEFT_FRONT_MOTOR = 4;
        public static final int LEFT_BACK_MOTOR = 5;
        public static final int RIGHT_FRONT_MOTOR = 14;
        public static final int RIGHT_BACK_MOTOR = 15;

        // Elevator
        public static final int ELEVATOR_MOTOR_1 = 3;
        public static final int ELEVATOR_MOTOR_2 = 13;

        // Arms
        public static final int ARM_LEFT = 2;
        public static final int ARM_RIGHT = 12;

        // Intake
        public static final int INTAKE_LEFT = 1;
        public static final int INTAKE_RIGHT = 11;
    }

    /**
     * IDs for pneumatics solenoids
     */
    public static class PCM {
        // Drivetrain
        public static final int DRIVETRAIN_SHIFTER_FORWARD = 0;
        public static final int DRIVETRAIN_SHIFTER_REVERSE = 1;
    }


    /**
     * IDs for digital devices such as switches and sensors
     */
    public static class DIO {
        // Drivetrain
        public static final int DRIVETRAIN_LEFT_ENCODER_A = 4;
        public static final int DRIVETRAIN_LEFT_ENCODER_B = 5;
        public static final int DRIVETRAIN_RIGHT_ENCODER_A = 2;
        public static final int DRIVETRAIN_RIGHT_ENCODER_B = 3;

        // Elevator
        public static final int ELEVATOR_ENCODER_A = 0;
        public static final int ELEVATOR_ENCODER_B = 1;

        // Arms
        public static final int ARM_LEFT_ENCODER_A = 6;
        public static final int ARM_LEFT_ENCODER_B = 7;
        public static final int ARM_RIGHT_ENCODER_A = 8;
        public static final int ARM_RIGHT_ENCODER_B = 9;
    }

    public static class XBOX {
        // Buttons
        public static final int BUTTON_A = 1;
        public static final int BUTTON_B = 2;
        public static final int BUTTON_X = 3;
        public static final int BUTTON_Y = 4;
        public static final int BUMPER_LEFT = 5;
        public static final int BUMPER_RIGHT = 6;
        public static final int BUTTON_BACK = 7;
        public static final int BUTTON_START = 8;
        public static final int STICK_LEFT = 9;
        public static final int STICK_RIGHT = 10;

        // Axes
        public static final int STICK_L_X_AXIS = 0;
        public static final int STICK_L_Y_AXIS = 1;
        public static final int STICK_R_X_AXIS = 4;
        public static final int STICK_R_Y_AXIS = 5;
        public static final int TRIGGER_L_AXIS = 2;
        public static final int TRIGGER_R_AXIS = 3;
    }
}
