package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

/**
 *
 */
public class AimerGoToAngleCommand extends Command {

    private int setPoint;
    
    public AimerGoToAngleCommand(int setPoint) {
        this.setPoint = setPoint;
	SmartDashboard.putNumber("Aimer setPoint", this.setPoint);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.intakeLauncher.setSetPoint(setPoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
