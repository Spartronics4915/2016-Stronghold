package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.AimLauncherTravelForAutoCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.LauncherGoToTravelPositionCommand;

public class IntakeBallCommandGroup extends CommandGroup {
    
    public  IntakeBallCommandGroup() {
        System.out.println("Intake Command Group");
        addSequential(new RetractLauncherServosCommand());
        addSequential(new AimLauncherTravelForAutoCommand(true));
        addParallel(new SpinIntakeWheelsInwardCommand());
    }
}
