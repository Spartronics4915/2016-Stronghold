package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class WaitCommand extends Command {

    double waitTime;
    double startTime;

    // this command waits for a number of milliseconds
    public WaitCommand(double waitTime) {
        requires(Robot.intakeLauncher);
        this.waitTime = waitTime;
        startTime = System.currentTimeMillis();
    }

    protected void initialize() {

    }

    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() > startTime + waitTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
