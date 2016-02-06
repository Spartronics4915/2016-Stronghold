package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.RobotMap;


public class highSpeedMode extends Command {

    DoubleSolenoid doubleSolenoid= RobotMap.doubleSolenoid;
    //DoubleSolenoid leftDoubleSolenoid= RobotMap.leftDoubleSolenoid;

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        //switches the gears from low speed to high speed
        //or turns the gears on and goes to high speed mode
        System.out.println("Entering high speed mode");
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        //leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
        System.out.println("Leaving high speed mode");
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
