package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class LauncherGoToIntakePositionCommand extends Command {

    public LauncherGoToIntakePositionCommand() {

    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetIntakePosition();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    
    }

    protected void interrupted() {
    
    }
}
