package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimLauncherTravelForAutoCommand extends Command {

	private boolean m_shouldQuit;
    public AimLauncherTravelForAutoCommand(boolean shouldQuit) {
    	m_shouldQuit = shouldQuit;
        requires(Robot.intakeLauncher);
        if(m_shouldQuit) {
        	requires(Robot.driveTrain); // should interrupt ArcadeDrive
        }
    }

    protected void initialize() {
        Robot.intakeLauncher.launcherSetTravelPosition(); // calls moveToSetPoint()
        System.out.println("Launcher Travel Auto Command");
        Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void execute() {
     
    }

    protected boolean isFinished() {
    		return Robot.intakeLauncher.isLauncherAtTravel() || m_shouldQuit;
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Launcher Travel Auto Interrupt");
    }
}

