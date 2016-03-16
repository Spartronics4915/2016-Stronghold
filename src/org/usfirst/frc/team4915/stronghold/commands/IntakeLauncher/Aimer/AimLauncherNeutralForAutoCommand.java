package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimLauncherNeutralForAutoCommand extends Command {

    public AimLauncherNeutralForAutoCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        System.out.println("Launcher Neutral Auto Command");
        Robot.intakeLauncher.launcherSetNeutralPosition();
        Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void execute() {
        Robot.intakeLauncher.aimLauncher();
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.isLauncherAtNeutral();
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Neutral Auto Interrupt");
    }
}
