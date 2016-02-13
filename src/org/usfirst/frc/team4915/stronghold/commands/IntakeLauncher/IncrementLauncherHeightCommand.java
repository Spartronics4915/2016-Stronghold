package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;
import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IncrementLauncherHeightCommand extends Command {

    private int direction;

    public IncrementLauncherHeightCommand(int direction) {
        requires(Robot.intakeLauncher);
        this.direction = direction;
    }

    protected void initialize() {
        Robot.intakeLauncher.incrementLauncherHeight(direction);
    }

    protected void execute() {
        SmartDashboard.putNumber("Launcher Position:", Robot.intakeLauncher.getAimMotor().getEncPosition());
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}
