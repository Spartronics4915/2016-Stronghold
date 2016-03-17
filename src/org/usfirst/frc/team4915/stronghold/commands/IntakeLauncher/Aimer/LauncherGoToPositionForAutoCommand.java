package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToPositionForAutoCommand extends Command {

    private boolean m_shouldQuit;
    private String myPosition;
    private double myDegrees;
    
    public static final String ANGLE = "angle";
    public static final String NEUTRAL = "neutral";
    public static final String TRAVEL = "travel";
    
    public LauncherGoToPositionForAutoCommand(boolean shouldQuit, String position) {
        m_shouldQuit = shouldQuit;
        myPosition = position;
        requires(Robot.intakeLauncher);
        if(m_shouldQuit) {
            requires(Robot.driveTrain); // should interrupt arcadedrive
        }
    }
    
    public LauncherGoToPositionForAutoCommand(boolean shouldQuit, double degrees) {
        m_shouldQuit = shouldQuit;
        myDegrees = degrees;
        myPosition = ANGLE;
        requires(Robot.intakeLauncher);
        if(m_shouldQuit) {
            requires(Robot.driveTrain); // should interrupt arcadedrive
        }
    }
    
    

    protected void initialize() {
        System.out.println("Launcher Neutral Auto Command");
        switch(myPosition) {
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
        switch(myPosition) {
            case NEUTRAL:
               return Robot.intakeLauncher.isLauncherAtNeutral() || m_shouldQuit; 
            case TRAVEL:
               return Robot.intakeLauncher.isLauncherAtTravel() || m_shouldQuit; 
            case ANGLE:
               return Robot.intakeLauncher.isLauncherAtAngle(myDegrees) || m_shouldQuit;
            default:
                return true;
        }
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Neutral Auto Interrupt");
    }
}
