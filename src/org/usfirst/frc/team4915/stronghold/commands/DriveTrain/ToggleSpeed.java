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
		if(Math.abs(Robot.driveTrain.turnMultiplier) < Math.abs(Robot.driveTrain.MEDIUM_TURN)) {
			Robot.driveTrain.turnMultiplier = Robot.driveTrain.MEDIUM_TURN;
		}
		else {
			Robot.driveTrain.turnMultiplier = Robot.driveTrain.SLOW_TURN;
		}
		System.out.println("Turn Multiplier " + Robot.driveTrain.turnMultiplier);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
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
