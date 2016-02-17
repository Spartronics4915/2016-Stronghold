package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

/**
 *
 */
public class AimWithDashboardCommand extends Command {

    public AimWithDashboardCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.intakeLauncher.aimWithDashboard();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.intakeLauncher.readSetPoint();
    }

    protected void interrupted() {
        end();
    }
}
