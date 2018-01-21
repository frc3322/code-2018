package frc.team3322.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import static frc.team3322.Robot.drivetrain;

public class Auton extends Command {

    private String gameData;
    private String switchSide;
    private String scaleSide;

    private int state = 0;
    private double startTime, autonStartTime;

    public enum StartPos {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public enum DesiredTarget {
        SCALE,
        SWITCH
    }

    private StartPos startPos;
    private DesiredTarget desiredTarget;

    public Auton(StartPos startPos, DesiredTarget desiredTarget) {
        requires(drivetrain);

        this.startPos = startPos;
        this.desiredTarget = desiredTarget;
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        switchSide = gameData.substring(0, 1);
        scaleSide = gameData.substring(1, 2);
        autonStartTime = System.currentTimeMillis();
        startTime = 0;
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        if (startPos == StartPos.RIGHT) {
            if (desiredTarget == DesiredTarget.SWITCH) {
                if (switchSide.equals("L")) {
                    switch (state) {
                        case 0:
                            if (System.currentTimeMillis() - startTime >= 5000) {
                                state++;
                            } else {
                                drivetrain.drive(1,0);
                            }
                            break;
                        case 1:
                            if (Math.abs(drivetrain.navx.getYaw() + 90) < 5) {
                                state++;
                                resetTime();
                            } else {
                                drivetrain.drive(0,0.5);
                            }
                            break;
                        case 2:
                            if (System.currentTimeMillis() - startTime >= 5000) {
                                state++;
                            } else {
                                drivetrain.drive(1,0);
                            }
                            break;
                        case 3:
                            if (Math.abs(drivetrain.navx.getYaw() + 90) < 5) {
                                state++;
                                resetTime();
                            } else {
                                drivetrain.drive(0,0.5);
                            }
                            break;
                        case 4:
                            if (System.currentTimeMillis() - startTime >= 500) {
                                state++;
                            } else {
                                drivetrain.drive(1,0);
                            }
                            break;
                        case 5:
                            //run cubeHolder motor
                            break;
                    }
                } else if (switchSide.equals("R")) {
                    switch (state) {

                    }
                }
            } else if (desiredTarget == DesiredTarget.SCALE) {
                if (scaleSide.equals("L")) {
                    switch (state) {

                    }
                } else if (scaleSide.equals("R")) {
                    switch (state) {

                    }
                }
            }
        } else if (startPos == StartPos.MIDDLE) {
            if (desiredTarget == DesiredTarget.SWITCH) {
                if (switchSide.equals("L")) {
                    switch (state) {

                    }
                } else if (switchSide.equals("R")) {
                    switch (state) {

                    }
                }
            } else if (desiredTarget == DesiredTarget.SCALE) {
                if (scaleSide.equals("L")) {
                    switch (state) {

                    }
                } else if (scaleSide.equals("R")) {
                    switch (state) {

                    }
                }
            }
        } else if (startPos == StartPos.LEFT) {
            if (desiredTarget == DesiredTarget.SWITCH) {
                if (switchSide.equals("L")) {
                    switch (state) {

                    }
                } else if (switchSide.equals("R")) {
                    switch (state) {

                    }
                }
            } else if (desiredTarget == DesiredTarget.SCALE) {
                if (scaleSide.equals("L")) {
                    switch (state) {

                    }
                } else if (scaleSide.equals("R")) {
                    switch (state) {

                    }
                }
            }
        }
    }

    public void resetTime() {
        startTime = System.currentTimeMillis();
    }

    public double getElapsedTime() {
        double elapsedTime = (System.currentTimeMillis() - autonStartTime)/1000;
        double twoDecimals = (double) Math.round(elapsedTime * 100) / 100;
        return twoDecimals;
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
