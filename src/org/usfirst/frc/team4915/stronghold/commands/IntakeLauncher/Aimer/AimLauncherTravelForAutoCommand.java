package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimLauncherTravelForAutoCommand extends Command {

    public AimLauncherTravelForAutoCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetTravelPosition();
        System.out.println("Launcher Travel Auto Command");
        Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void execute() {
        Robot.intakeLauncher.aimLauncher();
    }

    protected boolean isFinished() {
        return Robot.intakeLauncher.isLauncherAtTravel();
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Travel Auto Interrupt");
    }
}

