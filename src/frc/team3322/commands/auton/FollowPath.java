package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;

import java.awt.geom.Point2D;
import java.util.ArrayList;


public class FollowPath extends CommandGroup {

    public FollowPath(ArrayList<Point2D> points) {
        for (Point2D p : points) {
            addSequential(new DriveToPoint(p));
        }
    }
}
