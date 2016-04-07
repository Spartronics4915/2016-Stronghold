package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToPositionCommand extends Command {

    String myPosition;
    double myDegrees;
    
    public static final String ANGLE = "angle";
    public static final String NEUTRAL = "neutral";
    public static final String TRAVEL = "travel";
    
    public LauncherGoToPositionCommand(String position) {
        myPosition = position;
        requires(Robot.intakeLauncher);
    }
    
    public LauncherGoToPositionCommand(double degrees) {
        myDegrees = degrees;
        myPosition = ANGLE;
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        System.out.println("Launcher go to " + myPosition + " command");
        switch (myPosition) {
            case ANGLE: 
                Robot.intakeLauncher.launcherJumpToAngle(myDegrees);
                break;
            case NEUTRAL:
                Robot.intakeLauncher.launcherSetNeutralPosition();
                break;
            case TRAVEL:
                Robot.intakeLauncher.launcherSetTravelPosition();
                break;
            default:
                break;
        }
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    
    }

    protected void interrupted() {
        end();
    }
}
