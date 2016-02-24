package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBallCommandGroup extends CommandGroup {
    
    public  IntakeBallCommandGroup() {
        addParallel(new RetractLauncherServosCommand());
        addParallel(new LauncherGoToIntakePositionCommand());
        addParallel(new SpinIntakeWheelsInwardCommand());
    }
}
