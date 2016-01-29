package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SetElevatorHeightCommand extends Command {

    private double height;

    public SetElevatorHeightCommand(double height) {
        requires(Robot.intakeLauncher);
        this.height = height;
    }

    protected void initialize() {
        Robot.intakeLauncher.setElevatorHeight(height);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {
        end();
    }

}
