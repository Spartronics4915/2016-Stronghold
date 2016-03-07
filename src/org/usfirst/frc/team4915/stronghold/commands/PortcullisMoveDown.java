package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
=======
/*
 * 
>>>>>>> c0cfe573a4258530e4eb860b6da772cfbcbb53fa
 */
public class PortcullisMoveDown extends Command {

    public PortcullisMoveDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        while (!RobotMap.portcullisSwitchBottom.get()){
            RobotMap.portcullisLeftMasterMotor.set(-.6);
        }
        RobotMap.portcullisLeftMasterMotor.set(0);
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
