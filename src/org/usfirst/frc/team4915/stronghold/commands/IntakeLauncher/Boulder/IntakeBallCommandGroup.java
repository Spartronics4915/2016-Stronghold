package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder;


import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.LauncherGoToPositionCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBallCommandGroup extends CommandGroup {
    
    public  IntakeBallCommandGroup() {
        System.out.println("Intake Command Group");
        addSequential(new RetractLauncherServosCommand());
        addSequential(new LauncherGoToPositionCommand(LauncherGoToPositionCommand.INTAKE));
        addSequential(new SpinIntakeWheelsInwardCommand());
    }
}
