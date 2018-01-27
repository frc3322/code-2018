package frc.team3322.commands.auton;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.cubeIntake;
import static frc.team3322.Robot.drivetrain;
import static frc.team3322.Robot.elevator;


public class Auton extends Command {
    private String gameData;
    private String switchSide;
    private String scaleSide;
    private int state = 0;

    public enum StartPosition {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public enum AutonAction {
        SCALE,
        SWITCH,
        DONOTHING,
        DRIVESTRAIGHT,
        CLOSEST
    }

    public enum Path {
        POSX_DONOTHING,
        POS1_LSWITCH,
        POS1_LSCALE,
        POS1_RSWITCH,
        POS1_RSCALE,
        POS1_DRIVESTRAIGHT,
        POS2_LSWITCH,
        POS2_LSCALE,
        POS2_RSWITCH,
        POS2_RSCALE,
        POS2_DRIVESTRAIGHT,
        POS3_LSWITCH,
        POS3_LSCALE,
        POS3_RSWITCH,
        POS3_RSCALE,
        POS3_DRIVESTRAIGHT
    }

    private StartPosition startPos;
    private AutonAction action;
    private Path selectedPath;

    public Auton(StartPosition startPos, AutonAction action) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        requires(elevator);
        requires(cubeIntake);

        this.startPos = startPos;
        this.action = action;
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
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
                            selectedPath = Auton.Path.POS1_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS1_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS1_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS1_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("L") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS1_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS1_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS1_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS1_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS1_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS1_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS1_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS1_LSCALE;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS1_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS1_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS1_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS1_RSWITCH;
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
                            selectedPath = Auton.Path.POS2_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS2_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS2_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS2_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("L") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS2_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS2_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS2_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS2_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS2_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS2_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS2_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS2_RSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS2_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS2_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS2_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS2_RSWITCH;
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
                            selectedPath = Auton.Path.POS3_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS3_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS3_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS3_LSWITCH;
                            break;
                    }
                } else if (switchSide.equals("L") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS3_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS3_LSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS3_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS3_RSCALE;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("L")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS3_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS3_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS3_LSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS3_RSWITCH;
                            break;
                    }
                } else if (switchSide.equals("R") && scaleSide.equals("R")) {
                    switch (action) {
                        case DONOTHING:
                            selectedPath = Auton.Path.POSX_DONOTHING;
                            break;
                        case DRIVESTRAIGHT:
                            selectedPath = Auton.Path.POS3_DRIVESTRAIGHT;
                            break;
                        case SWITCH:
                            selectedPath = Auton.Path.POS3_RSWITCH;
                            break;
                        case SCALE:
                            selectedPath = Auton.Path.POS3_RSCALE;
                            break;
                        case CLOSEST:
                            selectedPath = Auton.Path.POS3_RSWITCH;
                            break;
                    }
                }
                break;
        }
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        switch (selectedPath) {
            case POSX_DONOTHING:

                break;
            case POS1_LSWITCH:

                break;
            case POS1_LSCALE:

                break;
            case POS1_RSWITCH:

                break;
            case POS1_RSCALE:

                break;
            case POS1_DRIVESTRAIGHT:

                break;
            case POS2_LSWITCH:

                break;
            case POS2_LSCALE:

                break;
            case POS2_RSWITCH:

                break;
            case POS2_RSCALE:

                break;
            case POS2_DRIVESTRAIGHT:

                break;
            case POS3_LSWITCH:

                break;
            case POS3_LSCALE:

                break;
            case POS3_RSWITCH:

                break;
            case POS3_RSCALE:

                break;
            case POS3_DRIVESTRAIGHT:

                break;
        }
    }


    /**
     * <p>
     * Returns whether this command is finished. If it is, then the command will be removed and
     * {@link #end()} will be called.
     * </p><p>
     * It may be useful for a team to reference the {@link #isTimedOut()}
     * method for time-sensitive commands.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. It is recommended to use
     * {@link edu.wpi.first.wpilibj.command.InstantCommand} (added in 2017) for this.
     * </p>
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {

    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
