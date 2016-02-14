package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LaunchBallCommandGroup extends CommandGroup {

    public final int WAIT_DURATION = 500;

    public LaunchBallCommandGroup() {
        addParallel(new SpinLaunchWheelsOutCommand());
        addSequential(new WaitCommand(WAIT_DURATION));
        addSequential(new ActivateLauncherServoCommand());
        addSequential(new RetractLauncherServoCommand());
        addSequential(new StopWheelsCommand());
    }
}
