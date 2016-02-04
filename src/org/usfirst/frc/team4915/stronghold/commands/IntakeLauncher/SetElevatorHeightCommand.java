package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SetElevatorHeightCommand extends Command {

    double speed;
    
    // this command sets the angle of the launcher
    public SetElevatorHeightCommand(double speed) {
        requires(Robot.intakeLauncher);
        this.speed = speed;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.changeElevatorHeight(speed);
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
