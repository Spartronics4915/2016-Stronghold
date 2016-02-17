package org.usfirst.frc.team4915.stronghold.commands.vision;

import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.command.Command;

public class AutoAimControlCommand extends Command{
	
	public AutoAimControlCommand(boolean toggleEnable, boolean toggleTarget) {
		VisionState vs = VisionState.getInstance();
		vs.toggleAimState(toggleEnable, toggleTarget);
	}
	@Override
	protected void initialize() {
		//Do nothing		
	}

	@Override
	protected void execute() {
		System.out.println("The state of AutoAimEnabled has changed");
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		// Do nothing
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}	
}
