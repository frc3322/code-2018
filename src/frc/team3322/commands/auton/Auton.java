package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3322.Robot;
import frc.team3322.commands.*;


public class Auton extends CommandGroup {
    private String gameData;
    private String switchSide;
    private String scaleSide;

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
        POSX_DONOTHING,
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
        FLEXIBLE,
        FORCE
    }

    private Path selectedPath;

    public Auton(StartPosition startPos, Action action, Priority priority) {
        gameData = Robot.gameData;
        switchSide = gameData.substring(0, 1);
        scaleSide = gameData.substring(1, 2);

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
                            if (priority == Priority.FLEXIBLE) {
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
                            if (priority == Priority.FLEXIBLE) {
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
                if (priority != Priority.FORCE) {
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
                            if (priority == Priority.FLEXIBLE) {
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
                            if (priority == Priority.FLEXIBLE) {
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
        System.out.println("Path queued");
        switch (selectedPath) {
            case POSX_DONOTHING:
                break;
            case POSL_LSWITCH:
                // TODO: finish this
                System.out.println("POSL_LSWITCH");
                addSequential(new DriveDistance(3));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToSwitch());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSL_LSCALE:
                // TODO: finish this
                System.out.println("POSL_LSCALE");
                addSequential(new DriveDistance(10));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToScale());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSL_RSWITCH:
                // TODO: finish this
                System.out.println("POSR_RSWITCH");
                addSequential(new DriveDistance(4));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(8));
                addSequential(new TurnToAngle(180));
                addSequential(new DriveDistance(3));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToSwitch());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSL_RSCALE:
                // TODO: finish this
                System.out.println("POSR_RSCALE");
                addSequential(new DriveDistance(4));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(8));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(3));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToScale());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSL_DRIVESTRAIGHT:
                // TODO: finish this
                System.out.println("POSL_DRIVESTRAIGHT");
                addSequential(new DriveDistance(6));
                break;
            case POSM_LSWITCH:
                // TODO: finish this
                System.out.println("POSM_LSWITCH");
                addSequential(new DriveDistance(1));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(4));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(3));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToSwitch());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSM_LSCALE:
                // TODO: finish this
                System.out.println("POSM_LSCALE");
                addSequential(new DriveDistance(1));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(5));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(8));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToScale());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSM_RSWITCH:
                // TODO: finish this
                System.out.println("POSM_RSWITCH");
                addSequential(new DriveDistance(1));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(4));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToSwitch());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSM_RSCALE:
                // TODO: finish this
                System.out.println("POSM_RSCALE");
                addSequential(new DriveDistance(1));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(5));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(8));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToScale());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSM_DRIVESTRAIGHT: // cross baseline
                // TODO: finish this
                System.out.println("POSM_DRIVESTRAIGHT");
                addSequential(new DriveDistance(1));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(5));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(5));
                break;
            case POSR_LSWITCH:
                // TODO: finish this
                System.out.println("POSR_LSWITCH");
                addSequential(new DriveDistance(6));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(6));
                addSequential(new TurnToAngle(180));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToSwitch());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSR_LSCALE:
                // TODO: finish this
                System.out.println("POSR_LSCALE");
                addSequential(new DriveDistance(6));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(8));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(3));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToScale());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSR_RSWITCH:
                // TODO: finish this
                System.out.println("POSR_RSWITCH");
                addSequential(new DriveDistance(4));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToSwitch());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSR_RSCALE:
                // TODO: finish this
                System.out.println("POSR_RSCALE");
                addSequential(new DriveDistance(8));
                addSequential(new TurnToAngle(270));
                addSequential(new DriveDistance(2));
                addSequential(new ElevatorToScale());
                addSequential(new EjectCube());
                addSequential(new ElevatorToBottom());
                break;
            case POSR_DRIVESTRAIGHT:
                // TODO: finish this
                System.out.println("POSR_DRIVESTRAIGHT");
                addSequential(new DriveDistance(5));
                break;
        }
    }
}