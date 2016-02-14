package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Scaler extends Subsystem {

    @Override
    protected void initDefaultCommand() {
        
    }

    public static enum State {

        LIFTING,
        REACHING

    }

}
