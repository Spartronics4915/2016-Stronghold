package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LaunchBallCommandGroup extends CommandGroup {

    public LaunchBallCommandGroup() {
        addParallel(new SpinLaunchWheelsOutCommand());
        addSequential(new ActivateLauncherServosCommand());
        addSequential(new WaitCommand(2)); //TODO finalize time
        addSequential(new RetractLauncherServosCommand());
    }
}
