package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearShiftCommand extends Command {

    private boolean highSpeed;

    public GearShiftCommand(boolean highSpeed) {
        this.highSpeed = highSpeed;
        requires(Robot.gearShift);
    }

    @Override
    protected void initialize() {
        if (highSpeed) {
            Robot.gearShift.highSpeedMode();
            SmartDashboard.putString("In high gear", null);
        } else {
            Robot.gearShift.lowSpeedMode();
            SmartDashboard.putString("In low gear", null);
        }
    }

    @Override
    protected void execute() {
    
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        // Nothing to clean up
    }

    @Override
    protected void interrupted() {
        end();
    }

}
