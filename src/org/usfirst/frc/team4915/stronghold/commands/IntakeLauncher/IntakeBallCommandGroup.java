package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBallCommandGroup extends CommandGroup {
    
    public  IntakeBallCommandGroup() {
        addSequential(new RetractLauncherServosCommand());
        addParallel(new LauncherGoToIntakePositionCommand());
        addParallel(new SpinIntakeWheelsInwardCommand());
    }
}
