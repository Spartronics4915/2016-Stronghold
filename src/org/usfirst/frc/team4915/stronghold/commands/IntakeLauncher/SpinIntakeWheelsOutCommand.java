package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinIntakeWheelsOutCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball
    public SpinIntakeWheelsOutCommand() {
        setTimeout(5);
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
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.intakeLauncher.stopWheels();
    }

    @Override
    protected void interrupted() {
        
    }
}
