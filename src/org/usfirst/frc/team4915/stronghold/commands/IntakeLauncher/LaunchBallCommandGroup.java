package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LaunchBallCommandGroup extends CommandGroup {

    public final int WAIT_DURATION = 500;

    public LaunchBallCommandGroup() {
        addSequential(new SpinLaunchWheelsOutCommand());
        System.out.println("Wheels spin out command finished");
        addSequential(new ActivateLauncherServoCommand());
        System.out.println("Activate Launcher Servo Command finished");
        addSequential(new RetractLauncherServoCommand());
        System.out.println("Retract Launcher Servo Command finished");
        addSequential(new StopWheelsCommand());
    }
}
