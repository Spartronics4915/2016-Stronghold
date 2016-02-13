package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;
import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetLauncherHeightCommand extends Command {

    // this command sets the angle of the launcher
    public SetLauncherHeightCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.moveLauncher();
        SmartDashboard.putNumber("Launcher Position:", Robot.intakeLauncher.getAimMotor().getEncPosition());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

}
