package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractLauncherPneumaticCommand extends Command {

    public RetractLauncherPneumaticCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.retractPneumatic();
    }

    protected void execute() {
    
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        Robot.intakeLauncher.setWheelsFinished(true);
    }

    protected void interrupted() {
    }
}
