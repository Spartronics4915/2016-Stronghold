package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBallCommandGroup extends CommandGroup {

    public IntakeBallCommandGroup() {
        System.out.println("Push Button 2");
        addSequential(new IntakeBallCommand());
        addSequential(new StopWheelsCommand());
    }
}
