package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SpinLaunchWheelsInwardCommand extends Command {

    // this command spins the intake flywheels inward to retrieve the ball
    public SpinLaunchWheelsInwardCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        setTimeout(10); // TODO finalize time
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedIntake();
    }

    @Override
    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch or
        // after 10 seconds
        return (Robot.intakeLauncher.boulderSwitch.get() || isTimedOut());
    }

    @Override
    protected void end() {
        Robot.intakeLauncher.stopWheels();
        // Robot.intakeLauncher.launcherSetNeutralPosition();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
