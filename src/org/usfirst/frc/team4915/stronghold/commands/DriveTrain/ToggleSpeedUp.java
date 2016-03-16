package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;
import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleSpeedUp extends Command {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {		
		Robot.driveTrain.turnMultiplier = Robot.driveTrain.MEDIUM_TURN;
		System.out.println("setting High TUrn");
	}

	@Override
	protected boolean isFinished() {
		return true;
		// TODO Auto-generated method stub			
		}

	@Override
	protected void end() {		
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		Robot.driveTrain.turnMultiplier = Robot.driveTrain.SLOW_TURN;

		// TODO Auto-generated method stub

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
