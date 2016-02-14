package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

/**
 *
 */
public class ZeroAimerCommand extends Command {

    public ZeroAimerCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.initAimer();
    }

    protected void execute() {
        if (Robot.intakeLauncher.aimMotor.isFwdLimitSwitchClosed()) {
            Robot.intakeLauncher.zeroEncoder();
        }
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.aimMotor.isFwdLimitSwitchClosed();
    }

    protected void end() {
        isFinished();
    }

    protected void interrupted() {

    }
}
