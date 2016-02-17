package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class LauncherForceNeutralCommand extends Command {

    public LauncherForceNeutralCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.setForceLauncherNeutral(true);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.intakeLauncher.setForceLauncherNeutral(false);
    }

    protected void interrupted() {
        end();
    }
}
