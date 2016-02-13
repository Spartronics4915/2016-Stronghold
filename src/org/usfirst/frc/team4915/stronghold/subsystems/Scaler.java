package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.commands.ScalerCommand;

public class Scaler extends Subsystem {

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ScalerCommand(State.RETRACTED));
    }

    public static enum State {

        RETRACTED,
        EXTENDED

    }

}
