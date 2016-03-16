package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToNeutralPositionCommand extends Command {

    public LauncherGoToNeutralPositionCommand() {

    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetNeutralPosition();
        System.out.println("Launcher Neutral Teleop Command");
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
