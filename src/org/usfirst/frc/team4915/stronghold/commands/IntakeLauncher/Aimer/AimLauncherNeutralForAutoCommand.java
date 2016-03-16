package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimLauncherNeutralForAutoCommand extends Command {

	private boolean m_blocking;
    public AimLauncherNeutralForAutoCommand(boolean blocking) {
    	m_blocking = blocking;
        requires(Robot.intakeLauncher);
        if(m_blocking)
        	requires(Robot.driveTrain); // should interrupt arcadedrive
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
    	if(m_blocking)
    		return Robot.intakeLauncher.isLauncherAtNeutral();
    	else
    		return true; // rely on timer
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Neutral Auto Interrupt");
    }
}
