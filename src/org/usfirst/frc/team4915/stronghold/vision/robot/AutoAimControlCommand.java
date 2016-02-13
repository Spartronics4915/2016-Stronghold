package org.usfirst.frc.team4915.stronghold.vision.robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoAimControlCommand extends Command{

	@Override
	protected void initialize() {
		VisionState vs = VisionState.getInstance();
		vs.AutoAimEnabled = !vs.AutoAimEnabled;
	}

	@Override
	protected void execute() {
		System.out.println("The state of AutoAimEnabled has changed");
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
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
