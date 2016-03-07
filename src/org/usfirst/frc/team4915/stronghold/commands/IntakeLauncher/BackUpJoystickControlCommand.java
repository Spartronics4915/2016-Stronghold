package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BackUpJoystickControlCommand extends Command {

    public BackUpJoystickControlCommand() {
        requires(Robot.intakeLauncher);
        
    }

    protected void initialize() {
        System.out.println("Back up Joystick Command");
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
