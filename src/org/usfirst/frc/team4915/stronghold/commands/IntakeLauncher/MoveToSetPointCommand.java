package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

public class MoveToSetPointCommand extends Command {

    public MoveToSetPointCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {

    }

    protected void execute() {
        if (!VisionState.getInstance().followTargetY(Robot.intakeLauncher)) {
            Robot.intakeLauncher.moveLauncherWithJoystick();
            Robot.intakeLauncher.moveToSetPoint();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        System.out.println("Interrupted");
    }

    protected void interrupted() {

    }
}
