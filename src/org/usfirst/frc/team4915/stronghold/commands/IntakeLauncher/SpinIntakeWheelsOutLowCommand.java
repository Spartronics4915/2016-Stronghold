package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinIntakeWheelsOutLowCommand extends Command {

    public SpinIntakeWheelsOutLowCommand() {

    }

    protected void initialize() {
        requires(Robot.intakeLauncher);
        setTimeout(10);
    }

    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunchLow();
        Robot.intakeLauncher.aimLauncher();
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        Robot.intakeLauncher.stopWheels();
    }

    protected void interrupted() {
    
    }
}
