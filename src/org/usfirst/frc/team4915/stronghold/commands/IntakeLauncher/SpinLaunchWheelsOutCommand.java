package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinLaunchWheelsOutCommand extends Command {

    public boolean shouldActivateServos = true;

    // this command spins the launch wheels outwards so they will launch the
    // ball
    public SpinLaunchWheelsOutCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunch();
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
