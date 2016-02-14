package org.usfirst.frc.team4915.stronghold.subsystems;

import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;


public class GearShift extends Subsystem {
    
    DoubleSolenoid doubleSolenoid= RobotMap.doubleSolenoid;

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }
    
    /*
     * switches the gears from low speed to high speed
     * or turns the gears on and goes to high speed mode
     */
    public void highSpeedMode() {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    /*
     * switches the gears from high speed to low speed
     * or turns the gears on and goes to low speed mode
     */
    public void lowSpeedMode() {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

}
