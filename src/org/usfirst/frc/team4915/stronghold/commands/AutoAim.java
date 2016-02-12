package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.command.Command;


public class AutoAim extends Command {

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		int turnDegrees = VisionState.getInstance().TargetX;
		if (turnDegrees != 0)
			AutoRotateDegrees(turnDegrees);
			
	}

	@Override
	protected boolean isFinished() {
		if (VisionState.getInstance().TargetX == 0 && VisionState.getInstance().TargetY == 0)
			return true;
		else 			
			return false;
	
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}
