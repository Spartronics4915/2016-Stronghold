package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleSpeed extends Command {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		if(Robot.driveTrain.turnMultiplier == Robot.driveTrain.SLOW_TURN)
			Robot.driveTrain.turnMultiplier = Robot.driveTrain.MEDIUM_TURN;
		else
			Robot.driveTrain.turnMultiplier = Robot.driveTrain.SLOW_TURN;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
