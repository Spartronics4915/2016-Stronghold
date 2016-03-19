package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.subsystems.Portcullis;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PortcullisMoveUpLeft extends Command {
    
    public PortcullisMoveUpLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("In PortcullisMoveUPLeft");
        Portcullis.PortcullisMoveUp(false); //right is true and left is false
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Portcullis.isLeftPortcullisAtTop();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}