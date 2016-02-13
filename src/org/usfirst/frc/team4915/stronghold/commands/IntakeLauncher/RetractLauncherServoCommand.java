package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class RetractLauncherServoCommand extends Command {

    public RetractLauncherServoCommand() {

    }

    protected void initialize() {
        Robot.intakeLauncher.retractLaunchServo();
    }

    protected void execute() {
        SmartDashboard.putNumber("Servo Position", Robot.intakeLauncher.getLauncherServo().get());
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}
