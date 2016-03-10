package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.command.Command;

// AutoAimLauncherCommand differs from AimLauncherCommand in that
// finishes when the aim target is reached.  This allows for
// a follow-on AutoLaunchCommand to execute.

public class VisionAimLauncherCommand extends Command {

    // when constructed with a non-zero targetAngle, we directly
    // control the aimer.  Otherwise, we rely on the vision system.
    public VisionAimLauncherCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.intakeLauncher.aimLauncher();
    }

    protected boolean isFinished() {
        VisionState vs = VisionState.getInstance();
        if(!vs.AutoAimEnabled || vs.LauncherLockedOnTarget)
            return true;
        else
            return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        System.out.println("Vision-aimer Interrupted");
        end();
    }
}
