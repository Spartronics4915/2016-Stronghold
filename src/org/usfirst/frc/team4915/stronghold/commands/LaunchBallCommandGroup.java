package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LaunchBallCommandGroup extends CommandGroup {

    public LaunchBallCommandGroup() {
        addSequential(new SpinLaunchWheelsOutCommand());
        addSequential(new ExtendLauncherCylinderCommand());
        addSequential(new RetractLauncherCylinderCommand());
        addSequential(new StopWheelsCommand());
    }
}
