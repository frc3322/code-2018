package frc.team3322.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class DropCube extends CommandGroup {

    public DropCube() {
        addParallel(new CloseArms());
        addParallel(new IntakeOut());
    }
}
