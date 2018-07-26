package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class PreparePickupCube extends CommandGroup {

    public PreparePickupCube() {
        addParallel(new ArmsToPreparePickup());
        addParallel(new IntakeCube());
    }
}
