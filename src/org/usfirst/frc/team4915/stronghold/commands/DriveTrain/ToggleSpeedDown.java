package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleSpeedDown extends Command {

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.driveTrain.turnMultiplier = Robot.driveTrain.SLOW_TURN;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
