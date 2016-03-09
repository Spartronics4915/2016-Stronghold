package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LauncherGoToAngleCommand extends Command {

    private double angle;

    public LauncherGoToAngleCommand(double angle) {
        this.angle = angle;
        SmartDashboard.putNumber("Aimer angle: ", this.angle);
    }

    protected void initialize() {
        Robot.intakeLauncher.launcherJumpToAngle(angle);
    }

    protected void execute() {
        // TODO: we probably need calls to moveToSetPoint here!
    }

    protected boolean isFinished() {
        // TODO: we're only done when we have reached the target angle.
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}
