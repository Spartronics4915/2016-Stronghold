package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class IntakeBallCommandGroup extends CommandGroup {
    
    public  IntakeBallCommandGroup() {
        addSequential(new LauncherGoToIntakePositionCommand());
        addSequential(new WaitCommand(3));
        addSequential(new RetractLauncherServosCommand());
        addSequential(new SpinLaunchWheelsInwardCommand());
    }
}
