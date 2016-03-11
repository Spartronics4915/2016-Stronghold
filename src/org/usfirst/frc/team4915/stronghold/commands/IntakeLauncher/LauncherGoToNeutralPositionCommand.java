package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToNeutralPositionCommand extends Command {

    public LauncherGoToNeutralPositionCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetNeutralPosition();
    }

    protected void execute() {
        Robot.intakeLauncher.moveToSetPoint();
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.launcherAtNeutralPosition();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
