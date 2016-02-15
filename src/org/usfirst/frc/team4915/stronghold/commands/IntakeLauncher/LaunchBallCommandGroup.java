package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LaunchBallCommandGroup extends CommandGroup {

    public LaunchBallCommandGroup() {
        addParallel(new SpinLaunchWheelsOutCommand());
        addSequential(new ActivateLauncherPneumaticCommand());
        addSequential(new RetractLauncherPneumaticCommand());
        addSequential(new StopWheelsCommand());
    }
}
