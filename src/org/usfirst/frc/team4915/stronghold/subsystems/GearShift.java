package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.LowSpeedModeCommand;


public class GearShift extends Subsystem {
    
    DoubleSolenoid doubleSolenoid= RobotMap.doubleSolenoid;

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }
    
    public void highSpeedMode() {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void lowSpeedMode() {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

}
