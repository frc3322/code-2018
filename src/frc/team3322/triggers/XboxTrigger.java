package frc.team3322.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;


public class XboxTrigger extends Trigger {
    private final GenericHID joystick;
    private final int axisNumber;

    public XboxTrigger(GenericHID stick, int axis) {
        this.joystick = stick;
        this.axisNumber = axis;
    }

    public boolean get() {
        return joystick.getRawAxis(axisNumber) > 0;
    }
}
