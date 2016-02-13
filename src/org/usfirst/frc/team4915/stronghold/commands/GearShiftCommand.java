package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearShiftCommand extends Command {
	private boolean on;
	private boolean fin = false;
    public GearShiftCommand (boolean on) {
        requires(Robot.gearShift);
    }
    @Override
    protected void initialize() {
    }
    
    @Override
    protected void execute() {
      	if (on = true){
    		Robot.gearShift.highSpeedMode();
            SmartDashboard.putString("In high gear", null);
    		fin = true;
    	}
        else {
             Robot.gearShift.lowSpeedMode();
             SmartDashboard.putString("In low gear", null );
             fin = true;
        }    }

    @Override
    protected boolean isFinished() {
        return fin;
    }

    @Override
    protected void end() {
        // FIXME: call isFinished() to ensure scheduler properly ends/cleans the command
    }

    @Override
    protected void interrupted() {
        // FIXME: call end() to ensure scheduler properly ends/cleans the command
    }

}
