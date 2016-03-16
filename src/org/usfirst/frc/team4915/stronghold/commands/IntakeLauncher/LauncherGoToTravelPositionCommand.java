package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToTravelPositionCommand extends Command {

    public LauncherGoToTravelPositionCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetTravelPosition();
    }

    protected void execute() {
        Robot.intakeLauncher.moveToSetPoint();
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.launcherAtTravelPosition();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
