package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AimLauncherCommand extends Command {

    public AimLauncherCommand() {
        requires(Robot.intakeLauncher);
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        Robot.intakeLauncher.aimLauncher();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        System.out.println("Aimer Interrupted");
        
    }
}
