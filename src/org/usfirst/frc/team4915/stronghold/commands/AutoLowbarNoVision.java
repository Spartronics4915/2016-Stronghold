package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.MoveStraightPositionModeCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SpinIntakeWheelsOutCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowbarNoVision extends CommandGroup {

    // autonomous command that in the case of vision failing, we can call this
    // and will go under low bar
    // rotate and shoot less accurate
    public AutoLowbarNoVision() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.

        // moves under the low bar
        addSequential(new MoveStraightPositionModeCommand(168, 0.5));
        // not exact degree number (rotates robot to get ready to shoot)
        addSequential(new AutoRotateDegrees(false, 30));
        // volts to degrees...
        // just put in a random number and gets the aimer ready to shoot
        addSequential(new LauncherGoToAngleCommand(520));
        // launches ball
        addSequential(new SpinIntakeWheelsOutCommand());

    }
}
