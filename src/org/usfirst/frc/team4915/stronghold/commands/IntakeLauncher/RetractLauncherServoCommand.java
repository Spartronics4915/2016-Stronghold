package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RetractLauncherServoCommand extends Command {

    public RetractLauncherServoCommand() {
    }

    protected void initialize() {
        Robot.intakeLauncher.retractLaunchServo();
    }

    protected void execute() {
    
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    
    }
}
