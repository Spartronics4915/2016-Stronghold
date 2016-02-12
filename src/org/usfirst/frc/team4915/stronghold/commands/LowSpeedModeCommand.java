package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LowSpeedModeCommand extends Command {


    public LowSpeedModeCommand() {
        requires(Robot.gearShift);
    }
    @Override
    protected void initialize() {
        // switches the gears from high speed to low speed
        // or turns the gears on and goes to low speed mode
        System.out.println("Entering low speed mode");
        Robot.gearShift.lowSpeedMode();
        //only uses initialize because the gear only shifts once
    }

    @Override
    protected void execute() {
        // initialize() ran the command - nothing more needed
    }

    @Override
    protected boolean isFinished() {
        return true;
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
