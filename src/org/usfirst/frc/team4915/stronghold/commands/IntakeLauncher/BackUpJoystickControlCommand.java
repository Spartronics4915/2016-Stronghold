package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

/**
 *
 */
public class BackUpJoystickControlCommand extends Command {

    public BackUpJoystickControlCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.intakeLauncher.backUpJoystickMethod();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        System.out.println("Interrupted");
        end();
    }
}
