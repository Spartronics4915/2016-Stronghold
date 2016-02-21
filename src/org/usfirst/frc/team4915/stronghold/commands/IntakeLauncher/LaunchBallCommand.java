package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class LaunchBallCommand extends Command {

    public boolean shouldActivateServos = true;
    
    // this command spins the launch wheels outwards so they will launch the
    // ball
    public LaunchBallCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        setTimeout(1); //TODO finalize time
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunch();
        if (isTimedOut() && shouldActivateServos) {
            Robot.intakeLauncher.activateLauncherServos();
            //System.out.println("Activating Servo");
            shouldActivateServos = false;
        } 
    }

    @Override
    protected boolean isFinished() {
        return (false);
    }

    @Override
    protected void end() {
        //System.out.println("Launch Command Ended");
        Robot.intakeLauncher.launchEnd();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
