package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateLauncherServoCommand extends Command {

    public ActivateLauncherServoCommand() {

    }

    protected void initialize() {
        Robot.intakeLauncher.activateLaunchServo();
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