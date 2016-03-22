package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;
import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleSpeedUp extends Command {

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {	
		Robot.driveTrain.turnMultiplier = Robot.driveTrain.MEDIUM_TURN;
	}

	@Override
	protected boolean isFinished() {
		return true;	
		}

	@Override
	protected void end() {		
	}

	@Override
	protected void interrupted() {
	}


	public static void main(String[] args) {
	}

}
