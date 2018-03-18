package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3322.Robot;
import frc.team3322.commands.*;


public class Auton extends CommandGroup {

    public enum StartPosition {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public enum Action {
        SCALE,
        SWITCH,
    }

    public enum Path {
        DONOTHING,
        POSL_LSWITCH,
        POSL_LSCALE,
        POSL_RSWITCH,
        POSL_RSCALE,
        POSL_DRIVESTRAIGHT,
        POSM_LSWITCH,
        POSM_LSCALE,
        POSM_RSWITCH,
        POSM_RSCALE,
        POSM_DRIVESTRAIGHT,
        POSR_LSWITCH,
        POSR_LSCALE,
        POSR_RSWITCH,
        POSR_RSCALE,
        POSR_DRIVESTRAIGHT
    }

    public enum Priority {
        IGNORE,
        PREFER,
        SAFE,
        FORCE
    }

    private Path selectedPath;

    public Auton(StartPosition startPos, Action action, Priority priority) {
        String gameData = Robot.gameData;
        String switchSide = gameData.substring(0, 1);
        String scaleSide = gameData.substring(1, 2);

        // TODO: mirror side path logic to reduce structure repetition
        switch (startPos) {
            case LEFT:
                if (priority == Priority.IGNORE) {
                    selectedPath = Path.POSL_DRIVESTRAIGHT;
                    break;
                }
                switch (action) {
                    case SWITCH:
                        if (switchSide.equals("L")) {
                            selectedPath = Path.POSL_LSWITCH;
                        } else {
                            if (priority == Priority.SAFE) {
                                if (scaleSide.equals("L")) {
                                    selectedPath = Path.POSL_LSCALE;
                                } else {
                                    selectedPath = Path.POSL_DRIVESTRAIGHT;
                                }
                            } else if (priority == Priority.FORCE) {
                                selectedPath = Path.POSL_RSWITCH;
                            }
                        }
                        break;
                    case SCALE:
                        if (scaleSide.equals("L")) {
                            selectedPath = Path.POSL_LSCALE;
                        } else {
                            if (priority == Priority.SAFE) {
                                if (switchSide.equals("L")) {
                                    selectedPath = Path.POSL_LSWITCH;
                                } else {
                                    selectedPath = Path.POSL_DRIVESTRAIGHT;
                                }
                            } else if (priority == Priority.FORCE) {
                                selectedPath = Path.POSL_RSCALE;
                            }
                        }
                        break;
                }
                break;
            case MIDDLE:
                if (priority == Priority.IGNORE) {
                    selectedPath = Path.POSM_DRIVESTRAIGHT;
                    break;
                }

                switch (action) {
                    case SWITCH:
                        if (switchSide.equals("L")) {
                            selectedPath = Path.POSM_LSWITCH;
                        } else {
                            selectedPath = Path.POSM_RSWITCH;
                        }
                        break;
                    case SCALE:
                        if (scaleSide.equals("L")) {
                            selectedPath = Path.POSM_LSCALE;
                        } else {
                            selectedPath = Path.POSM_RSCALE;
                        }
                        break;
                }
                break;
            case RIGHT:
                if (priority == Priority.IGNORE) {
                    selectedPath = Path.POSR_DRIVESTRAIGHT;
                    break;
                }
                switch (action) {
                    case SWITCH:
                        if (switchSide.equals("R")) {
                            selectedPath = Path.POSR_RSWITCH;
                        } else {
                            if (priority == Priority.SAFE) {
                                if (scaleSide.equals("R")) {
                                    selectedPath = Path.POSR_RSCALE;
                                } else {
                                    selectedPath = Path.POSR_DRIVESTRAIGHT;
                                }
                            } else if (priority == Priority.FORCE) {
                                selectedPath = Path.POSR_LSWITCH;
                            }
                        }
                        break;
                    case SCALE:
                        if (scaleSide.equals("R")) {
                            selectedPath = Path.POSR_RSCALE;
                        } else {
                            if (priority == Priority.SAFE) {
                                if (switchSide.equals("R")) {
                                    selectedPath = Path.POSR_RSWITCH;
                                } else {
                                    selectedPath = Path.POSR_DRIVESTRAIGHT;
                                }
                            } else if (priority == Priority.FORCE) {
                                selectedPath = Path.POSR_LSCALE;
                            }
                        }
                        break;
                }
                break;
        }

        queuePath();
    }

    private void queuePath() {
        SmartDashboard.putString("Selected path", selectedPath.toString());

        if (selectedPath == Path.DONOTHING) return;

        /*
        Important metrics (in inches)
        Distance from back wall to switch: 140.00
        Switch length from start to end: 56.00
        Distance from back wall to platform: 261.47
        Distance from back wall to scale: 299.65
        Distance from side wall to scale: 71.57
         */

        addParallel(new CloseArms(), 2);
        addParallel(new IntakeIdle());

        switch (selectedPath) {
            case POSL_DRIVESTRAIGHT:
                addSequential(new DriveDistance(140));
                break;
            case POSL_LSWITCH:
                addSequential(new DriveDistance(140 + 5));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSL_LSCALE:
                // TODO: test
                addSequential(new DriveDistance(299.65 + 5));
                addSequential(new TurnToAngle(90));
                addParallel(new ElevatorToScale());
                addSequential(new DriveDistance(-12));
                addSequential(new DriveDistance(24));
                addSequential(new EjectCube(), 3);
                break;
            case POSL_RSWITCH:
                // TODO: P2 tuning
                addSequential(new DriveDistance(218));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(148));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(180));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSL_RSCALE:
                addSequential(new DriveDistance(250));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(180));
                addParallel(new ElevatorToScale());
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(20));
                addSequential(new TurnToAngle(-90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSM_DRIVESTRAIGHT:
                addSequential(new DriveDistance(130));
                break;
            case POSM_LSWITCH:
                // TODO: P2 tuning
                addSequential(new DriveDistance(36));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(-45));
                addSequential(new DriveDistance(60));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSM_LSCALE:
                // TODO: finish this
                break;
            case POSM_RSWITCH:
                // TODO: P2 tuning
                addSequential(new DriveDistance(36));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(45));
                addSequential(new DriveDistance(60));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSM_RSCALE:
                // TODO: everything
                addSequential(new DriveDistance(36));
                addSequential(new TurnToAngle(-90));
                addSequential(new DriveDistance(36));
                addSequential(new DriveDistance(36));
                break;
            case POSR_DRIVESTRAIGHT:
                addSequential(new DriveDistance(130));
                break;
            case POSR_LSWITCH:
                // TODO: test
                addSequential(new DriveDistance(218));
                addSequential(new TurnToAngle(-90));
                addSequential(new DriveDistance(148));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(-180));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSR_LSCALE:
                // TODO: finish this
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
            case POSR_RSWITCH:
                addSequential(new DriveDistance(145));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(-90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube(), 3);
                break;
            case POSR_RSCALE:
                // TODO: test
                addSequential(new DriveDistance(310));
                addSequential(new TurnToAngle(-90));
                addParallel(new ElevatorToScale());
                addSequential(new DriveDistance(-12));
                addSequential(new DriveDistance(24));
                addSequential(new EjectCube(), 3);
                break;
        }
    }
}