package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimLauncherNeutralForAutoCommand extends Command {

	private boolean m_shouldQuit;
    public AimLauncherNeutralForAutoCommand(boolean shouldQuit) {
    	m_shouldQuit = shouldQuit;
        requires(Robot.intakeLauncher);
        if(m_shouldQuit) {
        	requires(Robot.driveTrain); // should interrupt arcadedrive
        }
    }

    protected void initialize() {
        System.out.println("Launcher Neutral Auto Command");
        Robot.intakeLauncher.launcherSetNeutralPosition(); //calls moveToSetPoint()
        Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
    		return Robot.intakeLauncher.isLauncherAtNeutral() || m_shouldQuit;
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Neutral Auto Interrupt");
    }
}
