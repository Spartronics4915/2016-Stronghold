package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.RobotMap;

public class lowSpeedMode extends Command {
    
    DoubleSolenoid doubleSolenoid= RobotMap.doubleSolenoid;

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        // switches the gears from high speed to low speed
        // or turns the gears on and goes to low speed mode
        System.out.println("Entering low speed mode");
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        // leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Leaving low speed mode");
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

}
