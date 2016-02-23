package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToNeutralPositionCommand extends Command {

    public LauncherGoToNeutralPositionCommand() {

    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetNeutralPosition();
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
