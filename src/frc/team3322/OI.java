/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3322;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import frc.team3322.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
    public Joystick stick = new Joystick(0);

    Button button_a = new JoystickButton(stick, RobotMap.XBOX.BUTTON_A);
    Button button_b = new JoystickButton(stick, RobotMap.XBOX.BUTTON_B);
    Button button_x = new JoystickButton(stick, RobotMap.XBOX.BUTTON_X);
    Button button_y = new JoystickButton(stick, RobotMap.XBOX.BUTTON_Y);
    Button bumper_left = new JoystickButton(stick, RobotMap.XBOX.BUMPER_LEFT);
    Button bumper_right = new JoystickButton(stick, RobotMap.XBOX.BUMPER_RIGHT);
    Button button_back = new JoystickButton(stick, RobotMap.XBOX.BUTTON_BACK);
    Button button_start = new JoystickButton(stick, RobotMap.XBOX.BUTTON_START);
    Button stick_left = new JoystickButton(stick, RobotMap.XBOX.STICK_LEFT);
    Button stick_right = new JoystickButton(stick, RobotMap.XBOX.STICK_RIGHT);

    public OI() {
        stick_left.whenPressed(new ShiftDrivetrain());

        button_a.whileHeld(new OpenArms());
        button_b.whileHeld(new CloseArms());
        button_x.whileHeld(new IntakeIn());
        button_y.whileHeld(new IntakeOut());

        bumper_left.whileHeld(new PickupCube());
        bumper_right.whileHeld(new DropCube());
    }

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
