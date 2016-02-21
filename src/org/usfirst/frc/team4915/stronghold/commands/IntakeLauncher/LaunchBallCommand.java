package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LaunchBallCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball
    public LaunchBallCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        setTimeout(2); //TODO finalize time
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.launch();
        SmartDashboard.putString("Flywheels spinning ", "outward");
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        SmartDashboard.putString("Boulder in Basket: ", "No");
        System.out.println("Launch Command Ended");
    }

    @Override
    protected void interrupted() {
        end();
    }
}
