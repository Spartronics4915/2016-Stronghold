package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

/**
 *
 */
public class Launcher45DegreesCommand extends Command {

    public Launcher45DegreesCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.setAimed45Degrees(false);
    }

    protected void execute() {
        Robot.intakeLauncher.launcher45Degrees();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intakeLauncher.getAimed45Degrees();
    }

    // Called once after isFinished returns true
    protected void end() {
  
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
