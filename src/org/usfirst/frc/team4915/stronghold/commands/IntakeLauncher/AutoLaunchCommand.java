package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoLaunchCommand extends CommandGroup {
    
    public  AutoLaunchCommand() {
        addSequential(new SpinIntakeWheelsOutCommand());
        addSequential(new WaitCommand(3));
        addSequential(new ActivateLauncherServosCommand());
        addSequential(new WaitCommand(1));
        addSequential(new RetractLauncherServosCommand());
        addSequential(new StopWheelsCommand());
    }
}
