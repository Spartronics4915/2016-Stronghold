package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.IntakeLauncher;

public class CalibrateLauncherEncoderCommand extends Command {

    // this command zeroes the encoder for the launcher angle to the lowest
    // postition
    public CalibrateLauncherEncoderCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.changeElevatorHeight(IntakeLauncher.ELEVATOR_MIN_HEIGHT);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        if (Robot.intakeLauncher.launcherBottomSwitch.get()) {
            Robot.intakeLauncher.zeroLauncherEncoder();
            return true;
        } else {
            return false;
        }

    }

    protected void end() {
        isFinished();
    }

    protected void interrupted() {
        end();
    }
}
