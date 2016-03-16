package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimLauncherTravelForAutoCommand extends Command {

	private boolean m_blocking;
    public AimLauncherTravelForAutoCommand(boolean blocking) {
    	m_blocking = blocking;
        requires(Robot.intakeLauncher);
        if(m_blocking) 
        	requires(Robot.driveTrain); // should interrupt ArcadeDrive
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
    	if(m_blocking)
    		return Robot.intakeLauncher.isLauncherAtTravel();
    	else
    		return true; // rely on timer
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Travel Auto Interrupt");
    }
}

