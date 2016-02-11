package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class WaitCommand extends Command {

    double waitTime;
    double startTime;

    // this command waits for a number of milliseconds
    public WaitCommand(double waitTime) {
        this.waitTime = waitTime;
        startTime = System.currentTimeMillis();
    }

    protected void initialize() {

    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return System.currentTimeMillis() > startTime + waitTime;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
