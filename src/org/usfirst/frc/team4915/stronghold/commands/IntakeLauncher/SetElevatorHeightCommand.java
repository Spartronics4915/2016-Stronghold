package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SetElevatorHeightCommand extends Command {

    double targetHeight;

    // this command sets the angle of the launcher
    public SetElevatorHeightCommand(double targetHeight, double currentHeight) {
        requires(Robot.intakeLauncher);
        this.targetHeight = targetHeight;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.changeElevatorHeight(targetHeight);
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
        end();
    }

}
