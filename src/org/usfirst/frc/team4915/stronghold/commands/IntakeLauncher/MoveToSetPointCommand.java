package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToSetPointCommand extends Command {

    public MoveToSetPointCommand() {
        requires(Robot.intakeLauncher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!VisionState.getInstance().followTargetY(Robot.intakeLauncher)) {
    		Robot.intakeLauncher.offsetSetPoint();
    		Robot.intakeLauncher.moveToSetPoint();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {        
        System.out.println("Interrupted");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
