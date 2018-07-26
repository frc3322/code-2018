package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class PickupCube extends CommandGroup {

    public PickupCube() {
        addParallel(new CloseArms(), .75);
        addParallel(new IntakeCube(), .75);
    }
}
