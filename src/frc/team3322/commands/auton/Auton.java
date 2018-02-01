package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;


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
        DONOTHING,
        DRIVESTRAIGHT,
        CLOSEST
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

    private Path selectedPath;

    public Auton(StartPosition startPos, Action action) {
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        switchSide = gameData.substring(0, 1);
        scaleSide = gameData.substring(0, 1);

        switch (startPos) {
            case LEFT:
                if (switchSide.equals("L") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSL_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSL_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSL_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSL_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("L") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSL_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSL_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSL_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSL_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSL_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSL_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSL_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSL_LSCALE;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSL_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSL_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSL_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSL_RSWITCH;
                            break;
                    }
                }
                break;
            case MIDDLE:
                if (switchSide.equals("L") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSM_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSM_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSM_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSM_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("L") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSM_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSM_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSM_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSM_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSM_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSM_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSM_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSM_RSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSM_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSM_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSM_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSM_RSWITCH;
                            break;
                    }
                }
                break;
            case RIGHT:
                if (switchSide.equals("L") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSR_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSR_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSR_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSR_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("L") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSR_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSR_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSR_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSR_RSCALE;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSR_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSR_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSR_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSR_RSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POSR_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POSR_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POSR_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POSR_RSWITCH;
                            break;
                    }
                }
                break;
        }

        queuePath();
    }

    public void queuePath() {
        switch (selectedPath) {
            case POSX_DONOTHING:
                break;
            case POSL_LSWITCH:
                addSequential(new DriveDistance(10));
                addSequential(new TurnToAngle(90));
                addSequential(new DriveDistance(5));
                break;
            case POSL_LSCALE:

                break;
            case POSL_RSWITCH:

                break;
            case POSL_RSCALE:

                break;
            case POSL_DRIVESTRAIGHT:

                break;
            case POSM_LSWITCH:

                break;
            case POSM_LSCALE:

                break;
            case POSM_RSWITCH:

                break;
            case POSM_RSCALE:

                break;
            case POSM_DRIVESTRAIGHT:

                break;
            case POSR_LSWITCH:

                break;
            case POSR_LSCALE:

                break;
            case POSR_RSWITCH:

                break;
            case POSR_RSCALE:

                break;
            case POSR_DRIVESTRAIGHT:

                break;
        }
    }
}
