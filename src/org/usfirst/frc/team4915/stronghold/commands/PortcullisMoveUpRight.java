package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.Portcullis;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PortcullisMoveUpRight extends Command {
    
    public PortcullisMoveUpRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
  //(Robot.portcullis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Portcullis.PortcullisMoveUp(true); //right is true and left is false
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Portcullis.isRightPortcullisAtTop();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.portcullis.stopRightMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        System.out.println("PortcullisMoveUpRight interrupted");
        end();
    }
}