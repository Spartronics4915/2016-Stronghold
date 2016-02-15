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
        System.out.println("Zero Aimer Command");
    }

    protected void execute() {
        Robot.intakeLauncher.initAimer();
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.aimMotor.isRevLimitSwitchClosed();
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.setEncPosition(0);
    }

    protected void interrupted() {

    }
}
