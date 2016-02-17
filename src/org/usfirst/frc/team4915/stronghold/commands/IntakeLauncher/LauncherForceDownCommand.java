package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

/**
 *
 */
public class LauncherForceDownCommand extends Command {

    double oldSetPoint;
    
    public LauncherForceDownCommand() {
        requires(Robot.intakeLauncher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        oldSetPoint = Robot.intakeLauncher.getSetPoint();
    }

    protected void execute() {
        Robot.intakeLauncher.setSetPoint(Robot.intakeLauncher.getMinHeight());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.intakeLauncher.setSetPoint(oldSetPoint);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
