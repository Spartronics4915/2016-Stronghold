package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToNeutralPositionCommand extends Command {

    public LauncherGoToNeutralPositionCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetNeutralPosition();
        System.out.println("Launcher Neutral Teleop Command");
    }

    protected void execute() {
        Robot.intakeLauncher.moveToSetPoint();
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.isLauncherAtNeutral();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
