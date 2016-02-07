package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class IncrementLauncherHeightCommand extends Command {

    private int direction;

    public IncrementLauncherHeightCommand(int direction) {
        requires(Robot.intakeLauncher);
        this.direction = direction;
    }

    protected void initialize() {
        Robot.intakeLauncher.incrementLauncherHeight(direction);
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
