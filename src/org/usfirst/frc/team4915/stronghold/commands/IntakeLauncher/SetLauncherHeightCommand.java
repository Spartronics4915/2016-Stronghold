package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SetLauncherHeightCommand extends Command {

    // this command sets the angle of the launcher
    public SetLauncherHeightCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.moveLauncher();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

}
