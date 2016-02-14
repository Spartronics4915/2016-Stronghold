package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;
import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActivateLauncherServoCommand extends Command {

    public ActivateLauncherServoCommand() {

    }

    protected void initialize() {
        
    }

    protected void execute() {
        Robot.intakeLauncher.activateLaunchServo();
        SmartDashboard.putNumber("Servo Position", Robot.intakeLauncher.getLauncherServo().get());
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.getLauncherServo().get() > .9;
    }

    protected void end() {

    }

    protected void interrupted() {
    
    }
}