package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3322.Robot;
import frc.team3322.commands.*;

import static frc.team3322.Robot.drivetrain;


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
        FLEXIBLE,
        FORCE
    }

    private Path selectedPath;

    public Auton(StartPosition startPos, Action action, Priority priority) {
        String gameData = Robot.gameData;
        String switchSide = gameData.substring(0, 1);
        String scaleSide = gameData.substring(1, 2);

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
        drivetrain.shiftLow();
        switch (selectedPath) {
            case DONOTHING:
                break;
            case POSL_DRIVESTRAIGHT:
                System.out.println("POSL_DRIVESTRAIGHT");
                addSequential(new DriveDistance(250));
                break;
            case POSL_LSWITCH:
                System.out.println("POSL_LSWITCH");
                addSequential(new DriveDistance(145));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube());
                break;
            case POSL_LSCALE:
                System.out.println("POSL_LSCALE");
                addSequential(new DriveDistance(324));
                addParallel(new ElevatorToScale());
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube());
                break;
            case POSL_RSWITCH:
                System.out.println("POSR_RSWITCH");
                // TODO: finish this
                break;
            case POSL_RSCALE:
                System.out.println("POSL_RSCALE");
                // TODO: finish this
                break;
            case POSM_LSWITCH:
                // TODO: finish this
                System.out.println("POSM_LSWITCH");
                addSequential(new DriveDistance(40));
                addSequential(new TurnToAngle(-45));
                addSequential(new DriveDistance(50));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(30));
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
                addSequential(new DriveDistance(50));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(20));
                addSequential(new TurnToAngle(0));
                addSequential(new DriveDistance(10));
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
                addSequential(new DriveDistance(145));
                addParallel(new ElevatorToSwitch());
                addSequential(new TurnToAngle(-90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube());
                break;
            case POSR_RSCALE:
                // TODO: finish this
                System.out.println("POSR_RSCALE");
                addSequential(new DriveDistance(324));
                addParallel(new ElevatorToScale());
                addSequential(new TurnToAngle(-90));
                addSequential(new DriveDistance(12));
                addSequential(new EjectCube());
                break;
            case POSR_DRIVESTRAIGHT:
                System.out.println("POSR_DRIVESTRAIGHT");
                addSequential(new DriveDistance(250));
                break;
        }
    }
}