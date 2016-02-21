package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class LaunchBallCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball
    public LaunchBallCommand() {
        requires(Robot.intakeLauncher);
    }

    private boolean shouldQuit = false;
    @Override
    protected void initialize() {
        setTimeout(1); //TODO finalize time
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunch();
        if (!shouldQuit && isTimedOut())
        {
            Robot.intakeLauncher.activateLauncherServos();
            setTimeout(5);
            shouldQuit = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return (shouldQuit && isTimedOut());
    }

    @Override
    protected void end() {
        System.out.println("Launch Command Ended");
        Robot.intakeLauncher.launchEnd();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
