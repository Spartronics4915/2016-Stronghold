package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateLauncherServosCommand extends Command {

    public ActivateLauncherServosCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        System.out.println("Activate Launcher Servos Command");
        Robot.intakeLauncher.activateLauncherServos();
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
}
