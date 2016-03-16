package org.usfirst.frc.team4915.stronghold.commands.vision;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.command.Command;

// AutoAimLauncherCommand differs from AimLauncherCommand in that
// finishes when the aim target is reached.  This allows for
// a follow-on AutoLaunchCommand to execute.

public class AutoVisionDriveAndAim extends Command {

    // when constructed with a non-zero targetAngle, we directly
    // control the aimer.  Otherwise, we rely on the vision system.
    public AutoVisionDriveAndAim() {
        requires(Robot.intakeLauncher);
        requires(Robot.driveTrain);
    }

    protected void initialize() {
        Robot.driveTrain.init();
    }

    protected void execute() {
        Robot.driveTrain.trackVision();
        Robot.intakeLauncher.trackVision();
    }

    protected boolean isFinished() {
        VisionState vs = VisionState.getInstance();
        if(!vs.AutoAimEnabled ||
            (vs.LauncherLockedOnTarget && vs.DriveLockedOnTarget))
            return true;
        else
            return false;
    }

    protected void end() {
        VisionState vs = VisionState.getInstance();
        vs.AutoAimEnabled = false;
    }

    protected void interrupted() {
        System.out.println("Vision-aimer Interrupted");
        end();
    }
}
