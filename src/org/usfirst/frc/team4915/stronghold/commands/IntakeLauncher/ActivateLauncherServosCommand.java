package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class ActivateLauncherServosCommand extends Command {

    public ActivateLauncherServosCommand() {
        
    }

    protected void initialize() {
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

    }
}
