package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.Robot;
import frc.team3322.commands.*;


public class Auton extends CommandGroup {

    public enum Position {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public enum Objective {
        SWITCH,
        SCALE,
        AUTO_LINE,
        NONE
    }

    public enum Priority {
        SAFE,
        PREFER,
        FORCE
    }

    public enum Path {
        DO_NOTHING,
        AUTO_LINE,
        SAME_SWITCH,
        SAME_SCALE,
        OPPOSITE_SWITCH,
        OPPOSITE_SCALE,
        LEFT_SWITCH,
        LEFT_SCALE,
        RIGHT_SWITCH,
        RIGHT_SCALE
    }

    private Position startPos;
    private Objective objective;
    private Priority priority;

    private Position switchSide;
    private Position scaleSide;

    private Path selectedPath;

    /*
    Important metrics (in inches)
    Distance from back wall to switch: 140.00
    Switch length from start to end: 56.00
    Distance from back wall to platform: 261.47
    Distance from back wall to scale: 299.65
    Distance from side wall to scale: 71.57
     */

    public Auton(Position startPos, Objective objective, Priority priority) {
        this.startPos = startPos;
        this.objective = objective;
        this.priority = priority;

        String gameData = Robot.gameData;
        switchSide = gameData.substring(0, 1).equals("L") ? Position.LEFT : Position.RIGHT;
        scaleSide = gameData.substring(1, 2).equals("L") ? Position.LEFT : Position.RIGHT;

        // Make sure input is valid
        if (startPos == null || objective == null || priority == null) {
            // Do something safe
            return;
        }

        decidePath();
        SmartDashboard.putString("Selected path", selectedPath.toString());

        queuePath();
    }

    private void decidePath() {
        // These decisions do not depend on start position
        switch (objective) {
            case NONE:
                selectedPath = Path.DO_NOTHING;
                return;
            case AUTO_LINE:
                selectedPath = Path.AUTO_LINE;
                return;
        }

        switch (startPos) {
            case LEFT:
            case RIGHT:
                switch (objective) {
                    case SWITCH:
                        switch (priority) {
                            case SAFE:
                                if (switchSide == startPos) {
                                    selectedPath = Path.SAME_SWITCH;
                                } else {
                                    selectedPath = Path.AUTO_LINE;
                                }
                                break;
                            case PREFER:
                                if (switchSide == startPos) {
                                    selectedPath = Path.SAME_SWITCH;
                                } else if (scaleSide == startPos) {
                                    selectedPath = Path.SAME_SCALE;
                                } else {
                                    selectedPath = Path.AUTO_LINE;
                                }
                                break;
                            case FORCE:
                                if (switchSide == startPos) {
                                    selectedPath = Path.SAME_SWITCH;
                                } else {
                                    selectedPath = Path.OPPOSITE_SWITCH;
                                }
                                break;
                        }
                        break;
                    case SCALE:
                        switch (priority) {
                            case SAFE:
                                if (scaleSide == startPos) {
                                    selectedPath = Path.SAME_SCALE;
                                } else {
                                    selectedPath = Path.AUTO_LINE;
                                }
                                break;
                            case PREFER:
                                if (scaleSide == startPos) {
                                    selectedPath = Path.SAME_SCALE;
                                } else if (switchSide == startPos) {
                                    selectedPath = Path.SAME_SWITCH;
                                } else {
                                    selectedPath = Path.AUTO_LINE;
                                }
                                break;
                            case FORCE:
                                if (scaleSide == startPos) {
                                    selectedPath = Path.SAME_SCALE;
                                } else {
                                    selectedPath = Path.OPPOSITE_SCALE;
                                }
                                break;
                        }
                        break;
                }
            case MIDDLE:
                switch (objective) {
                    case SWITCH:
                        if (switchSide == Position.LEFT) {
                            selectedPath = Path.LEFT_SWITCH;
                        } else {
                            selectedPath = Path.RIGHT_SWITCH;
                        }
                        break;
                    case SCALE:
                        if (scaleSide == Position.LEFT) {
                            selectedPath = Path.LEFT_SCALE;
                        } else {
                            selectedPath = Path.RIGHT_SCALE;
                        }
                        break;
                }
        }
    }

    private void queuePath() {
        if (selectedPath == Path.DO_NOTHING) return;
        if (startPos == Position.MIDDLE) {
            if (switchSide == Position.RIGHT){
                addSequential(new DriveDistance(110), 5);
                addSequential(new ElevatorToSwitch());
                addSequential(new ArmsToParallel(), 1);
                addSequential(new EjectCube());
            } else {
                addSequential(new DriveDistance(110), 5);
                addSequential(new IntakeIdle());
            }
        }
        if (switchSide == startPos) {
            if (startPos == Position.LEFT) {
                addSequential(new DriveDistance(145), 5);
                addSequential(new TurnToAngle(0.5), 1.5);
                addSequential(new ElevatorToSwitch());
                addSequential(new ArmsToParallel(), 1);
                addSequential(new DriveDistance(12), 2);
                addSequential(new EjectCube(), 3);
            } else {
                addSequential(new DriveDistance(145),5);
                addSequential(new TurnToAngle(-0.5), 1.5);
                addSequential(new ElevatorToSwitch());
                addSequential(new ArmsToParallel(), 1);
                addSequential(new DriveDistance(12), 2);
                addSequential(new EjectCube(), 3);
            }
        } else addSequential(new DriveDistance(130));


        /*switch (startPos) {
            case RIGHT:
            case LEFT:
                int rightInversion = startPos == Position.LEFT ? 1 : -1;

                switch (selectedPath) {
                    case AUTO_LINE:
                        addSequential(new DriveDistance(130));
                        break;
                    case SAME_SWITCH:
                        addSequential(new DriveDistance(145));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new TurnToAngle(90 * rightInversion));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case OPPOSITE_SWITCH:
                        // TODO: P2 tuning
                        addSequential(new DriveDistance(218));
                        addSequential(new TurnToAngle(90 * rightInversion));
                        addSequential(new DriveDistance(148));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new TurnToAngle(180 * rightInversion));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case SAME_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(305));
                        addSequential(new TurnToAngle(90 * rightInversion));
                        addParallel(new ElevatorToScale());
                        addSequential(new DriveDistance(-12));
                        addSequential(new DriveDistance(24));
                        addSequential(new EjectCube(), 3);
                        break;
                    case OPPOSITE_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(218));
                        addSequential(new TurnToAngle(90 * rightInversion));
                        addSequential(new DriveDistance(180));
                        addParallel(new ElevatorToScale());
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(20));
                        addSequential(new TurnToAngle(-90 * rightInversion));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                }
                break;
            case MIDDLE:
                rightInversion = (selectedPath == Path.LEFT_SWITCH || selectedPath == Path.LEFT_SCALE) ? 1 : -1;

                switch (selectedPath) {
                    case AUTO_LINE:
                        addSequential(new DriveDistance(130));
                        break;
                    case RIGHT_SWITCH:
                    case LEFT_SWITCH:
                        // TODO: P2 tuning
                        addSequential(new DriveDistance(36));
                        addSequential(new TurnToAngle(-45 * rightInversion));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new DriveDistance(60));
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case RIGHT_SCALE:
                    case LEFT_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(36));
                        addSequential(new TurnToAngle(-90 * rightInversion));
                        addSequential(new DriveDistance(72));
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(305));
                        addSequential(new TurnToAngle(90 * rightInversion));
                        addSequential(new ElevatorToScale());
                        addSequential(new DriveDistance(-12));
                        addSequential(new DriveDistance(24));
                        addSequential(new EjectCube(), 3);
                        break;
                }
                break;
        }

        /* Uses separate code for each side of field
        switch (startPos) {
            case LEFT:
                switch (selectedPath) {
                    case AUTO_LINE:
                        addSequential(new DriveDistance(130));
                        break;
                    case SAME_SWITCH:
                        addSequential(new DriveDistance(145));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new TurnToAngle(90));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case OPPOSITE_SWITCH:
                        // TODO: P2 tuning
                        addSequential(new DriveDistance(218));
                        addSequential(new TurnToAngle(90));
                        addSequential(new DriveDistance(148));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new TurnToAngle(180));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case SAME_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(305));
                        addSequential(new TurnToAngle(90));
                        addParallel(new ElevatorToScale());
                        addSequential(new DriveDistance(-12));
                        addSequential(new DriveDistance(24));
                        addSequential(new EjectCube(), 3);
                        break;
                    case OPPOSITE_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(218));
                        addSequential(new TurnToAngle(90));
                        addSequential(new DriveDistance(180));
                        addParallel(new ElevatorToScale());
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(20));
                        addSequential(new TurnToAngle(-90));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                }
                break;
            case RIGHT:
                switch (selectedPath) {
                    case AUTO_LINE:
                        addSequential(new DriveDistance(130));
                        break;
                    case SAME_SWITCH:
                        addSequential(new DriveDistance(145));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new TurnToAngle(-90));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case OPPOSITE_SWITCH:
                        // TODO: test
                        addSequential(new DriveDistance(218));
                        addSequential(new TurnToAngle(-90));
                        addSequential(new DriveDistance(148));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new TurnToAngle(-180));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case SAME_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(305));
                        addSequential(new TurnToAngle(-90));
                        addParallel(new ElevatorToScale());
                        addSequential(new DriveDistance(-12));
                        addSequential(new DriveDistance(24));
                        addSequential(new EjectCube(), 3);
                        break;
                    case OPPOSITE_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(250));
                        addSequential(new TurnToAngle(-90));
                        addSequential(new DriveDistance(180));
                        addParallel(new ElevatorToScale());
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(20));
                        addSequential(new TurnToAngle(90));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                }
                break;
            case MIDDLE:
                switch (selectedPath) {
                    case AUTO_LINE:
                        addSequential(new DriveDistance(130));
                        break;
                    case LEFT_SWITCH:
                        // TODO: P2 tuning
                        addSequential(new DriveDistance(36));
                        addSequential(new TurnToAngle(-45));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new DriveDistance(60));
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case RIGHT_SWITCH:
                        // TODO: P2 tuning
                        addSequential(new DriveDistance(36));
                        addSequential(new TurnToAngle(45));
                        addParallel(new ElevatorToSwitch());
                        addSequential(new DriveDistance(60));
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(12));
                        addSequential(new EjectCube(), 3);
                        break;
                    case LEFT_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(36));
                        addSequential(new TurnToAngle(-90));
                        addSequential(new DriveDistance(72));
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(305));
                        addSequential(new TurnToAngle(90));
                        addSequential(new ElevatorToScale());
                        addSequential(new DriveDistance(-12));
                        addSequential(new DriveDistance(24));
                        addSequential(new EjectCube(), 3);
                        break;
                    case RIGHT_SCALE:
                        // TODO: test
                        addSequential(new DriveDistance(36));
                        addSequential(new TurnToAngle(90));
                        addSequential(new DriveDistance(72));
                        addSequential(new TurnToAngle(0));
                        addSequential(new DriveDistance(305));
                        addSequential(new TurnToAngle(-90));
                        addSequential(new ElevatorToScale());
                        addSequential(new DriveDistance(-12));
                        addSequential(new DriveDistance(24));
                        addSequential(new EjectCube(), 3);
                        break;
                }
                break;
        }*/
    }
}