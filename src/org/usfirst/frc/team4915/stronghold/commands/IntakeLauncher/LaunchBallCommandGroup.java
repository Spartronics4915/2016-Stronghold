package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.WaitCommand;

public class LaunchBallCommandGroup extends CommandGroup {

    public final int WAIT_DURATION = 500;

    public LaunchBallCommandGroup() {
        addSequential(new SpinLaunchWheelsOutCommand());
        addSequential(new WaitCommand(500));
        addSequential(new ExtendLauncherCylinderCommand());
        addSequential(new RetractLauncherCylinderCommand());
        addSequential(new WaitCommand(500));
        addSequential(new StopWheelsCommand());
    }
}
