package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class HighSpeedModeCommand extends Command {

    public HighSpeedModeCommand () {
        requires(Robot.gearShift);
    }
    @Override
    protected void initialize() {
        // switches the gears from low speed to high speed
        // or turns the gears on and goes to high speed mode
        System.out.println("Entering high speed mode");
        Robot.gearShift.highSpeedMode();
        //only uses initializes because the gear only shifts once
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

    }

    @Override
    protected void interrupted() {

    }

}
