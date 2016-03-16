package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LaunchBallCommandGroup extends CommandGroup {

    public LaunchBallCommandGroup() {
        System.out.println("Launch Ball Command Group");
        addSequential(new ActivateLauncherServosCommand());
        addSequential(new WaitCommand(1));
        addSequential(new RetractLauncherServosCommand());
        addSequential(new StopWheelsCommand());
    }
}
